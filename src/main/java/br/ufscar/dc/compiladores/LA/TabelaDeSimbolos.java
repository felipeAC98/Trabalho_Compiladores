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
        //Nao existe o tipo funcao pois a funcao eh tratada como uma variavel, e seu tipo eh seu retorno
    }
    class EntradaTabelaDeSimbolos{
        String nome;
        TipoLA tipo;
        Boolean ponteiro;
        TabelaDeSimbolos subTabelaRegistro;
        
        //entrada padrao da tabela
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = false;
            this.subTabelaRegistro = null;
        }
        
        //Entrada para tabela para quando um ponteiro precisa ser definido
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo, Boolean ponteiro)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = ponteiro;
            this.subTabelaRegistro = null;
        }
        
        //Entrada para tabela para quando uma subtabela precisa ser definida (funcoes e registros)
        private EntradaTabelaDeSimbolos(String nome, TipoLA tipo, Boolean ponteiro, TabelaDeSimbolos subTabelaRegistro)
        {
            this.nome = nome;
            this.tipo = tipo;
            this.ponteiro = ponteiro;
            this.subTabelaRegistro = subTabelaRegistro;

        }
    }
    //LinkedHash pois foi necessario obter a ordem dos parametros de uma funcao(sao inseridos em uma subtabela)
    private final LinkedHashMap<String, EntradaTabelaDeSimbolos> tabela;
    private final Map<String, String> tabelaDeRegistros;
     
    public TabelaDeSimbolos()
    {
        this.tabela = new LinkedHashMap<>();
        this.tabelaDeRegistros = new HashMap<>();
    }
    
    //Funcoes de adicao
    public void adicionar(String nome, TipoLA tipo)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo));
    }
    public void adicionar(String nome, TipoLA tipo, Boolean ponteiro)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo, ponteiro));
    }
    //adicionar variavel de um registro/funcao
    public void adicionar(String nome, TipoLA tipo, Boolean ponteiro, TabelaDeSimbolos subTabelaDeSimbolos)
    {
        tabela.put(nome, new EntradaTabelaDeSimbolos(nome, tipo, ponteiro,subTabelaDeSimbolos));  
        
    }
    
    //Verificadores de existencia de uma variavel na tabela
    public boolean existe(String nome)
    {
        return tabela.containsKey(nome);

    }
    
    public boolean existe(LAParser.IdentificadorContext identificador)
    {
        String nomeVar;
                
        //Quando um identificador tiver dimensao entao ele sera um vetor, por definicao ele foi salvo na tabela com o [0]
        if(identificador.dimensao().exp_aritmetica(0)!=null){
            nomeVar=identificador.IDENT(0).getText()+"[0]";
        }
        else
            nomeVar=identificador.getText(); 
      
        //Verificando se o identificador nao eh um registro
        if(identificador.reg==null){
            return tabela.containsKey(nomeVar);  
        }
        else{
            //verificando se existe o registro na tabela, caso nao ja retorna falso
           if(tabela.get(identificador.IDENT(0).getText())!=null){
               //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
                TabelaDeSimbolos tabelaRegistro=obtemSubTabela(identificador.IDENT(0).getText());
                return tabelaRegistro.existe(identificador.IDENT(1).getText()); //retorna o tipo do registro
           }
           else{
               return false;
           }
                      
        }
    }
    
    //Verificando o tipo da variavel 
    public TipoLA verificar(String nome)
    {
        return tabela.get(nome).tipo;
    }
    
    //Utilizado para quando for uma subtabela de uma funcao(que guarda os parametros)
    //neste caso um ID sera enviado e o retorno sera o tipo daquele ID, o ID aqui na verdade eh a posicao do parametro da funcao
    public TipoLA verificar(int tipoID)
    {
        List<EntradaTabelaDeSimbolos> l;
        l = new ArrayList<EntradaTabelaDeSimbolos>(tabela.values());
        return l.get(tipoID).tipo;
    }
    
    //Adaptacao mais geral para caso um identificador tenha sido passado para verificar o tipo
    public TipoLA verificar(LAParser.IdentificadorContext identificador)
    {
        String nomeVar;
        
        //Quando um identificador tiver dimensao entao ele sera um vetor, por definicao ele foi salvo na tabela com o [0]
        if(identificador.dimensao().exp_aritmetica(0)!=null){
            nomeVar=identificador.IDENT(0).getText()+"[0]";
        }
        else
            nomeVar=identificador.getText(); 
        
       //Utilizando mesma solucao criada para verificacao de existencia
       if(identificador.reg==null){
            return tabela.get(nomeVar).tipo;
       }
       else{
           //NAO FUNCIONA CASO FOR UMA ESTRURA DE UMA ESTRUTURA
           TabelaDeSimbolos tabelaRegistro=obtemSubTabela(identificador.IDENT(0).getText());
           return tabelaRegistro.verificar(identificador.IDENT(1).getText());
           
       }
    }
    
    //Essa subtabela pode ser os parametros de uma funcao ou os dados de um registro(estrutura), depende do tipo
    public TabelaDeSimbolos obtemSubTabela(String nome){
        return tabela.get(nome).subTabelaRegistro;
    }
    
    public int obtemTamanhoTabela(){
        return tabela.size();
    }
    
    //Verificando se este nome de variavel consta como sendo um ponteiro na tabela (legado)
    public Boolean verificarPonteiro(String nome)
    {
        return tabela.get(nome).ponteiro;
    }
    
    //Funcao para verificar se o identificador consta na tabela como um ponteiro
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
}