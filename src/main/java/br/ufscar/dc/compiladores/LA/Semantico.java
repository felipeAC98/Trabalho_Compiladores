package br.ufscar.dc.compiladores.LA;

import static br.ufscar.dc.compiladores.LA.LASemanticoUtils.adicionarErroSemantico;
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
    
    @Override public TipoLA visitExpressao(LAParser.ExpressaoContext ctx) {

        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitDeclaracao_local(LAParser.Declaracao_localContext ctx) {    
        //Se for somente uma definicao de tipo, devera entrar aqui para criar a subtabela do registro em questao
        if (ctx.tipo()!=null &&  ctx.tipo().registro()!=null){

                //salvando os valores anteriores da tabela
                TabelaDeSimbolos tabelaSimbolosOriginal=tabela;

                //A ideia aqui eh criar uma 
                tabela= new TabelaDeSimbolos();

                TipoLA retornoFilho= visitChildren(ctx); 

                //neste momento "tabela" possui somente as variaveis da estrutura
                TabelaDeSimbolos tabelaRegistro=tabela;

                tabela=tabelaSimbolosOriginal;

                //inserindo as tabelas de cada registro na tabela geral
                String nomeVar=ctx.IDENT().getText();
                //System.out.println("nomeVar: "+ nomeVar);
                Boolean tipoP = false;
                tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO, tipoP, tabelaRegistro );

                //apos visitar o filho, a tabela devera ter somente as variaveis referentes a este registro
                return retornoFilho;

            }
        else
        //Quando tiver um tipo basico entao uma declaracao de constante foi feita
        if( ctx.tipo_basico()!=null){
            String tipoVar=ctx.tipo_basico().getText();
            String nomeVar=ctx.IDENT().getText();
            
            TipoLA tipoVarLA = LASemanticoUtils.verificaTipoVar(tabela, tipoVar, null);; 
            
            if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.INVALIDO){
                String mensagem="tipo " + tipoVar  + " nao declarado";
                {
                    try {
                        this.saida.write((String.format("Linha %d: %s\n", ctx.tipo().start.getLine() , mensagem)).getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            tabela.adicionar(nomeVar, tipoVarLA);  

        }     

        return visitChildren(ctx);

    }
    
    @Override public TipoLA visitVariavel(LAParser.VariavelContext ctx) {
        //Se for um registro entao pode ter varias variaveis dentro, e isso sera tratado para cada uma delas posteriormente na recursao          
        if (ctx.tipo().registro()!=null ){
            
            //salvando os valores anteriores da tabela
            TabelaDeSimbolos tabelaSimbolosOriginal=tabela;

            //A ideia aqui eh criar uma 
            tabela= new TabelaDeSimbolos();

            //Entrando na criacao da subtabela            
            TipoLA retornoFilho= visitChildren(ctx); 
                                   
            //neste momento "tabela" possui somente as variaveis da estrutura
            TabelaDeSimbolos tabelaRegistro=tabela;
            
            tabela=tabelaSimbolosOriginal;
            
            //inserindo as tabelas de cada registro na tabela geral
            for (var parametro : ctx.identificador()) { 
                String nomeVar=parametro.getText();
                //System.out.println("nomeVar: "+ nomeVar);
                Boolean tipoP = false;
                tabela.adicionar(nomeVar, br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO, tipoP, tabelaRegistro ); 
            }
           
            
            //apos visitar o filho, a tabela devera ter somente as variaveis referentes a este registro
            return retornoFilho;
 
        }
        
        //Quando for uma variavel do tipo de um registro, essa variavel vai ser alterada posteriormente
        TabelaDeSimbolos tipoRegistro=null;
                
        String tipoVar=ctx.tipo().tipo_estendido().tipo_basico_ident().getText();
        
        Boolean tipoP = ctx.tipo().tipo_estendido().pont != null;
        
        //Andando em todas variaveis de um identificador
         for (var identificador : ctx.identificador()) { 
            String nomeVar=identificador.getText();
            //System.out.println("nomeVar: "+ nomeVar); 
            if(tabela.existe(nomeVar) == true){
                String mensagem="identificador " + nomeVar  + " ja declarado anteriormente";
                try {
                    this.saida.write((String.format("Linha %d: %s\n",  identificador.start.getLine(), mensagem)).getBytes());
                } catch (IOException ex) {
                    Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            //Obtendo o tipo da variavel
            TipoLA tipoVarLA = LASemanticoUtils.verificaTipoVar(tabela, tipoVar, tipoRegistro); 
            
            if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.INVALIDO){
                //System.out.println("Tipo errado: "+ tipoVar);
                String mensagem="tipo " + tipoVar  + " nao declarado";
                {
                    try {
                        this.saida.write((String.format("Linha %d: %s\n", ctx.tipo().start.getLine() , mensagem)).getBytes());
                    } catch (IOException ex) {
                        Logger.getLogger(Semantico.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //Verificando se eh um registro para obter a tabela interna
            else if(tipoVarLA==br.ufscar.dc.compiladores.LA.TabelaDeSimbolos.TipoLA.REGISTRO){
                tipoRegistro = tabela.obtemTabelaRegistro(tipoVar);
            }
            
            //Verificando se eh um vetor
            if(identificador.dimensao().exp_aritmetica(0)!=null){
                int tamanhoVetor= Integer.parseInt(identificador.dimensao().exp_aritmetica(0).termo(0).fator(0).parcela(0).parcela_unario().NUM_INT().toString()); 
                nomeVar=identificador.IDENT(0).getText();
                String nomeVarPadrao=nomeVar+"[";
                
                //Adicionando todas posicoes do vetor como se fossem varias variaveis
                for(int i =0; i<tamanhoVetor; i++){
                    nomeVar=nomeVarPadrao+Integer.toString(i)+"]"; 
                    tabela.adicionar(nomeVar, tipoVarLA, tipoP,tipoRegistro);
                    //System.out.println("nomeVar: "+ nomeVar); 
                }
            
                //Caso precise de uma matriz, precisa fazer em cima dessa iteracao ai
                /*for (var exp : identificador.dimensao().exp_aritmetica()) { 
                    for(var termo : exp.termo()){
                        for(var fator : termo.fator()){
                            for(var parcela : fator.parcela()){
                                System.out.println(parcela.parcela_unario().NUM_INT().toString()); 
                            }
                        }
                    }
                }*/
            }
            else
                tabela.adicionar(nomeVar, tipoVarLA, tipoP,tipoRegistro);  
        }

        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitCmdleia(LAParser.CmdleiaContext ctx) {
        
        //Verificando se todos identificadores/parametros que sao chamados no leia existem na tabela
    
        for (var identificador : ctx.identificador()) {
                    Boolean existeIdentificador;
                    existeIdentificador=tabela.existe(identificador);
                    if(existeIdentificador == false){
                        String mensagem="identificador " + identificador.getText()  + " nao declarado";
                        LASemanticoUtils.adicionarErroSemantico(identificador.start, mensagem);
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
        
        //System.out.println(identificador.getText());         
        
        //Verificando se o identificador esta na tabela antes de prosseguir
        if(tabela.existe(identificador)==false){
            String mensagem="identificador " + identificador.getText()+ " nao declarado";
            LASemanticoUtils.adicionarErroSemantico(identificador.start, mensagem );
            return visitChildren(ctx);
        }
        TipoLA tipoIdentificador=tabela.verificar(identificador);
        TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, expressao);

        //System.out.println(tipoIdentificador);

        //Nao devemos ter erros quando o identificador for real e a expressao inteiro ou viceversa
        if(tipoExpressao==TabelaDeSimbolos.TipoLA.REAL || tipoExpressao==TabelaDeSimbolos.TipoLA.INTEIRO){
            if(tipoIdentificador==TabelaDeSimbolos.TipoLA.REAL || tipoIdentificador==TabelaDeSimbolos.TipoLA.INTEIRO){
                tipoExpressao=tipoIdentificador;
            }
        }
        
        //Se o tipo for invalido ira anotar oerro
        if(tipoExpressao==TabelaDeSimbolos.TipoLA.INVALIDO || tipoIdentificador!=tipoExpressao){
           if(tabela.verificarPonteiro(identificador)){
                LASemanticoUtils.adicionarErroSemantico(identificador.start, "atribuicao nao compativel para ^" + identificador.getText());
           }
           else{
                LASemanticoUtils.adicionarErroSemantico(identificador.start, "atribuicao nao compativel para " + identificador.getText());
           }
        }
        
        return visitChildren(ctx);
    }
    
    @Override public TipoLA visitCmdenquanto(LAParser.CmdenquantoContext ctx) { 
       
        TipoLA tipoExpressao = LASemanticoUtils.verificarTipo(tabela, ctx.expressao());
        
        return visitChildren(ctx); 
    }
    
    @Override public TipoLA visitCmdpara(LAParser.CmdparaContext ctx) {
        
        //Adicionando na tabela o iterador "i"
        tabela.adicionar(ctx.IDENT().getText(), TabelaDeSimbolos.TipoLA.INTEIRO);  
        return visitChildren(ctx); 
    }
	
}

//Linha 21: atribuicao nao compativel para classificacao

//15
//Linha 49: comando retorne nao permitido nesse escopo
//Fim da compilacao