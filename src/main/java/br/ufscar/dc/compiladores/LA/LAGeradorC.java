/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mariz
 */
public class LAGeradorC extends LABaseVisitor<Void>{
    StringBuilder saida;
    TabelaDeSimbolos tabela;
    List<String> variaveisLeia = new ArrayList<String>();
    List<String> operadorEscreva = new ArrayList<String>();
    Integer numeroIntervalo1;
    Integer numeroIntervalo2;
    Boolean visita1;
    Boolean registro = false;
    String nomeRegistro;
    private boolean declaracaoReg = false;
    List<String> struct = new ArrayList<String>();
    List<TipoLA> structTipo = new ArrayList<TipoLA>();
    String tipoRetFunc = "";
    
    public LAGeradorC()
    {
        saida = new StringBuilder();
        this.tabela = new TabelaDeSimbolos();
    }

    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
        //aqui estamos imprimindo a base do código em C
        // e entrando nas declarações e comandos
        
        saida.append("#include <stdio.h>\n");
        saida.append("#include <stdlib.h>\n");
        
        visitDeclaracoes(ctx.declaracoes());
        
        saida.append("int main() {\n");
        
        visitCorpo(ctx.corpo());
        
        saida.append("\treturn 0;\n");
        saida.append("}\n");
        
        return null;
    }
    
    @Override
    public Void visitDeclaracoes(LAParser.DeclaracoesContext ctx)
    {
        //aqui, assim como em muitas outras funções visit escritas nessa classe,
        //estamos apenas chamando a função mais específica para a linha em que estamos.
        //algumas dessas funções, inclusive, não seriam necessárias,
        //pois já implementam da classe da qual herdam,
        //mas as escrevemos a fim de melhorar a visualização de comandos complexos
        //por isso, as demais funções desse tipo não serão comentadas
        ctx.decl_local_global().forEach(dec -> visitDecl_local_global(dec));
        return null;
    }
    
    @Override
    public Void visitDeclaracao_global(LAParser.Declaracao_globalContext ctx)
    {
        if(ctx.inicioProc != null)
        {
            //para o caso de ser a declaração de um procedimento
            saida.append("void ");
            saida.append(ctx.IDENT().getText());
            saida.append("(");
            
            //para imprimir os parâmetros
            visitParametros(ctx.parametros());
            
            saida.append("){\n");
            
            //para imprimir os comandos
            ctx.cmd().forEach(dec -> visitCmd(dec));
            
            saida.append("}\n");
        }
        else if(ctx.inicioFuncao != null)
        {
            //no caso de uma função estar sendo declarada
            
            //pegamos o tipo usado no retorno
            String tipoNome = ctx.tipo_estendido().getText();
            String tipo = "";
            
            //aqui, passamos o tipo na LA para o tipo em C
            switch(tipoNome)
            {
                case "inteiro":
                    tipo = "int";
                    break;
                case "real":
                    tipo = "float";
                    break;
                case "literal":
                    tipo = "char*";
                    break;
                case "logico":
                    break;
            }
            
            //salvamos o retorno da funcao nessa variavel
            tipoRetFunc = ctx.tipo_estendido().getText();
            
            //imprimindo o tipo de retorno e o nome
            saida.append(tipo + " " + ctx.IDENT() + "(");
            
            //imprimindo os parametros
            visitParametros(ctx.parametros());
            
            saida.append(")\n");
            
            //imprimindo as declaracoes locais
            ctx.declaracao_local().forEach(dec -> visitDeclaracao_local(dec));
            
            saida.append("{\n");
            
            //imprimindo os comandos
            ctx.cmd().forEach(dec -> visitCmd(dec));
            
            saida.append("}\n");
        }
        return null;
    }
    
    @Override
    public Void visitParametros(LAParser.ParametrosContext ctx)
    {
        ctx.parametro().forEach(dec -> visitParametro(dec));
        return null;
    }
    
    @Override
    public Void visitParametro(LAParser.ParametroContext ctx)
    {
        String tipo;
        TipoLA tipoLA;
        String nome;
        
        for(Integer i = 0; i < ctx.identificador().size(); i++)
        {
            //passando por todos os parametros
            
            //pegando o tipo
            tipo = ctx.tipo_estendido().getText();
            //pegando o nome
            nome = ctx.identificador(i).IDENT(0).getText();
            
            //inicializando a variavel so para nao dar erro
            //aqui estamos pegando o nome do tipo em C
            //e também o tipo dela no formato TipoLA
            tipoLA = TipoLA.INVALIDO;
            switch(tipo)
            {
                case "inteiro":
                    tipo = "int";
                    tipoLA = TipoLA.INTEIRO;
                    break;
                case "real":
                    tipo = "float";
                    tipoLA = TipoLA.REAL;
                    break;
                case "literal":
                    tipo = "char*";
                    tipoLA = TipoLA.LITERAL;
                    break;
                case "logico":
                    tipoLA = TipoLA.LOGICO;
                    break;
            }
            
            //imprimindo tipo e nome
            saida.append(tipo);
            saida.append(" ");
            saida.append(nome);
            
            //coloca vírgula se nao for o ultimo
            if(i < ctx.identificador().size() - 1)
            {
                saida.append(", ");
            }
            
            //coloca a varivel na tabela de simbolos
            tabela.adicionar(nome, tipoLA);
        }
        return null;
    }
    
    @Override
    public Void visitDeclaracao_local(LAParser.Declaracao_localContext ctx)
    {
        if(ctx.variavel() != null)
        {
            //se é uma variavel vamos pro visitvariavel
            visitVariavel(ctx.variavel());
        }
        else if(ctx.valor_constante() != null)
        {
            //se eh uma constante ja imprimimos
            saida.append("#define " + ctx.IDENT().getText() + " " + ctx.valor_constante().getText() + "\n\n");
        }
        else if(ctx.IDENT() != null)
        {
            //esse caso é para estruturas
            saida.append("typedef struct{\n");
            visitTipo(ctx.tipo());
            saida.append("}\n");
            saida.append(ctx.IDENT().getText());
            saida.append(";\n");
        }
        
        return null;
    }
    
    @Override
    public Void visitVariavel(LAParser.VariavelContext ctx)
    {
        TipoLA tipoLA = TipoLA.INVALIDO;
        String tipo = ctx.tipo().getText();
        String nome;
        
        if(ctx.tipo().registro() == null){
            //se a variavel nao é um registro
            //pegamos pelo tipo em LA o tipo em C e o tipo em Objeto LA
            switch(tipo)
            {
                case "inteiro":
                case "^inteiro":
                    tipo = "int";
                    tipoLA = TipoLA.INTEIRO;
                    break;
                case "real":
                case "^real":
                    tipo = "float";
                    tipoLA = TipoLA.REAL;
                    break;
                case "literal":
                case "^literal":
                    tipo = "char";
                    tipoLA = TipoLA.LITERAL;
                    break;
                case "logico":
                case "^logico":
                    tipoLA = TipoLA.LOGICO;
                    break;
                default:
                    declaracaoReg = true;
                    break;
            }
            
            //se nao for nenhum dos tipos originais de variaveis em LA
            //trata-se de uma declaracao de objeto de algum tipo de registro
            if(declaracaoReg)
            {
                for(Integer i = 0; i < struct.size(); i++)
                {
                    //nesse caso, nos pegamos os dados de nome e tipo do registro
                    //ja salvos anteriormente
                    //e armazenamos a variavel na tabela
                    tabela.adicionar(struct.get(i), structTipo.get(i));
                }
            }

            if(ctx.tipo().tipo_estendido() != null)
            {
                //se entra nessa verificacao é porque nao é registro
                if(ctx.tipo().tipo_estendido().pont != null)
                {
                    //se for ponteiro nós colocamos o * para ponteiro em C
                    saida.append("\t" + tipo + "* ");
                }
                else
                {
                    //se nao imprimimos o tipo normalmente
                    saida.append("\t" + tipo + " ");
                }
            }
            else
            {
                //se for registro também vamos imprimir o tipo normalmente
                saida.append("\t" + tipo + " ");
            }

            for (Integer i = 0; i < ctx.identificador().size(); i++)
            {
                //para cada identificador
                nome = ctx.identificador(i).IDENT(0).getText();
                if(registro == false)
                {
                    //se nao for registro salvamos na tabela normalmente, nome e tipo
                    tabela.adicionar(nome, tipoLA);
                }
                else if(ctx.identificador(0).dimensao() != null)
                {
                    //se nao for registro mas tem dimensao, ou seja, vetor, salvamos com um true em ponteiro
                    tabela.adicionar(nome, tipoLA, true);
                }
                else
                {
                    //se nao, precisamos adicionar na tabela no formato nomeRegistro.nomeVariavel
                    tabela.adicionar(nomeRegistro + "." + nome, tipoLA);
                }
                
                saida.append(nome);
                
                if(tipoLA == TipoLA.LITERAL)
                {
                    //se o tipo for literal, temos que declarar um vetor de char
                    //vamos usar o tamanho 80 de acordo com o exemplo
                    saida.append("[80]");
                }
                
                if(ctx.identificador(0).dimensao() != null)
                {
                    //se for um vetor, temos que imprimir o tamanho
                    saida.append(ctx.identificador(0).dimensao().getText());
                }
                
                if(i == ctx.identificador().size() - 1)
                {
                    //se for o ultimo imprimimos com ;
                    saida.append(";\n");
                }
                else
                {
                    //se nao for o ultimos usamos apenas ,
                    saida.append(", ");
                }
            }
        }
        else
        {
            //se for registro
            
            //setamos esta flag
            registro = true;
            
            saida.append("\tstruct {\n");
        
            nome = ctx.identificador(0).IDENT(0).getText();
            
            //tambem salvamos o nome do registro nesta variavel
            nomeRegistro = nome;
            
            Integer qtdVar = ctx.tipo().registro().variavel().size();
            
            for(Integer i = 0; i < qtdVar; i++)
            {
                //para cada variavel do registro vamos no visitvariavel imprimi-la
                saida.append("\t");
                visitVariavel(ctx.tipo().registro().variavel(i));
            }
            
            //essa flag é usada no visitvariavel então ja podemos seta-la novamente como false
            registro = false;
            
            saida.append("\t} "+ nome + ";\n");
            
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdleia(LAParser.CmdleiaContext ctx)
    {
        String nome = ctx.identificador(0).getText();
        TipoLA tipo = tabela.verificar(nome);
        String tipoScanf = "";
        
        //se o tipo da variavel a ser usada na leitura for literal, a funcao e diferente
        if(tipo == TipoLA.LITERAL)
        {
            saida.append("\tgets(" + nome + ");\n");
        }
        else
        {
            //se nao, a funcao é scanf mesmo
            switch (tipo)
            {
                case INTEIRO:
                    tipoScanf = "d";
                    break;
                case REAL:
                    tipoScanf = "f";
                    break;
            }
            saida.append("\tscanf(\"%" + tipoScanf + "\", &" + nome + ");\n");
        }
        
        return null;
    }
    
    /*comando do tipo printf*/
    @Override
    public Void visitCmdescreva(LAParser.CmdescrevaContext ctx)
    {
        /*quantidade de expressoes que precisa imprimir*/
        Integer qExpressao = ctx.expressao().size();
        
        for (Integer i = 0; i < qExpressao; i++){
            //aqui só vamos imprimir o print e deixar o resto pro visitexpressao
            saida.append("\tprintf(");
            visitExpressao(ctx.expressao(i));
        }
        
        return null;
    }
    
    @Override
    public Void visitExpressao(LAParser.ExpressaoContext ctx)
    {
        for (Integer i = 0; i < ctx.termo_logico().size(); i++){
            visitTermo_logico(ctx.termo_logico(i));
        }
        return null;
    }
    
    @Override
    public Void visitTermo_logico(LAParser.Termo_logicoContext ctx)
    {
        for (Integer i = 0; i < ctx.fator_logico().size(); i++){
            visitFator_logico(ctx.fator_logico(i));
        }
        return null;
    }
    
    @Override
    public Void visitFator_logico(LAParser.Fator_logicoContext ctx)
    {
        if(ctx.not != null)
        {
            //aqui se houver uma negativa, colocaremos !, o not no C
            saida.append("!");
        }
        visitParcela_logica(ctx.parcela_logica());
        
        return null;
    }
    
    @Override
    public Void visitParcela_logica(LAParser.Parcela_logicaContext ctx)
    {
        visitExp_relacional(ctx.exp_relacional());
        return null;
    }
    
    @Override
    public Void visitExp_relacional(LAParser.Exp_relacionalContext ctx)
    {
        if(ctx.op_relacional(0) != null)
        {
            //se chegarmos nesse ponto e houver um operador do tipo relacional
            
            //primeiro imprimimos a primeira parte da comparacao
            saida.append(ctx.exp_aritmetica(0).getText() + " ");
            
            if(ctx.op_relacional(0).getText().equals("="))
            {
                //se o operador for de igual, precisamos trocá-lo para o igual no C
                saida.append("==");
            }
            else if(ctx.op_relacional(0).getText().equals("<>"))
            {
                //mesma coisa para o operador de diferente
                saida.append("!=");
            }
            else
            {
                //se nao for esses casos, o operador sera o mesmo
                saida.append(ctx.op_relacional(0).getText());
            }
            
            //agora imprimimos a segunda parte da comparacao
            saida.append(" " + ctx.exp_aritmetica(1).getText());
        }
        else
        {
            //se nao houver operador relacional, só seguimos
            for (Integer i = 0; i < ctx.exp_aritmetica().size(); i++){
                visitExp_aritmetica(ctx.exp_aritmetica(i));
            }
        }
        return null;
    }
    
    @Override
    public Void visitExp_aritmetica(LAParser.Exp_aritmeticaContext ctx)
    {   
        TipoLA tipo = TipoLA.INVALIDO;
        if(ctx.termo(0).fator(0).parcela(0).parcela_unario() != null)
        {
            //se houver parcela do tipo unario
            if(ctx.termo(0).fator(0).parcela(0).parcela_unario().identificador() != null){
                //se houver um identificador
                
                //pegamos o nome da variavel
                String nome = ctx.termo(0).fator(0).parcela(0).parcela_unario().identificador().IDENT(0).getText();
                
                if(ctx.termo(0).fator(0).parcela(0).parcela_unario().identificador().reg != null)
                {
                    //se for registro
                    //precisamos pegar a parte depois do .
                    nome = ctx.termo(0).fator(0).parcela(0).parcela_unario().identificador().IDENT(1).getText();
                }
                
                //pegamos o tipo da variavel
                tipo = tabela.verificar(nome);
                
                //colocamos o tipo no formato do printf
                String tipoPrintf = "";
                switch (tipo)
                {
                    case INTEIRO:
                        tipoPrintf = "d";
                        break;
                    case REAL:
                        tipoPrintf = "f";
                        break;
                    case LITERAL:
                        tipoPrintf = "s";
                        break;
                }
                saida.append("\"%" + tipoPrintf + "\"");
                
                //imprimimos a virgula para colocar a variavel
                saida.append(", ");
            }
            else
            {
                //se nao houver identificador
                
                //aqui trata-se de chamar uma funcao se ela tiver sido declarada
                //ou seja, o tipo dela ja tera sido salvo nessa variavel
                if(tipoRetFunc != "")
                {
                    //vamos pegar o tipo da variavel no tipo objeto TipoLA
                    switch(tipoRetFunc)
                    {
                        case "inteiro":
                            tipo = TipoLA.INTEIRO;
                            break;
                        case "real":
                            tipo = TipoLA.REAL;
                            break;
                        case "literal":
                            tipo = TipoLA.LITERAL;
                            break;
                        case "logico":
                            tipo = TipoLA.LOGICO;
                            break;
                    }
                    
                    //em seguida, vamos transformar esse tipo que pegamos
                    //no tipo para ser usado no printf
                    String tipoPrintf = "";
                    switch (tipo)
                    {
                        case INTEIRO:
                            tipoPrintf = "d";
                            break;
                        case REAL:
                            tipoPrintf = "f";
                            break;
                        case LITERAL:
                            tipoPrintf = "s";
                            break;
                    }
                    saida.append("\"%" + tipoPrintf + "\"");
                    saida.append(", ");
                }
            }
        }
        
        //aqui, temos o caso especial quando pega uma comparacao
        //temos = e precisamos de ==
        String texto = ctx.getText();
        Integer index = -1;
        //vamos procurar no texto
        index = texto.indexOf('=');
        //se tiver achado
        if(index != -1)
        {
            //vamos trocar = por ==
            String [] textoPartes = texto.split("=");
            saida.append(textoPartes[0] + "==" + textoPartes[1]);
        }
        else
        {
            saida.append(texto);
        }
        
        saida.append(");\n");
        
        return null;
    }
    
    @Override
    public Void visitTermo(LAParser.TermoContext ctx)
    {
        for (Integer i = 0; i < ctx.fator().size(); i++){
            visitFator(ctx.fator(i));
        }
        return null;
    }
    
    @Override
    public Void visitFator(LAParser.FatorContext ctx)
    {
        for (Integer i = 0; i < ctx.parcela().size(); i++){
            visitParcela(ctx.parcela(i));
        }
        return null;
    }
    
    @Override
    public Void visitParcela(LAParser.ParcelaContext ctx)
    {
        //aqui, so vemos se a parcela em questao é do tipo unario ou nao unario
        if(ctx.parcela_unario() != null)
        {
            visitParcela_unario(ctx.parcela_unario());
        }
        else
        {
            visitParcela_nao_unario(ctx.parcela_nao_unario());
        }
        return null;
    }
    
    @Override
    public Void visitParcela_unario(LAParser.Parcela_unarioContext ctx)
    {
        
        if(ctx.identificador() != null)
        {
            //se houver um identificador
            String nome;
            if(ctx.identificador().reg == null)
            {
                //se nao for estrutura imprimimos normal
                nome = ctx.identificador().IDENT(0).getText();
            }
            else
            {
                //se for estruturas temos que usar o nome da estrutura, ponto, nome da variavel
                nome = ctx.identificador().IDENT(0).getText() + "." + ctx.identificador().IDENT(1).getText();
            }
        }
        else if(ctx.IDENT() != null)
        {
            //chamada de uma funcao
            System.out.print(ctx.getText());
            TipoLA tipoLA = TipoLA.INVALIDO;
            
            //o tipo de retorno da funcao ja eh salvo assim que ela e criada
            switch(tipoRetFunc)
            {
                //pegamos o tipo no formato tipoLA
                case "inteiro":
                    tipoLA = TipoLA.INTEIRO;
                    break;
                case "real":
                    tipoLA = TipoLA.REAL;
                    break;
                case "literal":
                    tipoLA = TipoLA.LITERAL;
                    break;
                case "logico":
                    tipoLA = TipoLA.LOGICO;
                    break;
            }
            String tipoPrintf = "";

            switch (tipoLA)
            {
                //a partir do tipo pegamos a letra pra ser usada no printf
                case INTEIRO:
                    tipoPrintf = "d";
                    break;
                case REAL:
                    tipoPrintf = "f";
                    break;
                case LITERAL:
                    tipoPrintf = "s";
                    break;
            }

            saida.append("%" + tipoPrintf);
        }
        return null;
    }
    
    @Override
    public Void visitCmdse(LAParser.CmdseContext ctx)
    {
        saida.append("\tif(");
        //buscamos a impressao da expressao no visitexpressao
        visitExpressao(ctx.expressao());
        saida.append("){\n");

        saida.append("\t");
        //aqui buscamos a impressao dos comandos no se
        visitCmd(ctx.cmd(0));
        
        saida.append("\t}\n");
        
        if(ctx.cmd().size() > 1)
        {
            //aqui eh para o caso de haver else
            saida.append("\telse{\n");
            saida.append("\t");
            visitCmd(ctx.cmd(1));
            saida.append("\t}\n");
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdpara(LAParser.CmdparaContext ctx)
    {
        //aqui imprimimos o for no formato em C
        saida.append("\tfor (");
        saida.append(ctx.IDENT().getText() + " = ");
        saida.append(ctx.exp_aritmetica(0).getText() + "; ");
        saida.append(ctx.IDENT().getText() + " <= ");
        saida.append(ctx.exp_aritmetica(1).getText() + "; ");
        saida.append(ctx.IDENT().getText() + "++){\n");
        
        for(Integer i = 0; i < ctx.cmd().size(); i++)
        {
            //aqui imprimimos os comandos
            saida.append("\t");
            visitCmd(ctx.cmd(i));
        }
        
        saida.append("\t}\n\n");
        
        return null;
    }
    
    @Override
    public Void visitCmdenquanto(LAParser.CmdenquantoContext ctx)
    {
        //aqui imprimimos o enquanto em C (while)
        saida.append("\twhile(");
        saida.append(ctx.expressao().getText() + "){\n");
        //impressao dos comandos
        ctx.cmd().forEach(dec -> visitCmd(dec));
        saida.append("\t}\n");
        return null;
    }
    
    @Override
    public Void visitCmdatribuicao(LAParser.CmdatribuicaoContext ctx)
    {
        saida.append("\t");
        
        if(ctx.identificador().dimensao() == null){
            //se nao for um vetor
            
            if(tabela.verificar(ctx.identificador().getText()) != TipoLA.LITERAL)
            {
                //se nao for literal
                if(ctx.pont != null)
                {
                    //se for ponteiro imprimimos o *
                    saida.append("*");
                }
                saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
            }
            else
            {
                //se for literal (string) a atribuicao eh feita com strcpy
                saida.append("strcpy(" + ctx.identificador().getText() + ", " + ctx.expressao().getText() + ");\n");
            }
        }
        else
        {
            //se for vetor
            String nome = ctx.identificador().IDENT(0).getText();
            if(ctx.identificador().reg != null)
            {
                //se nao for registro
                nome = ctx.identificador().IDENT(1).getText();
            }
            if(tabela.verificar(nome) != TipoLA.LITERAL)
            {
                //se for ponteiro imprime *
                if(ctx.pont != null)
                {
                    saida.append("*");
                }
                saida.append(ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
            }
            else
            {
                //se for literal usa strcpy
                saida.append("strcpy(" + ctx.identificador().getText() + ", " + ctx.expressao().getText() + ");\n");
            }
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdcaso(LAParser.CmdcasoContext ctx)
    {
        //base do switch case
        saida.append("\tswitch (");
        saida.append(ctx.exp_aritmetica().getText() + "){\n");
        
        for(Integer i = 0; i < ctx.selecao().item_selecao().size(); i++)
        {
            //imprime os casos no intervalo
            //os numeros do intervalo ja foram salvos anteriormente
            numeroIntervalo1 = 0;
            numeroIntervalo2 = 0;
            
            visita1 = true;
            
            //na primeira visita vamos pegar as constantes
            visitItem_selecao(ctx.selecao().item_selecao(i));
            
            visita1 = false;
            
            if(numeroIntervalo2 != 0)
            {
                //se realmente tiver um intervalo
                for(Integer j = numeroIntervalo1; j <= numeroIntervalo2; j++)
                {
                    saida.append("\n\tcase " + j + ":\n");
                }
            }
            else
            {
                //se for um unico numero
                saida.append("\n\tcase " + numeroIntervalo1 + ":\n");
            }
            
            //na segunda visita pega-se os comandos
            visitItem_selecao(ctx.selecao().item_selecao(i));
            
            saida.append("\tbreak;\n");
        }
        
        if(ctx.cmd(0) != null)
        {
            //caso default
            saida.append("\n\tdefault:\n");
            //pegamos os comandos
            ctx.cmd().forEach(dec -> visitCmd(dec));
        }
        
        saida.append("\t}\n\n");
        
        return null;
    }
    
    @Override
    public Void visitItem_selecao(LAParser.Item_selecaoContext ctx)
    {
        //na primeira visita pegamos as constantes
        if(visita1 == true)
        {
            visitConstantes(ctx.constantes());
        }
        //na segunda visita pegamos os comandos
        else
        {
            ctx.cmd().forEach(dec -> visitCmd(dec));
        }
        return null;
    }
    
    @Override
    public Void visitConstantes(LAParser.ConstantesContext ctx)
    {
        visitNumero_intervalo(ctx.numero_intervalo(0));
        return null;
    }
    
    @Override
    public Void visitNumero_intervalo(LAParser.Numero_intervaloContext ctx)
    {
        //pegamos o intervalo para o switch case
        if(ctx.NUM_INT(0) != null)
        {
            numeroIntervalo1 = Integer.parseInt(ctx.NUM_INT(0).getText());
        }
        if(ctx.NUM_INT(1) != null)
        {
            numeroIntervalo2 = Integer.parseInt(ctx.NUM_INT(1).getText());
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdfaca(LAParser.CmdfacaContext ctx)
    {
        //comando do tipo do while
        saida.append("\tdo{\n");
        
        //pegamos o comando
        ctx.cmd().forEach(dec -> visitCmd(dec));
        saida.append("\t}\n");
        saida.append("\twhile(");
        
        //pegamos a expressao
        visitExpressao(ctx.expressao());
        
        return null;
    }
    
    @Override 
    public Void visitCmdchamada(LAParser.CmdchamadaContext ctx)
    {
        //aqui é a chamada para uma funcao, imprimimos o nome da funcao e o parametro
        saida.append("\t" + ctx.IDENT() + "(" + ctx.expressao(0).getText() + ");\n");
        return null;
    }
    
    @Override
    public Void visitCmdretorne(LAParser.CmdretorneContext ctx)
    {
        //aqui imprimimos simplesmente a expressao que a funcao retorna
        saida.append("\treturn ");
        
        saida.append(ctx.expressao().getText());
        
        saida.append(";\n");
        
        return null;
    }
}
