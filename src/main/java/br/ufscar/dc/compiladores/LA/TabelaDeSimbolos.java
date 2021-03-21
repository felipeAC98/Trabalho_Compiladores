/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.LAParser.IdentificadorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mariz
 */
public class TabelaDeSimbolos {
    public enum TipoLA{
        INTEIRO,
        LITERAL,
        REAL,
        LOGICO,
        INVALIDO,
        REGISTRO
    }
    class EntradaTabelaDeSimbolos{
        String nome;
        TipoLA tipo;
        Boolean ponteiro;
        TabelaDeSimbolos subTabelaRegistro;
        
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = false;
            this.subTabelaRegistro = null;
        }
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo, Boolean ponteiro)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = ponteiro;
            this.subTabelaRegistro = null;
        }
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo, Boolean ponteiro, TabelaDeSimbolos subTabelaRegistro)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = ponteiro;
            this.subTabelaRegistro = subTabelaRegistro;
        }
    }
    private final Map<String, EntradaTabelaDeSimbolos> tabela;
    private final Map<String, String> tabelaDeRegistros;
    
    public TabelaDeSimbolos()
    {
        this.tabela = new HashMap<>();
        this.tabelaDeRegistros = new HashMap<>();
    }
    public void adicionar(String nome, TipoLA tipo)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }
    public void adicionar(String nome, TipoLA tipo, Boolean ponteiro)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo, ponteiro));
    }
    //adicionar variavel de um registro
    public void adicionar(String nome, TipoLA tipo, Boolean ponteiro, TabelaDeSimbolos subTabelaDeSimbolos)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo, ponteiro,subTabelaDeSimbolos));  
        
    }
    public boolean existe(String nome)
    {

        return tabela.containsKey(nome);

    }
    
    public boolean existe(LAParser.IdentificadorContext identificador)
    {
        if(identificador.reg==null){
            return tabela.containsKey(identificador.getText());  
        }
        else{
            //verificando se existe o registro na tabela, caso nao ja retorna falso
           if(tabela.get(identificador.IDENT(0).getText())!=null){
               //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
                TabelaDeSimbolos tabelaRegistro=obtemTabelaRegistro(identificador.IDENT(0).getText());
                return tabelaRegistro.existe(identificador.IDENT(1).getText());
           }
           else{
               return false;
           }
                      
        }
    }
    public TipoLA verificar(String nome)
    {
        return tabela.get(nome).tipo;
    }
    public TipoLA verificar(LAParser.IdentificadorContext identificador)
    {
       if(identificador.reg==null){
            return tabela.get(identificador.getText()).tipo;
       }
       else{
           //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
           TabelaDeSimbolos tabelaRegistro=obtemTabelaRegistro(identificador.IDENT(0).getText());
           return tabelaRegistro.verificar(identificador.IDENT(1).getText());
           
           //ideia caso estrutura de uma estrutura ->chamar recursivamente esta funcao para cada tabela
           //Removendo o primeiro identificador que ja sabemos que eh referente a um registro 
           //identificador.IDENT().remove(0);
       }
    }
    
    public TabelaDeSimbolos obtemTabelaRegistro(String nome){
        return tabela.get(nome).subTabelaRegistro;
    }
    public Boolean verificarPonteiro(String nome)
    {
        return tabela.get(nome).ponteiro;
    }
    
    public Boolean verificarPonteiro(LAParser.IdentificadorContext identificador)
    {
       if(identificador.reg==null){
             return tabela.get(identificador.getText()).ponteiro;
       }
       else{
           //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
           TabelaDeSimbolos tabelaRegistro=obtemTabelaRegistro(identificador.IDENT(0).getText());
           return tabelaRegistro.verificarPonteiro(identificador.IDENT(1).getText());
           
           //ideia caso estrutura de uma estrutura ->chamar recursivamente esta funcao para cada tabela
           //Removendo o primeiro identificador que ja sabemos que eh referente a um registro 
           //identificador.IDENT().remove(0);
       }
    }    
    
    public String[] registroParaVetor(String variavelRegistro){
        return variavelRegistro.split(".");
    }
    public void adicionarRegistro(String nome, String tipoRegistro)
    {
        tabelaDeRegistros.put(nome, tipoRegistro);
    }
    public boolean existeRegistro(String nome)
    {
        return tabelaDeRegistros.containsKey(nome);
    }
    
    
    //public TipoLA verificarRegistro(String nome)
    //{
        //return tabelaDeRegistros.get(nome).;
        //como retornar o tipo do registro?
    //}
}