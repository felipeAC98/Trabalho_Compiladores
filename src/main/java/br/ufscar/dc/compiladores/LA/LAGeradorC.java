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
        visitVariavel(ctx.variavel());
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
    
    @Override
    public Void visitCmdescreva(LAParser.CmdescrevaContext ctx)
    {
        variaveisLeia = new ArrayList<String>();
        Integer qExpressao = ctx.expressao().size();
        saida.append("\tprintf(\"");
        
        for (Integer i = 0; i < qExpressao; i++){
            visitExpressao(ctx.expressao(i));
        }
        
        if(operadorEscreva.size() == 0){
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
        }
        
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
        for (Integer i = 0; i < ctx.exp_aritmetica().size(); i++){
            visitExp_aritmetica(ctx.exp_aritmetica(i));
        }
        return null;
    }
    
    @Override
    public Void visitExp_aritmetica(LAParser.Exp_aritmeticaContext ctx)
    {
        operadorEscreva = new ArrayList<String>();
        
        for (Integer i = 0; i < ctx.termo().size(); i++){
            visitTermo(ctx.termo(i));
        }
        if(ctx.op1() != null)
        {
            for (Integer i = 0; i < ctx.op1().size(); i++)
            {
                operadorEscreva.add(ctx.op1(i).getText());
            }
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
        saida.append(ctx.expressao().getText());
        saida.append("){\n");
        
        ctx.cmd().forEach(dec -> visitCmd(dec));
        
        if(ctx.cmd().size() > 1)
        {
            saida.append("\telse{");
            saida.append("\t");
            ctx.cmd().forEach(dec -> visitCmd(dec));
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
        saida.append(ctx.IDENT().getText() + "++)\n");
        
        for(Integer i = 0; i < ctx.cmd().size(); i++)
        {
            saida.append("\t");
            ctx.cmd().forEach(dec -> visitCmd(dec));
        }
        
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
    
    /*@Override
    public Void visitCorpo(LAParser.CorpoContext ctx)
    {
        ctx.cmd().forEach(dec -> visitCorpo(dec));
        return null;
    }*/
    
    /*@Override
    public Void visitIdentificador(LAParser.IdentificadorContext ctx)
    {
        return null;
    }*/
}
