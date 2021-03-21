/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;

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
        
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = false;
        }
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo, Boolean ponteiro)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = ponteiro;
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
    public boolean existe(String nome)
    {
        return tabela.containsKey(nome);
    }
    public TipoLA verificar(String nome)
    {
        return tabela.get(nome).tipo;
    }
    public Boolean verificarPonteiro(String nome)
    {
        return tabela.get(nome).ponteiro;
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