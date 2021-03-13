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
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LAErrorListener extends BaseErrorListener{
    
    FileOutputStream saida;
    Boolean firstError = true; //vai armazenar a mensagem de erro que queremos exibir
    
    public LAErrorListener(FileOutputStream saida){
        this.saida=saida;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        String erro_simbolo = ((Token) offendingSymbol).getText();
        
        if (firstError == true){ // verificação para apresentar apenas a primeira mensagem de erro
            try {
                this.saida.write(("Linha " + line + ": erro sintatico proximo a " + ((erro_simbolo == "<EOF>") ? "EOF" : erro_simbolo) + "\n").getBytes());
            } catch (IOException ex) {
                Logger.getLogger(LAErrorListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            firstError=false;
        }
    }
}
