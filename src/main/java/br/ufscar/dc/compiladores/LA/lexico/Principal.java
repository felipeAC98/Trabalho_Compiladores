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
       //FileOutputStream writer = new FileOutputStream(args[1]); //usada para escrever no arquivo de saída
       Token aux;
       
        while((aux = lex.nextToken()).getType() != Token.EOF){
            System.out.print("<"+aux.getType()+","+aux.getText()+">");
        }
    }
}
