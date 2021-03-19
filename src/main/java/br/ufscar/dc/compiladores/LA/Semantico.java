package br.ufscar.dc.compiladores.LA;

import br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Semantico extends LABaseVisitor<TipoLA>{
    public TabelaDeSimbolos tabela;
    FileOutputStream saida;
    
    public Semantico(FileOutputStream saida){
        this.saida=saida;
    }
    
    public TabelaDeSimbolos getTabela() {
        return tabela;
    }
        
    @Override
    public TipoLA visitPrograma(LAParser.ProgramaContext ctx) {
        //Inicializando tabela de simbolos
        tabela = new TabelaDeSimbolos();
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
        
        //Andando em todas variaveis de um identificador
         for (var parametro : ctx.identificador()) { 
             
            String nomeVar=parametro.getText();
            
            if(tabela.existe(nomeVar) == true){
                
                String mensagem="identificador " + nomeVar  + " ja declarado anteriormente";
                try {
                    this.saida.write((String.format("Linha %d: %s\n",  ctx.tipo().start.getLine(), mensagem)).getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        //Verificando se o simbolo foi digitado corretamente
            switch(tipoVar) {
                case "literal":
                    tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.LITERAL);
                    //System.out.println("Tipo certo: "+ tipoVar); 
                    break;
                case "inteiro":
                    tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.INTEIRO);
                    //System.out.println("Tipo certo: "+ tipoVar); 
                    break;
                case "real":
                    tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REAL);
                    //System.out.println("Tipo certo: "+ tipoVar); 
                    break;
                case "logico":
                    tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.LOGICO);
                    //System.out.println("Tipo certo: "+ tipoVar); 
                    break;
                default:
                    tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.INVALIDO);
                    String mensagem="tipo " + tipoVar  + " nao declarado";
                {
                    try {
                        this.saida.write((String.format("Linha %d: %s\n", ctx.tipo().start.getLine() , mensagem)).getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            
            
            
        }

        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitCmdleia(LAParser.CmdleiaContext ctx) {
        
        //Verificando se todos identificadores/parametros que sao chamados no leia existem na tabela
    
        for (var parametro : ctx.identificador()) {
            for (var identificador : parametro.IDENT()) {
                    
                    if(tabela.existe(identificador.getText()) == false){
                        String mensagem="identificador " + identificador.getText()  + " nao declarado";
                        try {
                            this.saida.write((String.format("Linha %d: %s\n",  identificador.getSymbol().getLine(), mensagem)).getBytes());
                        } catch (IOException ex) {
                            Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
            }
        }
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitCmdescreva(LAParser.CmdescrevaContext ctx) {
        for (var expressao : ctx.expressao()) {
            LASemanticoUtils.verificarTipo(tabela, expressao);
        }
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitCmdatribuicao(LAParser.CmdatribuicaoContext ctx) {
        var identificador = ctx.identificador();
        var expressao = ctx.expressao();
         
        var tipoExpressao = LASemanticoUtils.verificarTipo(tabela, expressao);
        
        //Se o tipo for invalido ira anotar oerro
        if(tipoExpressao==TabelaDeSimbolos.TipoLA.INVALIDO){
           LASemanticoUtils.adicionarErroSemantico(identificador.start, "atribuicao nao compativel para " + identificador.getText());
        }
        
        return visitChildren(ctx);
    }
    
    //o erro da linha 5 é q ele pega linha 8, mas é linha 7
}

//Linha 21: atribuicao nao compativel para classificacao