/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA;

/**
 *
 * @author mariz
 */
public class LAGeradorC extends LABaseVisitor<Void>{
    StringBuilder saida;
    TabelaDeSimbolos tabela;
    
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
        String nome = ctx.expressao(0).getText();
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
        
        saida.append("\tprintf(\"%" + tipoPrintf + "\", " + nome + ");\n");
        
        return null;
    }
    
    @Override
    public Void visitExpressao(LAParser.ExpressaoContext ctx)
    {
        
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
