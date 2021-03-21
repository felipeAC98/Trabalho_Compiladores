/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufscar.dc.compiladores.LA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mariz
 */
public class TabelaDeRegistros {
    public enum TipoLA{
        INTEIRO,
        LITERAL,
        REAL,
        LOGICO,
        INVALIDO
    }    
    //estou criando aqui uma tabela para pegar novas estruturas que foram criadas
    class EntradaTabelaDeRegistros
    {
        String nomeTipo;
        ArrayList<String> variaveis;
        ArrayList<TipoLA> tipoVariaveis;
        private EntradaTabelaDeRegistros(String nomeTipo)
        {
            this.nomeTipo = nomeTipo;
            this.variaveis = new ArrayList();
        }
        private void adicionarVariaveis(ArrayList<TipoLA> tipos, ArrayList<String> nomes)
        {
            this.variaveis = nomes;
            this.tipoVariaveis = tipos;
        }
    }
    private final Map<String, EntradaTabelaDeRegistros> tabela;
    
    public TabelaDeRegistros()
    {
        this.tabela = new HashMap<>();
    }
    
    public void adicionar(String tipo, ArrayList<String> variaveis, ArrayList<TipoLA> tipoVariaveis)
    {
        EntradaTabelaDeRegistros entrada = new EntradaTabelaDeRegistros(tipo);
        entrada.adicionarVariaveis(tipoVariaveis, variaveis);
        tabela.put(tipo, entrada);
    }
}