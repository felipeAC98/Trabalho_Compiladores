/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* ------------------------------------------------------------------------------*/
/*           >>  TRABALHO 2 - CONSTRUÇÃO DE COMPILADORES 1   <<                  */
/*                                                                               */ 
/*                  ALUNOS: Felipe Alves        RA: 744335                       */
/*                          Karolayne Arrais    RA: 726460                       */	
/*                          Mariana Zagatti     RA: 628620                   */
/*                                                                               */
/*  Instrucoes para executar o programa:                                         */
/*      apos clicar em Build, abra o terminal do windows no                      */
/*      diretorio do projeto e executo o seguinte comando:                       */
/*                                                                               */
/*  java -jar target/TrabalhoCompiladores-1.0-SNAPSHOT-jar-with-dependencies.jar */
/*  codigoEntrada.txt codigoSaida.txt                                            */
/*                                                                               */
/*  Atencao: codigoEntrada.txt deve conter o programa em linguagem algoritmica   */
/*           codigoSaida.txt diz respeito ao arquivo que contera os tokens apos  */
/*           a analise lexica                                                    */
/* ----------------------------------------------------------------------------- */

package br.ufscar.dc.compiladores.LA;

import java.io.FileOutputStream;
import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Principal {
    public static void main(String args[]) throws IOException{
        
        CharStream cs= CharStreams.fromFileName(args[0]);       //utilizado para leitura do arquivo passado como entrada contendo codigo com programa em linguagem algoritmica 
        LALexer lex = new LALexer(cs);
        FileOutputStream saida = new FileOutputStream(args[1]); //utilizado para escrita no arquivo de saida apos analisa lexica
        Token aux = null;   //token auxiliar para analise
        
        //Definicoes para verificacoes de sintaxe
        CommonTokenStream tokens = new CommonTokenStream(lex);     
        LAParser parser = new LAParser(tokens);
        LAErrorListener LAEL = new LAErrorListener(saida); 
        
        //Verificando erros lexicos aqui
        while((aux = lex.nextToken()).getType() != Token.EOF){  //enquanto tivermos caracteres no arquivo de entrada para serem analisados 

            //para formacao da parte direita do token
            String direita_token = "'" + aux.getText() + "'"; // usada para montagem do token <getText(),direita_token>
           
            // verifica erros lexicos definidos na gramatica - trabalho 1 - ainda eh preciso printar o erro lexico
            switch (LALexer.VOCABULARY.getDisplayName(aux.getType())) {
                case "ERRO_SIMBOLO":
                    saida.write(("Linha " + aux.getLine() + ": " + aux.getText() + " - simbolo nao identificado\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    saida.close();
                    return;
                case "COMENTARIO_ERRADO":
                    saida.write(("Linha " + aux.getLine() + ": comentario nao fechado\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    saida.close();
                    return;
                case "ERRO_CADEIA":
                    saida.write(("Linha " + aux.getLine() + ": cadeia literal nao fechada\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    saida.close();
                    return;
            }
        }
        
        //movendo para o inicio do arquivo novamente
        cs.seek(0);
        lex.setInputStream(cs);
        
        lex.removeErrorListeners();
        //inserindo o errorListener criado
        lex.addErrorListener(LAEL);

        parser.removeErrorListeners();
        parser.addErrorListener(LAEL);
        parser.programa();       
        
        saida.write(("Fim da compilacao\n").getBytes());
        saida.close();
        return;    
        
    }
}