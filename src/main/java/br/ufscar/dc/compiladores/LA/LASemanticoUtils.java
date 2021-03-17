/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author mariz
 */
public class LASemanticoUtils {
    
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(Token t, String mensagem)
    {
        int linha = t.getLine();
        int coluna = t.getCharPositionInLine();
        errosSemanticos.add(String.format("Erro %d:%d - %s", linha, coluna, mensagem));
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Parcela_unarioContext parcelaUnario) {
        
        if (parcelaUnario.NUM_INT() != null)
        {
            System.out.println("texto");
            return TabelaDeSimbolos.TipoLA.INTEIRO;
        }
        else if (parcelaUnario.NUM_REAL() != null)
        {
            System.out.println("REAL");
            return TabelaDeSimbolos.TipoLA.REAL;
        }
        else if (parcelaUnario.IDENT() != null)
        {
            System.out.println("PIPIPI");
            return tabela.verificar(parcelaUnario.IDENT().getText());
        }
        
        
        
        
        
        
        
        
        return null; //coloquei só pra buildar mas tá errado!!!
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.ParcelaContext parcela) {
        if (parcela.parcela_unario() != null)
        {
            return verificarTipo(tabela, parcela.parcela_unario());
        }
        return null; //novamente só para buildar
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.FatorContext fator) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var ta: fator.parcela())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                adicionarErroSemantico(fator.start, "Expressão " + fator.getText() + " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.TermoContext termo) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var ta: termo.fator())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                adicionarErroSemantico(termo.start, "Expressão " + termo.getText() + " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Exp_aritmeticaContext ctx)
    {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var ta: ctx.termo())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                adicionarErroSemantico(ctx.start, "Expressão "+ctx.getText()+ " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Exp_relacionalContext exp_relacional) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var ta: exp_relacional.exp_aritmetica())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                adicionarErroSemantico(exp_relacional.start, "Expressão "+exp_relacional.getText()+ " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Parcela_logicaContext parcelaLogica) {
        if (parcelaLogica.exp_relacional() != null) {
            return verificarTipo(tabela, parcelaLogica.exp_relacional());
        } else {
            return TabelaDeSimbolos.TipoLA.LOGICO;
        }
    }

    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Fator_logicoContext fatorLogico) {
        return verificarTipo(tabela, fatorLogico.parcela_logica());
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Termo_logicoContext termoLogico) {
        TabelaDeSimbolos.TipoLA ret = null;
        for(var ta: termoLogico.fator_logico())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                adicionarErroSemantico(termoLogico.start, "Expressão "+termoLogico.getText()+ " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.ExpressaoContext expressao) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var ta: expressao.termo_logico())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                adicionarErroSemantico(expressao.start, "Expressão "+expressao.getText()+ " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*private static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, LAParser.Exp_aritmeticaContext ctx)
    {
        TabelaDeSimbolos.TipoAlguma ret = null;
        for(var ta: ctx.termoAritmetico())
        {
            TabelaDeSimbolos.TipoAlguma aux = verificarTipo(tabela, ta);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoAlguma.INVALIDO){
                adicionarErroSemantico(ctx.start, "Expressão "+ctx.getText()+ " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
        }
        return ret;
    }*/
    
    /*
    private static TabelaDeSimbolos.TipoAlguma verificarTipo(TabelaDeSimbolos tabela, LAParser.Exp_aritmeticaContext ctx)
    {
        if(ctx.NUMINT() != null)
        {
            return TabelaDeSimbolos.TipoAlguma.INTEIRO;
        } else if(ctx.NUMREAL()!=null)
        {
            return TabelaDeSimbolos.TipoAlguma.REAL;
        }
        else if(ctx.expressaoAritmetica() != null)
        {
            return verificarTipo(tabela, ctx.expressaoAritmetica());
        }
        else
        {
            String nomeVar = ctx.VARIAVEL().getText();
            if(!tabela.existe(nomeVar))
            {
                adicionarErroSemantico(ctx.VARIAVEL().getSymbol(), "Variável " +nomeVar+" não foi declarada antes do uso");
                return TabelaDeSimbolos.TipoAlguma.INVALIDO;
            }
            //return verificarTipo(tabela, nomeVar);
        }
        return verificarTipo(tabela, ctx.expressaoAritmetica());
    }*/
    //private static TabelaSimbolos.TipoAlguma verificarTipo(TabelaSimbolos tabela, String nomeVar)
    //{
    //    return tabela.verificar(nomeVar);
    //}

    
    
    
    
    
}