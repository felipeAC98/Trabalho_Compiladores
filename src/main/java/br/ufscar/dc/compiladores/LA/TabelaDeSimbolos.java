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
        INVALIDO
    }
    class EntradaTabelaDeSimbolos{
        String nome;
        TipoLA tipo;
        
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo)
        {
            this.nome = nome;
            this.tipo = tipo;
        }
    }
    private final Map<String, EntradaTabelaDeSimbolos> tabela;
    
    public TabelaDeSimbolos()
    {
        this.tabela = new HashMap<>();
    }
    public void adicionar(String nome, TipoLA tipo)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }
    public boolean existe(String nome)
    {
        return tabela.containsKey(nome);
    }
    public TipoLA verificar(String nome)
    {
        return tabela.get(nome).tipo;
    }
}
