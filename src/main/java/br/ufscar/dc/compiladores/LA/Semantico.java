package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Semantico extends LABaseVisitor<TipoLA>{
    TabelaDeSimbolos tabela;
    FileOutputStream saida;
    
    public Semantico(FileOutputStream saida){
        this.saida=saida;
    }
    
    public TabelaDeSimbolos getTabela() {
        return tabela;
    }
        
    @Override
    public TipoLA visitPrograma(LAParser.ProgramaContext ctx) {
        //tabela = new TabelaDeSimbolos();
        return visitChildren(ctx);
    }
    
    @Override
    public TipoLA visitDeclaracao_local(LAParser.Declaracao_localContext ctx) { 
             
        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitExpressao(LAParser.ExpressaoContext ctx) {

        return visitChildren(ctx); 
    }
    @Override public TipoLA visitVariavel(LAParser.VariavelContext ctx) {
        
        String tipoVar=ctx.tipo().getText();
        /*if(tipoVar!="literal" && tipoVar!="inteiro" && tipoVar!="real" && tipoVar!="logico"){
          System.out.println("Tipo errado: "+ tipoVar);    
        }*/
        
        switch(tipoVar) {
            case "literal":
                System.out.println("Tipo certo: "+ tipoVar); break;
            case "inteiro":
                System.out.println("Tipo certo: "+ tipoVar); break;
            case "real":
                System.out.println("Tipo certo: "+ tipoVar); break;
            case "logico":
                System.out.println("Tipo certo: "+ tipoVar); break;
            default:
                String mensagem="tipo " + tipoVar  + " nao declarado";
            {
                try {
                    this.saida.write((String.format("Linha %d: %s\n", ctx.tipo().start.getLine() , mensagem)).getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
  
        }

        return visitChildren(ctx); 
    }
}
