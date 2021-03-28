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
    
    public LAGeradorC()
    {
        saida = new StringBuilder();
        this.tabela = new TabelaDeSimbolos();
    }

    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx) {
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
        ctx.decl_local_global().forEach(dec -> visitDecl_local_global(dec));
        return null;
    }
    
    @Override
    public Void visitDecl_local_global(LAParser.Decl_local_globalContext ctx)
    {
        visitDeclaracao_local(ctx.declaracao_local());
        return null;
    }
    
    @Override
    public Void visitDeclaracao_local(LAParser.Declaracao_localContext ctx)
    {
        if(ctx.variavel() != null)
        {
            visitVariavel(ctx.variavel());
        }
        else if(ctx.valor_constante() != null)
        {
            saida.append("#define " + ctx.IDENT().getText() + " " + ctx.valor_constante().getText() + "\n\n");
        }
        
        return null;
    }
    
    @Override
    public Void visitVariavel(LAParser.VariavelContext ctx)
    {
        TipoLA tipoLA = TipoLA.INVALIDO;
        String tipo = ctx.tipo().getText();
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
                tipo = "char";
                tipoLA = TipoLA.LITERAL;
                break;
            case "logico":
                tipoLA = TipoLA.LOGICO;
                break;
        }

        saida.append("\t" + tipo + " ");
        
        String nome;
        for (Integer i = 0; i < ctx.identificador().size(); i++)
        {
            nome = ctx.identificador(i).IDENT(0).getText();
            tabela.adicionar(nome, tipoLA);
            saida.append(nome);
            if(tipoLA == TipoLA.LITERAL)
            {
                saida.append("[80]");
            }
            if(i == ctx.identificador().size() - 1)
            {
                saida.append(";\n");
            }
            else
            {
                saida.append(", ");
            }
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdleia(LAParser.CmdleiaContext ctx)
    {
        String nome = ctx.identificador(0).getText();
        TipoLA tipo = tabela.verificar(nome);
        String tipoScanf = "";
        
        if(tipo == TipoLA.LITERAL)
        {
            saida.append("\tgets(" + nome + ");\n");
        }
        else
        {
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
        /*lista com os nomes das variaveis que vai imprimir*/
        variaveisLeia = new ArrayList<String>();
        
        /*quantidade de expressoes que precisa imprimir*/
        Integer qExpressao = ctx.expressao().size();
        
        saida.append("\tprintf(\"");
        
        /*visita cada expressao, e imprime*/
        for (Integer i = 0; i < qExpressao; i++){
            visitExpressao(ctx.expressao(i));
        }
        
        /*if(operadorEscreva.size() == 0){
            for(Integer i = 0; i < variaveisLeia.size(); i++)
            {
                String nome = variaveisLeia.get(i);
                TipoLA tipo = tabela.verificar(nome);
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

                saida.append("%" + tipoPrintf);

            }
        }
        else{
            String nome = variaveisLeia.get(0);
            TipoLA tipo = tabela.verificar(nome);
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

            saida.append("%" + tipoPrintf);
        }*/
        
        saida.append("\"");
        
        if(variaveisLeia.size() != 0)
        {
            saida.append(", ");
        }
        
        Integer j = 0;
        for(Integer i = 0; i < variaveisLeia.size(); i++)
        {
            String nome = variaveisLeia.get(i);
            saida.append(nome);
            
            if(j < operadorEscreva.size()){
                saida.append(operadorEscreva.get(j++));
            }
        }
        
        saida.append(");\n");
        
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
            saida.append("!");
            /*if(ctx.parcela_logica().exp_relacional().op_relacional(0) != null)
            {
                saida.append("(");
                saida.append(ctx.parcela_logica().exp_relacional().exp_aritmetica(0).getText());
                saida.append(ctx.parcela_logica().exp_relacional().op_relacional(0).getText());
                saida.append(ctx.parcela_logica().exp_relacional().exp_aritmetica(1).getText());
                saida.append(")");
            }*/
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
            saida.append(ctx.exp_aritmetica(0).getText() + " ");
            if(ctx.op_relacional(0).getText().equals("="))
            {
                saida.append("==");
            }
            else if(ctx.op_relacional(0).getText().equals("<>"))
            {
                saida.append("!=");
            }
            else
            {
                saida.append(ctx.op_relacional(0).getText());
            }
            saida.append(" " + ctx.exp_aritmetica(1).getText());
        }
        else
        {
            for (Integer i = 0; i < ctx.exp_aritmetica().size(); i++){
                visitExp_aritmetica(ctx.exp_aritmetica(i));
            }
        }
        return null;
    }
    
    @Override
    public Void visitExp_aritmetica(LAParser.Exp_aritmeticaContext ctx)
    {
        operadorEscreva = new ArrayList<String>();
        
        if(ctx.op1() != null)
        {
            for (Integer i = 0; i < ctx.op1().size(); i++)
            {
                operadorEscreva.add(ctx.op1(i).getText());
            }
        }
        
        visita1 = true;
        
        for (Integer i = 0; i < ctx.termo().size(); i++){
            visitTermo(ctx.termo(i));
        }
        
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
            String nome = ctx.identificador().IDENT(0).getText();
            variaveisLeia.add(nome);
            
            if(operadorEscreva.isEmpty()){
                for(Integer i = 0; i < variaveisLeia.size(); i++)
                {
                    TipoLA tipo = tabela.verificar(nome);
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

                    saida.append("%" + tipoPrintf);

                }
            }
            else{
                if(visita1)
                {
                    TipoLA tipo = tabela.verificar(nome);
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

                    saida.append("%" + tipoPrintf);
                    
                    visita1 = false;
                }
            }
        }
        return null;
    }
    
    @Override
    public Void visitParcela_nao_unario(LAParser.Parcela_nao_unarioContext ctx)
    {
        if(ctx.CADEIA() != null)
        {
            String texto = ctx.CADEIA().getText();
            String [] textoSemAspas = texto.split("\"");
            saida.append(textoSemAspas[1]);
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdse(LAParser.CmdseContext ctx)
    {
        saida.append("\tif(");
        visitExpressao(ctx.expressao());
        saida.append("){\n");
        
        /*for (Integer i = 0; i < ctx.cmd().size(); i++)
        {
            saida.append("\t");
            visitCmd(ctx.cmd(i));
        }*/
        saida.append("\t");
        visitCmd(ctx.cmd(0));
        
        saida.append("\t}\n");
        
        if(ctx.cmd().size() > 1)
        {
            saida.append("\telse{\n");
            saida.append("\t");
            //ctx.cmd().forEach(dec -> visitCmd(dec));
            visitCmd(ctx.cmd(1));
            saida.append("\t}\n");
        }
        
        return null;
    }
    
    @Override
    public Void visitCmdpara(LAParser.CmdparaContext ctx)
    {
        saida.append("\tfor (");
        saida.append(ctx.IDENT().getText() + " = ");
        saida.append(ctx.exp_aritmetica(0).getText() + "; ");
        saida.append(ctx.IDENT().getText() + " <= ");
        saida.append(ctx.exp_aritmetica(1).getText() + "; ");
        saida.append(ctx.IDENT().getText() + "++){\n");
        
        for(Integer i = 0; i < ctx.cmd().size(); i++)
        {
            saida.append("\t");
            ctx.cmd().forEach(dec -> visitCmd(dec));
        }
        
        saida.append("\t}\n\n");
        
        return null;
    }
    
    @Override
    public Void visitCmdenquanto(LAParser.CmdenquantoContext ctx)
    {
        saida.append("\twhile(");
        saida.append(ctx.expressao().getText() + "){\n");
        ctx.cmd().forEach(dec -> visitCmd(dec));
        saida.append("\t}\n");
        return null;
    }
    
    @Override
    public Void visitCmdatribuicao(LAParser.CmdatribuicaoContext ctx)
    {
        saida.append("\t" + ctx.identificador().getText() + " = " + ctx.expressao().getText() + ";\n");
        return null;
    }
    
    @Override
    public Void visitCmdcaso(LAParser.CmdcasoContext ctx)
    {
        saida.append("\tswitch (");
        saida.append(ctx.exp_aritmetica().getText() + "){\n");
        
        for(Integer i = 0; i < ctx.selecao().item_selecao().size(); i++)
        {
            numeroIntervalo1 = 0;
            numeroIntervalo2 = 0;
            visita1 = true;
            
            visitItem_selecao(ctx.selecao().item_selecao(i));
            
            visita1 = false;
            
            if(numeroIntervalo2 != 0)
            {
                for(Integer j = numeroIntervalo1; j <= numeroIntervalo2; j++)
                {
                    saida.append("\n\tcase " + j + ":\n");
                }
            }
            else
            {
                saida.append("\n\tcase " + numeroIntervalo1 + ":\n");
            }
            
            visitItem_selecao(ctx.selecao().item_selecao(i));
            
            saida.append("\tbreak;\n");
        }
        
        if(ctx.cmd(0) != null)
        {
            saida.append("\n\tdefault:\n");
            ctx.cmd().forEach(dec -> visitCmd(dec));
        }
        
        saida.append("\t}\n\n");
        
        return null;
    }
    
    /*@Override
    public Void visitSelecao(LAParser.SelecaoContext ctx)
    {
        ctx.item_selecao().forEach(dec -> visitItem_selecao(dec));
        return null;
    }*/
    
    @Override
    public Void visitItem_selecao(LAParser.Item_selecaoContext ctx)
    {
        if(visita1 == true)
        {
            visitConstantes(ctx.constantes());
        }
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
        saida.append("\tdo{\n");
        ctx.cmd().forEach(dec -> visitCmd(dec));
        saida.append("\t}\n");
        saida.append("\twhile(");
        visitExpressao(ctx.expressao());
        saida.append(");\n");
        
        return null;
    }
}
