/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }
    
    //Definindo os tipos de expressoes nao unarias
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Parcela_nao_unarioContext parcelaNaoUnario) {
     
        if (parcelaNaoUnario.CADEIA() != null){
            //System.out.println("CADEIA");
            return TabelaDeSimbolos.TipoLA.LITERAL;
        }
        return null;
    }
    
    //O objetivo final aqui eh classificar os tipos de cada um dos individuos dentro de uma expressao, isso para um caso unario
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Parcela_unarioContext parcelaUnario) {
      
        if (parcelaUnario.NUM_INT() != null)
        {
            //System.out.println("INTEIRO");
            return TabelaDeSimbolos.TipoLA.INTEIRO;
        }
        else if (parcelaUnario.NUM_REAL() != null)
        {
            //System.out.println("REAL");
            return TabelaDeSimbolos.TipoLA.REAL;
        }
        else if (parcelaUnario.IDENT() != null)
        {
            //System.out.println("IDENT");
            return tabela.verificar(parcelaUnario.IDENT().getText());
        }
        else if (parcelaUnario.identificador() != null)
        {
            //System.out.println("IDENTIFICADOR");
            var identificador=parcelaUnario.identificador();
            
            //Verificando se o identificador ja esta na tabela (foi definido) antes de prosseguir com a tipagem dos elementos
            if(tabela.existe(identificador.getText()) == false){
                String mensagem="identificador " + identificador.getText()  + " nao declarado";
                adicionarErroSemantico(identificador.start, mensagem);
                return TabelaDeSimbolos.TipoLA.INVALIDO;
            }
            else{
                TabelaDeSimbolos.TipoLA tipoIdentificador = tabela.verificar(identificador.getText());
                return tipoIdentificador;
            }
        }
        else if (parcelaUnario.expressao() != null)
        {
            //System.out.println("EXPRESSAO");
            return LASemanticoUtils.verificarTipo(tabela, parcelaUnario.expressao(0));
        }
        else{
            System.out.println(" Parcela unaria nao identificada, retornando null"); 
            return TabelaDeSimbolos.TipoLA.LITERAL; //coloquei só pra buildar mas tá errado!!!
        
        }
       
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.ParcelaContext parcela) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        if (parcela.parcela_unario() != null) {
            return verificarTipo(tabela, parcela.parcela_unario());
        } else if (parcela.parcela_nao_unario() != null) {
            return verificarTipo(tabela, parcela.parcela_nao_unario());
        }
        return ret;

    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.FatorContext fator) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var parcela: fator.parcela())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, parcela);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                //adicionarErroSemantico(fator.start, "Expressão " + fator.getText() + " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.TermoContext termo) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var fator: termo.fator())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, fator);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){                
                if(ret == TabelaDeSimbolos.TipoLA.INTEIRO || ret == TabelaDeSimbolos.TipoLA.REAL){
                    //A aritmetica entre inteiro e real deve ser realizada sem erros
                }
                else{
                    //adicionarErroSemantico(termo.start, "Expressão " + termo.getText() + " contém tipos incompatíveis");
                    ret = TabelaDeSimbolos.TipoLA.INVALIDO;
                }
            }
        }
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Exp_aritmeticaContext ctx)
    {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var termo: ctx.termo())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, termo);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                //adicionarErroSemantico(ctx.start, "Expressão "+ctx.getText()+ " contém tipos incompatíveis");
                ret = TabelaDeSimbolos.TipoLA.INVALIDO;
            }
        }
        
        return ret;
    }
    
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.Exp_relacionalContext exp_relacional) {
        TabelaDeSimbolos.TipoLA ret = null;
        
        for(var expressaoAritimetica: exp_relacional.exp_aritmetica())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, expressaoAritimetica);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                //Evitando que termos logicos entre numeros sejam reconhecidos como erros
                
                if(exp_relacional.op_relacional()==null){
                    //adicionarErroSemantico(exp_relacional.start, "Expressão "+exp_relacional.getText()+ " contém tipos incompatíveis");
                    ret = TabelaDeSimbolos.TipoLA.INVALIDO;
                }
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
        for(var fatorLogico: termoLogico.fator_logico())
        {
            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, fatorLogico);
            if(ret == null)
            {
                ret = aux;
            }
            else if(termoLogico.op_logico_2()!=null){
                ret = TabelaDeSimbolos.TipoLA.LOGICO;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                //Evitando que termos logicos entre numeros sejam reconhecidos como erros
                if(termoLogico.op_logico_2()==null){
                    //adicionarErroSemantico(termoLogico.start, "Expressão "+termoLogico.getText()+ " contém tipos incompatíveis");
                    ret = TabelaDeSimbolos.TipoLA.INVALIDO;
                }
            }
        }
        return ret;
    }
    
    //Quando o tipo estiver dentro de uma expressao, devera ser verificado aqui
    public static TabelaDeSimbolos.TipoLA verificarTipo(TabelaDeSimbolos tabela, LAParser.ExpressaoContext expressao) {
        TabelaDeSimbolos.TipoLA ret = null;
        for(var termoLogico: expressao.termo_logico())
        {

            TabelaDeSimbolos.TipoLA aux = verificarTipo(tabela, termoLogico);
            if(ret == null)
            {
                ret = aux;
            }
            else if (ret != aux && aux != TabelaDeSimbolos.TipoLA.INVALIDO){
                //Evitando que termos logicos entre numeros sejam reconhecidos como erros
                if(expressao.op_logico_1()==null){
                    //adicionarErroSemantico(expressao.start, "Expressão 3"+expressao.getText()+ " contém tipos incompatíveis");
                    ret = TabelaDeSimbolos.TipoLA.INVALIDO;
                }
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