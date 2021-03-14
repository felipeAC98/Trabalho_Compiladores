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

public class LASemantico extends LABaseVisitor<Void>{
    TabelaDeSimbolos tabela;
    
    @Override
    public Void visitPrograma(LAParser.ProgramaContext ctx)
    {
        tabela = new TabelaDeSimbolos();
        return super.visitPrograma(ctx);
    }
    
    /*@Override
    public Void visitDeclaracao_local(LAParser.Declaracao_localContext ctx)
    {
        String nomeVar = ctx.variavel().getText();
        String strTipoVar = ctx.tipo()
                .getText();
        TipoLA tipoVar = TipoLA.INVALIDO;
        switch(strTipoVar)
        {
            case "INTEIRO": tipoVar = TipoLA.INTEIRO; break;
            case "REAL": tipoVar = TipoLA.REAL; break;
        }
        if(tabela.existe(nomeVar))
        {
            LASemanticoUtils.adicionarErroSemantico(ctx.VARIAVEL().getSymbol(), "Variável "+nomeVar + " já existe");
        }
        else
        {
            tabela.adicionar(nomeVar, tipoVar);
        }
        return super.visitDeclaracoes(ctx);
    }*/
    
    /*@aqui só copiei o do professor
    
    Override
    public Void visitComandoAtribuicao(LAParser.ComandoAtribuicaoContext ctx)
    {
        TipoLA tipoExpressao  = LASemanticoUtils.verificarTipo(tabela, ctx.expressaoAritmetica);
        if(tipoExpressao != TipoAlguma.INVALIDO)
        {
            String nomeVar = ctx.VARIAVEL().getText();
            if(!tabela.existe(nomeVar))
            {
                LASemanticoUtils.adicionarErroSemantico(ctx.VARIAVEL().getSymbol(),"Variável "+nomeVar+" não foi declarada");
            }
            else
            {
                TipoLA tipoVariavel = LASemanticotils.verificarTipo(tabela, nomeVar);
                if(tipoVariavel != tipoExpressao)
                {
                    AlgumaSemanticoUtils.adicionarErroSemantico(ctx.VARIAVEL().getSymbol(), "Tipo da variável "+nomeVar+" não compatível com tipo da expressão");
                }
            }
        }
        return super.visitComandoAtribuicao(ctx);
    }*/
}
