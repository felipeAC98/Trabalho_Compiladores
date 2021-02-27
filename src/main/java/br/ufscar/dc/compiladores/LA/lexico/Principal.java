/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* ------------------------------------------------------------ */
/*   >>  TRABALHO 1 - CONSTRUÇÃO DE COMPILADORES 1   <<         */
/*								*/ 
/*		ALUNOS: Felipe Alves        RA:             	*/
/*			Karolayne Arrais    RA: 746460		*/	
/*			Mariana Zagatti     RA:			*/
/* ------------------------------------------------------------ */

package br.ufscar.dc.compiladores.LA.lexico;

import java.io.FileOutputStream;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author felip
 */
public class Principal {
    public static void main(String args[]) throws IOException{
        
//fazer o tratamento para caso nao passeo arquivo com programa como argumento
        
        CharStream cs= CharStreams.fromFileName(args[0]);       //leitura do arquivo de entrada
        LALexer lex = new LALexer(cs); 
        FileOutputStream saida = new FileOutputStream(args[1]); //usada para escrever no arquivo de saída
        Token aux;
       
        while((aux = lex.nextToken()).getType() != Token.EOF){
            //para formacao da parte direita do token
            String direita_token = "'" + aux.getText() + "'"; // usada para montagem do token <getText(),direita_token>
            // System.out.print("<"+aux.getType()+","+aux.getText()+">");

            // verifica se foi indentificado algum dos erros léxicos definidos na gramática
            switch (LALexer.VOCABULARY.getDisplayName(aux.getType())) {
                case "IDENT":
                    direita_token = LALexer.VOCABULARY.getDisplayName(aux.getType());       // forma token <'aux.getText()','IDENT'>
                    break;
                case "CADEIA":
                    direita_token = LALexer.VOCABULARY.getDisplayName(aux.getType());       // forma token <'aux.getText()','CADEIA'>
                    break;
                case "NUM_INT":
                    direita_token = LALexer.VOCABULARY.getDisplayName(aux.getType());       // forma token <'aux.getText()','NUM_INT'>
                    break;
                case "NUM_REAL":
                    direita_token = LALexer.VOCABULARY.getDisplayName(aux.getType());       // forma token <'aux.getText()','NUM_REAL'>
                    break;
            }
                   
        
            // verifica erros lexicos definidos na gramática
            switch (LALexer.VOCABULARY.getDisplayName(aux.getType())) {
                case "ERRO_SIMBOLO":
                    saida.write(("Linha " + aux.getLine() + ": " + aux.getText() + " - simbolo nao identificado\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    saida.close();
                    return;
                default:
                    saida.write(("<'" + aux.getText() + "'," + direita_token+ ">\n").getBytes()); //forma token padrao das palavras chaves e simbolos <'aux.getText()','aux.getText()'>
            }
        }
        saida.close();
    }
}
