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
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LAErrorListener extends BaseErrorListener{
    
    FileOutputStream saida;     //arquivo onde imprimirá a saída
    int maxErrosEsperados;      //quantos erros serão imprimidos na saída (no caso é 1)
    int nErrosObtidos;          //quantos erros foram efetivamente encontrados
    
    public LAErrorListener(FileOutputStream saida, int maxErrosEsperados){
        //ao ser chamado recebe o arquivo onde imprimirá a saída e o máximo de erros a serem impressos
        //o construtor atribui os parâmetros recebidos aos atributos
        this.saida=saida;
        this.maxErrosEsperados=maxErrosEsperados;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        //pega uma string com o erro
        String erroEncontrado = ((Token) offendingSymbol).getText();
        
        // verificação para apresentar apenas a primeira mensagem de erro
        //pois uma vez que é encontrado um erro o resto torna-se irrelevante
        if (nErrosObtidos < maxErrosEsperados){
            try {
                //quando o erro está próximo a EOF, o erro é pegado como <EOF>
                //então modificamos a mensagem para ficar correta
                this.saida.write(("Linha " + line + ": erro sintatico proximo a " + ("<EOF>".equals(erroEncontrado) ? "EOF" : erroEncontrado) + "\n").getBytes());
            } catch (IOException ex) {
                Logger.getLogger(LAErrorListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            nErrosObtidos=nErrosObtidos+1;
        }
    }
}
