package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.LAParser.ExpressaoContext;
import br.ufscar.dc.compiladores.LA.LAParser.IdentificadorContext;
import static br.ufscar.dc.compiladores.LA.LASemanticoUtils.adicionarErroSemantico;
import br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Semantico extends LABaseVisitor<TipoLA>{
    public TabelaDeSimbolos tabela;
    FileOutputStream saida;
    
    public TabelaDeSimbolos getTabela() {
        return tabela;
    }
        
    @Override
    public TipoLA visitPrograma(LAParser.ProgramaContext ctx) {
        //Inicializando tabela de simbolos
        tabela = new TabelaDeSimbolos();
        
        for(var cmd: ctx.corpo().cmd()){

            //Se nao for uma funcao, nao deve ter return
            if(cmd.cmdretorne()!=null){
                String mensagem="comando retorne nao permitido nesse escopo";
                LASemanticoUtils.adicionarErroSemantico(cmd.cmdretorne().getStart(), mensagem);
            }

        }
                    
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitDeclaracao_global(LAParser.Declaracao_globalContext ctx) { 
        
        //Verificando se possui uma funcao nessa declaracao
        if(ctx.inicioFuncao!=null || ctx.inicioProc!=null  ){
            
            TabelaDeSimbolos tabelaSimbolosOriginal=tabela;
            
            String nomeFuncao = ctx.IDENT().getText();
            
            //Criando uma subtabela para adicionar os elementos da funcao
            TabelaDeSimbolos tabelaFuncao= new TabelaDeSimbolos();
            
            for (var parametro : ctx.parametros().parametro()) { 
                System.out.println("parametro: "+ parametro.identificador(0).getText()); 
                String nomeVar=parametro.identificador(0).getText();
                String tipoVar;
                
                //Obtendo o tipo da variavel
                if(parametro.tipo_estendido().tipo_basico_ident().tipo_basico()!=null)
                    tipoVar =parametro.tipo_estendido().tipo_basico_ident().tipo_basico().getText();
                else //Caso seja um registro, vai entrar aqui
                    tipoVar =parametro.tipo_estendido().tipo_basico_ident().IDENT().getText();
                
                TipoLA tipoVarLA=LASemanticoUtils.verificaTipoVar(tabela, tipoVar, null);

                //Tratando o recebimento de um registro como parametro
                TabelaDeSimbolos tipoRegistro=null;
                if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO){
                    tipoRegistro = tabela.obtemSubTabela(tipoVar);
                }
                                
                tabelaFuncao.adicionar(nomeVar, tipoVarLA, false, tipoRegistro);  
            }
            
            tabela=tabelaFuncao;
            //Chamando o filho para ele usar da tabela que foi criada com os parametros recebidos pela funcao
            TipoLA retornoFilho= visitChildren(ctx); 
            //Retornando os valores originais da tabela, os valores criados dentro da ffuncao ja podem ser descartados pois nao devem contar em outros escopos
            tabela=tabelaSimbolosOriginal;
            
            TipoLA tipoRetornoLA;
                    
            //Caso seja uma funcao, entao a variavel referente a funcao na tabela sera do tipo do retorno da funcao
            if(ctx.inicioFuncao!=null){
                 String tipoFuncao=ctx.tipo_estendido().getText();
                 tipoRetornoLA=LASemanticoUtils.verificaTipoVar(tabela, tipoFuncao, null);
            
            }else{
                 tipoRetornoLA= br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.PROCEDIMENTO;
                 //Aproveitando para ver se nao tem nenhum return nesse procedimento 
                 for(var cmd: ctx.cmd()){

                    //Se nao for uma funcao, nao deve ter return
                    if(cmd.cmdretorne()!=null){
                        String mensagem="comando retorne nao permitido nesse escopo";
                        LASemanticoUtils.adicionarErroSemantico(cmd.cmdretorne().getStart(), mensagem);
                    }

                }
            }
            //inserindo na tabela principal a funcao que ira ser do tipo de seu retorno
            tabela.adicionar(nomeFuncao, tipoRetornoLA, false, tabelaFuncao);
            
            //Agora sim ja pode retornar o filho
            return retornoFilho;
            
        }
        else{
            //Verificando todos comandos do escopo
            for(var cmd: ctx.cmd()){

                //Se nao for uma funcao, nao deve ter return
                if(cmd.cmdretorne()!=null){
                    String mensagem="comando retorne nao permitido nesse escopo";
                    LASemanticoUtils.adicionarErroSemantico(cmd.cmdretorne().getStart(), mensagem);
                }

            }
        }
        return visitChildren(ctx); 
    
    }
    
    @Override public TipoLA visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {    
        //Se for somente uma definicao de tipo, devera entrar aqui para criar a subtabela do registro em questao
        if (ctx.tipo()!=null &&  ctx.tipo().registro()!=null){

                //salvando os valores anteriores da tabela
                TabelaDeSimbolos tabelaSimbolosOriginal=tabela;

                //A ideia aqui eh criar uma 
                tabela= new TabelaDeSimbolos();

                TipoLA retornoFilho= visitChildren(ctx); 

                //neste momento "tabela" possui somente as variaveis da estrutura
                TabelaDeSimbolos tabelaRegistro=tabela;

                tabela=tabelaSimbolosOriginal;

                //inserindo as tabelas de cada registro na tabela geral
                String nomeVar=ctx.IDENT().getText();
                //System.out.println("registro: "+ nomeVar);
                Boolean tipoP = false;
                tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO, tipoP, tabelaRegistro );

                //apos visitar o filho, a tabela devera ter somente as variaveis referentes a este registro
                return retornoFilho;

            }
        else
        //Quando tiver um tipo basico entao uma declaracao de constante foi feita
        if( ctx.tipo_basico()!=null){
            String tipoVar=ctx.tipo_basico().getText();
            String nomeVar=ctx.IDENT().getText();
            
            //Obtendo o tipo da constante
            TipoLA tipoVarLA = LASemanticoUtils.verificaTipoVar(tabela, tipoVar, null); 
            
            if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.INVALIDO){
                String mensagem="tipo " + tipoVar  + " nao declarado";
                LASemanticoUtils.adicionarErroSemantico(ctx.tipo_basico().getStart(), mensagem);
            }
            
            tabela.adicionar(nomeVar, tipoVarLA);  

        }     

        return visitChildren(ctx);

    }
    
    @Override public TipoLA visitVariavel(LAParser.VariavelContext ctx) {
        //Se for um registro entao pode ter varias variaveis dentro, e isso sera tratado para cada uma delas posteriormente na recursao          
        if (ctx.tipo().registro()!=null ){
            
            //salvando os valores anteriores da tabela
            TabelaDeSimbolos tabelaSimbolosOriginal=tabela;

            //A ideia aqui eh criar uma 
            tabela= new TabelaDeSimbolos();

            //Entrando na criacao da subtabela            
            TipoLA retornoFilho= visitChildren(ctx); 
                                   
            //neste momento "tabela" possui somente as variaveis da estrutura
            TabelaDeSimbolos tabelaRegistro=tabela;
            
            tabela=tabelaSimbolosOriginal;
            
            //inserindo as tabelas de cada registro na tabela geral
            for (var parametro : ctx.identificador()) { 
                String nomeVar=parametro.getText();
                //System.out.println("nomeVar: "+ nomeVar);
                Boolean tipoP = false;
                tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO, tipoP, tabelaRegistro ); 
            }
           
            
            //apos visitar o filho, a tabela devera ter somente as variaveis referentes a este registro
            return retornoFilho;
 
        }
        
        //Quando for uma variavel do tipo de um registro, essa variavel vai ser alterada posteriormente
        TabelaDeSimbolos tipoRegistro=null;
        
        //Obtendo o tipo da variavel (em texto)
        String tipoVar=ctx.tipo().tipo_estendido().tipo_basico_ident().getText();
        
        //Verificando se a variavel em questao eh na verdade um ponteiro, caso seja uma flag sera adicionada na tabela
        Boolean tipoP = ctx.tipo().tipo_estendido().pont != null;
        
        //Andando em todas variaveis de um identificador
         for (var identificador : ctx.identificador()) { 
            String nomeVar=identificador.getText();
            //System.out.println("identificador: "+ nomeVar); 
            if(tabela.existe(nomeVar) == true){
                String mensagem="identificador " + nomeVar  + " ja declarado anteriormente";
                LASemanticoUtils.adicionarErroSemantico(identificador.getStart(), mensagem);
            }
            
            //Obtendo o tipo da variavel (no tipo LA)
            TipoLA tipoVarLA = LASemanticoUtils.verificaTipoVar(tabela, tipoVar, tipoRegistro); 
            
            //Verificando se o tipo eh invalido, caso seja um erro sera reportado
            if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.INVALIDO){
                String mensagem="tipo " + tipoVar  + " nao declarado";
                LASemanticoUtils.adicionarErroSemantico(identificador.getStart(), mensagem);
            }
            //Verificando se eh um registro para obter a tabela interna
            else if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO){
                tipoRegistro = tabela.obtemSubTabela(tipoVar);
            }
            
            //Verificando se eh um vetor
            if(identificador.dimensao().exp_aritmetica(0)!=null){
                int tamanhoVetor;
                //Obtendo tamanho do vetor
                if(identificador.dimensao().exp_aritmetica(0).termo(0).fator(0).parcela(0).parcela_unario().NUM_INT()!=null)
                    //Mantendo por motivos legados, nao eh necessario saber o tamanho do vetor
                    tamanhoVetor= Integer.parseInt(identificador.dimensao().exp_aritmetica(0).termo(0).fator(0).parcela(0).parcela_unario().NUM_INT().toString()); 
                else
                    //Usar o vetor como 1 ja eh o suficiente uma vez que nao vamos verificar se a posicao escolhida existe
                    tamanhoVetor=1;
                nomeVar=identificador.IDENT(0).getText();
                String nomeVarPadrao=nomeVar+"[";
                
                //Adicionando todas posicoes do vetor como se fossem varias variaveis
                for(int i =0; i<tamanhoVetor; i++){
                    nomeVar=nomeVarPadrao+Integer.toString(i)+"]"; 
                    tabela.adicionar(nomeVar, tipoVarLA, tipoP,tipoRegistro);
                }

            }
            //Caso nao seja um vetor, somente uma instancia da variavel sera inserida na tabela
            else
                tabela.adicionar(nomeVar, tipoVarLA, tipoP,tipoRegistro);  
        }

        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitCmdleia(LAParser.CmdleiaContext ctx) {
        
        //Verificando se todos identificadores/parametros que sao chamados no leia existem na tabela
        for (var identificador : ctx.identificador()) {
                    Boolean existeIdentificador;
                    existeIdentificador=tabela.existe(identificador);
                    if(existeIdentificador == false){
                        String mensagem="identificador " + identificador.getText()  + " nao declarado";
                        LASemanticoUtils.adicionarErroSemantico(identificador.start, mensagem);
                    }
            }
        
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitCmdescreva(LAParser.CmdescrevaContext ctx) {
        //Verificando se os tipos das variaveis dentro do comando sao consistentes
        //Caso nao sejam a verificacao do tipo ja ira inserir o erro na lista de erros
        for (var expressao : ctx.expressao()) {
            LASemanticoUtils.verificarTipo(tabela, expressao);
        }
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitCmdatribuicao(LAParser.CmdatribuicaoContext ctx) {
        var identificador = ctx.identificador();
        var expressao = ctx.expressao();
        
        //Verificando se o identificador esta na tabela antes de prosseguir
        if(tabela.existe(identificador)==false){
            String mensagem="identificador " + identificador.getText()+ " nao declarado";
            LASemanticoUtils.adicionarErroSemantico(identificador.start, mensagem );
            //Se o identificador nao estiver na tabela, os demais erros da atribuicao serao descartados
            return visitChildren(ctx);
        }
        //Obtendo o tipo do identificador
        TipoLA tipoIdentificador=tabela.verificar(identificador);
        //Obtendo o tipo da expressao, caso exista algum erro interno nela o erro sera inserido dentro da lista de erros
        TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, expressao);

        //Nao devemos ter erros quando o identificador for real e a expressao inteiro ou viceversa
        if(tipoExpressao==TabelaDeSimbolos.TipoLA.REAL || tipoExpressao==TabelaDeSimbolos.TipoLA.INTEIRO){
            if(tipoIdentificador==TabelaDeSimbolos.TipoLA.REAL || tipoIdentificador==TabelaDeSimbolos.TipoLA.INTEIRO){
                tipoExpressao=tipoIdentificador;
            }
        }
        
        //Se o tipo for invalido ira anotar oerro
        if(tipoExpressao==TabelaDeSimbolos.TipoLA.INVALIDO || tipoIdentificador!=tipoExpressao){
           //Verificando se o identificador eh um ponteiro para responder a mensagem de erro de forma correta
            if(tabela.verificarPonteiro(identificador)){
                LASemanticoUtils.adicionarErroSemantico(identificador.start, "atribuicao nao compativel para ^" + identificador.getText());
           }
           else{
                LASemanticoUtils.adicionarErroSemantico(identificador.start, "atribuicao nao compativel para " + identificador.getText());
           }
        }
        
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitCmdenquanto(LAParser.CmdenquantoContext ctx) { 
       
        //Verificando se a expressao dentro do enquanto esta correta
        TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, ctx.expressao());
        
        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitCmdpara(LAParser.CmdparaContext ctx) {
        
        //Adicionando na tabela o iterador "i" para que ele seja encontrado quando for utilizado dentro da repeticao
        tabela.adicionar(ctx.IDENT().getText(), TabelaDeSimbolos.TipoLA.INTEIRO);  
        return visitChildren(ctx); 
    }

    @Override public TipoLA visitParcela_unario(LAParser.Parcela_unarioContext ctx) {
        //Verificando se existe algum identificador (sera uma funcao ou procedimento caso exista)
        if (ctx.IDENT() != null) {
            String nomeFuncao = ctx.IDENT().getText();
            //Essa subtabela eh a tabela da funcao, ou seja, os elementos dessa tabela sao as variaveis parametro da funcao, e seus tipos
            TabelaDeSimbolos subTabela=tabela.obtemSubTabela(nomeFuncao);

            //Verificando se o numero de parametros(expressoes) passadas sao do mesmo tamanho do esperado por aquela funcao
            if (subTabela.obtemTamanhoTabela() == ctx.expressao().size()) {
                
                //Cada expressao eh um parametro aqui, todos parametros/expressoes serao comparadas aqui
                for (int numExpressao = 0; numExpressao < ctx.expressao().size(); numExpressao++) {
                   ExpressaoContext expressao = ctx.expressao(numExpressao);
                   
                   //Obtendo tipo da expressao passada e do parametro da funcao 
                   TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, expressao);
                   TipoLA tipoParametro = subTabela.verificar(numExpressao);
                   
                   //Verificando se o tipo da expressao bate com o parametro da funcao
                   if(tipoExpressao!=tipoParametro){
                       LASemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(), "incompatibilidade de parametros na chamada de " + nomeFuncao);
                   }
                   
                }
            } else {
                LASemanticoUtils.adicionarErroSemantico(ctx.IDENT().getSymbol(), "incompatibilidade de parametros na chamada de " + nomeFuncao);
            }
        }
        
        return visitChildren(ctx);
    }
}