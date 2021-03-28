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

import br.ufscar.dc.compiladores.LA.LAParser.ProgramaContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;

public class Principal {
    public static void main(String args[]) throws IOException{
        
        CharStream cs = CharStreams.fromFileName(args[0]);      //utilizado para leitura do arquivo passado como entrada contendo codigo com programa em linguagem algoritmica 
                                                                // pega o primeiro argumento passado por comando
        LALexer lex = new LALexer(cs);                          //para verificação léxica
        FileOutputStream saida = new FileOutputStream(args[1]); //utilizado para escrita no arquivo de saida apos analisa lexica
                                                                //pega o segundo argumento passado por linha de comando
        Token aux = null;                                       //token auxiliar para analise
        
        //Definicoes para as verificacoes de sintaxe
        //criando um listener para detectar erros sintáticos
        //considerando apenas 1 erro
        CommonTokenStream tokens = new CommonTokenStream(lex);     
        LAParser parser = new LAParser(tokens);
        int maxErrosEsperados = 1; 
        LAErrorListener LAEL = new LAErrorListener(saida, maxErrosEsperados); 
        
        //Variavel para armazenar a ocorrencia de erro
        Boolean ERROR = false;
        
        //Verificando erros lexicos aqui
        while((aux = lex.nextToken()).getType() != Token.EOF){  //enquanto tivermos caracteres no arquivo de entrada para serem analisados 

            //para formacao da parte direita do token
            String direita_token = "'" + aux.getText() + "'"; // usada para montagem do token <getText(),direita_token>
           
            // verifica erros lexicos definidos na gramatica - trabalho 1
            switch (LALexer.VOCABULARY.getDisplayName(aux.getType())) {
                case "ERRO_SIMBOLO":
                    saida.write(("Linha " + aux.getLine() + ": " + aux.getText() + " - simbolo nao identificado\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    ERROR=true;
                    break;
                    
                case "COMENTARIO_ERRADO":
                    saida.write(("Linha " + aux.getLine() + ": comentario nao fechado\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    ERROR=true;
                    break;
                    
                case "ERRO_CADEIA":
                    saida.write(("Linha " + aux.getLine() + ": cadeia literal nao fechada\n").getBytes());    //imprime a linha de erro e o tipo de erro
                    ERROR=true;
                    break;
            }
        }
        
        if(ERROR==false){
            //movendo para o inicio do arquivo novamente
            //para analisar novamente e ver a ocorrência de erros 
            cs.seek(0);
            lex.setInputStream(cs);
            
            //associando o errorListener criado com o lexer e com o parser
            lex.removeErrorListeners();
            lex.addErrorListener(LAEL);

            parser.removeErrorListeners();
            parser.addErrorListener(LAEL);
            ProgramaContext arvore = parser.programa();
            Semantico las = new Semantico(saida);
            las.visitPrograma(arvore); 
            
            if(LASemanticoUtils.errosSemanticos.isEmpty())
            {
                LAGeradorC gerador = new LAGeradorC();
                gerador.visitPrograma(arvore);
                try(PrintWriter pw = new PrintWriter(args[1]))
                {
                    pw.print(gerador.saida.toString());
                }
            }
            else{
               //Escrevendo os erros gravados no LASemanticoUtils para um arquivo
                List<String> errosSemanticos = LASemanticoUtils.errosSemanticos;
                  for (var erroSemantico : errosSemanticos) {
                      saida.write((erroSemantico + "\n").getBytes());
                  }   

                  saida.write(("Fim da compilacao\n").getBytes());  
            }
        }
        
        saida.close(); //fecha o ponteiro do arquivo de saída
        //return ;
    }
}