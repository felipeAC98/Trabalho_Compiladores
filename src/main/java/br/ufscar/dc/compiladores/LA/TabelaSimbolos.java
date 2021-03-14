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
public class TabelaSimbolos {
    public enum TipoAlguma{
        INTEIRO,
        REAL,
        INVALIDO
    }
    class EntradaTabelaDeSimbolos{
        String nome;
        TipoAlguma tipo;
        
        private EntradaTabelaDeSimbolos(String nome, TipoAlguma tipo)
        {
            this.nome = nome;
            this.tipo = tipo;
        }
    }
    private final Map<String, EntradaTabelaDeSimbolos> tabela;
    
    public TabelaSimbolos()
    {
        this.tabela = new HashMap<>();
    }
    public void adicionar(String nome, TipoAlguma tipo)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }
    public boolean existe(String nome)
    {
        return tabela.containsKey(nome);
    }
    public TipoAlguma verificar(String nome)
    {
        return tabela.get(nome).tipo;
    }
}
