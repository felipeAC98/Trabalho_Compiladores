/* ------------------------------------------------------------ */
/*   >>  TRABALHO 1 - CONSTRUÇÃO DE COMPILADORES 1   <<         */
/*								*/ 
/*		ALUNOS: Felipe Alves        RA:             	*/
/*			Karolayne Arrais    RA: 746460		*/	
/*			Mariana Zagatti     RA:	628620  	*/
/* ------------------------------------------------------------ */				

lexer grammar LALexer;

/*comentarios nao devem gerar tokens*/
COMENTARIO: '{'
            (' ' | 'A'..'Z' | 'a'..'z' | '0'..'9' | '\u0080'..'\uffff' | '(' | ')' | ',' | SIMBOLOS)*
            '}' 
            -> skip;

/* define palavras reservadas (palavres chaves) da linguagem LAlexer*/
PALAVRAS_CHAVE: 'algoritmo' | 'fim_algoritmo'
				| 'declare' | 'constante' | 'tipo' 
				| 'literal' | 'inteiro' | 'real' | 'logico'
				| 'verdadeiro' | 'falso'
				| 'registro' | 'fim_registro' 
				| 'procedimento' | 'fim_procedimento'
				| 'funcao' | 'fim_funcao'
				| 'var' | 'leia' | 'escreva'
				| 'se' | 'entao' | 'senao' | 'fim_se'
				| 'caso' | 'seja' | 'fim_caso' 
				| 'para' | 'ate' | 'faca' | 'fim_para'
				| 'enquanto' | 'fim_enquanto' 
				| 'retorne' | 'nao' | '<-'; 

/* define caracteres que nao sao letras */
SIMBOLOS: ':' | '=' | ',' | '[' | ']' | '^' | '(' | ')' | '-' | '&'; 

/* define operadores aritmeticos */
OPERADORES_ARITMETICOS: '+' | '-' | '*' | '/' |'%';

/* define operadores relacionais */
OP_RELACIONAL: '=' | '<>' | '>=' | '<=' | '>' | '<';

/* define operador logicos */
OP_LOGICOs: 'ou' | 'e'; 



/* define identificadores da linguagem
apenas com restricao de nao inicializar com caracteres numericos, podendo conter apenas _ como caractere especial alem de letras e numeros*/
IDENT: ('A'..'Z' | 'a'..'z' | '_')('A'..'Z' | 'a'..'z' | '0'..'9' | '_')*; 

/* define cadeias da linguagem  */
/* sequencia ao longo do algoritmo que necessariamente comeca e finaliza com aspas  */
/* onde entre essas aspas nao pode ocorrer " ou quebra de linha */
CADEIA: '"'(~('"'|'\n'))*'"';    

/* definicao de numeros inteiros    */
/* formados pela sequencia de 1 ou + caracteres numericos de 0 a 9.*/
NUM_INT: ('0'..'9')+; 

/* definicao de numeros reais  */
NUM_REAL: ('0'..'9')+'.'('0'..'9')+; 

/* definicao para erro - simbolo nao identificado, nao faz parte da linguagem  */
ERRO:'@' | '$' | '¨' | '.' | '~' | '!'; 

/* nao gerar token para espacos, tabulacoes, quebras de linha */
WS: [ \t\r\n]+ -> skip; 

/* definicao para erro de cadeia?   */

/* definicao para erro de comentario?   */