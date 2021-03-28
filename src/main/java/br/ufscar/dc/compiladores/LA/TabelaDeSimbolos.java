/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.LAParser.IdentificadorContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
        REGISTRO,
        PROCEDIMENTO
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
    private final LinkedHashMap<String, EntradaTabelaDeSimbolos> tabela;
    private final Map<String, String> tabelaDeRegistros;
     
    public TabelaDeSimbolos()
    {
        this.tabela = new LinkedHashMap<>();
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
        String nomeVar;
                
        //Gambiarra brabissima para ver se o vetor ja foi definido
        if(identificador.dimensao().exp_aritmetica(0)!=null){
            nomeVar=identificador.IDENT(0).getText()+"[0]";
        }
        else
            nomeVar=identificador.getText(); 
        
        //System.out.println("nomeVar existe: "+ nomeVar); 
        
        if(identificador.reg==null){
            return tabela.containsKey(nomeVar);  
        }
        else{
            //verificando se existe o registro na tabela, caso nao ja retorna falso
           if(tabela.get(identificador.IDENT(0).getText())!=null){
               //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
                TabelaDeSimbolos tabelaRegistro=obtemSubTabela(identificador.IDENT(0).getText());
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
    
    public TipoLA verificar(int tipoID)
    {
        List<EntradaTabelaDeSimbolos> l;
        l = new ArrayList<EntradaTabelaDeSimbolos>(tabela.values());
        return l.get(tipoID).tipo;
    }
    public TipoLA verificar(LAParser.IdentificadorContext identificador)
    {
        String nomeVar;
        
        //Gambiarra brabissima para ver se o vetor ja foi definido (se passar qualquer coisa dentro das chaves ele vai aceitar)
        if(identificador.dimensao().exp_aritmetica(0)!=null){
            nomeVar=identificador.IDENT(0).getText()+"[0]";
        }
        else
            nomeVar=identificador.getText(); 
       if(identificador.reg==null){
            return tabela.get(nomeVar).tipo;
       }
       else{
           //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
           TabelaDeSimbolos tabelaRegistro=obtemSubTabela(identificador.IDENT(0).getText());
           return tabelaRegistro.verificar(identificador.IDENT(1).getText());
           
           //ideia caso estrutura de uma estrutura ->chamar recursivamente esta funcao para cada tabela
           //Removendo o primeiro identificador que ja sabemos que eh referente a um registro 
           //identificador.IDENT().remove(0);
       }
    }
    
    public TabelaDeSimbolos obtemSubTabela(String nome){
        return tabela.get(nome).subTabelaRegistro;
    }
    
    public int obtemTamanhoTabela(){
        return tabela.size();
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
           TabelaDeSimbolos tabelaRegistro=obtemSubTabela(identificador.IDENT(0).getText());
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
    
    
}