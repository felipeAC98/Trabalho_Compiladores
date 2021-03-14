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

    private static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.TermoContext ta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
