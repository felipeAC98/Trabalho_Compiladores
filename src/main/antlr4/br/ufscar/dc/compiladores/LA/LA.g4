/* ------------------------------------------------------------ */
/*   >>  TRABALHO 1 - CONSTRUÇÃO DE COMPILADORES 1   <<         */
/*								*/ 
/*		ALUNOS: Felipe Alves        RA: 744335         	*/
/*			Karolayne Arrais    RA: 726460		*/	
/*			Mariana Zagatti     RA:	628620  	*/
/* ------------------------------------------------------------ */				

grammar LA;

/*comentarios nao devem gerar tokens*/
COMENTARIO: COMENTARIO_ERRADO
            '}'
            -> skip;

/* definicao para erro de comentario */
COMENTARIO_ERRADO: '{'
            (' ' | 'A'..'Z' | 'a'..'z' | '0'..'9' | '\u0080'..'\uffff' | SIMBOLOS | SIMBOLO_NAODEFINIDO | CADEIA | OP_RELACIONAL|OPERADORES_ARITMETICOS )*
            ;

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
				| 'retorne' | 'nao' | '<-' ; 

/* define caracteres que nao sao letras */
SIMBOLOS: ':' | '=' | ',' | '[' | ']' | '^' | '(' | ')' | '-' | '&' | '..' | '.'; 

/* define operadores aritmeticos */
OPERADORES_ARITMETICOS: '+' | '-' | '*' | '/' |'%';

/* define operadores relacionais */
OP_RELACIONAL: '=' | '<>' | '>=' | '<=' | '>' | '<';

/* define operador logicos */
op_logico_1: 'ou';
op_logico_2: 'e';

/* define identificadores da linguagem
apenas com restricao de nao inicializar com caracteres numericos, podendo conter apenas _ como caractere especial alem de letras e numeros*/
IDENT: ('A'..'Z' | 'a'..'z' | '_')('A'..'Z' | 'a'..'z' | '0'..'9' | '_')*; 

/* define cadeias da linguagem  */
/* sequencia ao longo do algoritmo que necessariamente comeca e finaliza com aspas  */
/* onde entre essas aspas nao pode ocorrer " ou quebra de linha */
CADEIA: '"'(~('"'|'\n'))*'"';    

/* definicao para erro de cadeia  */
ERRO_CADEIA: '"'(~('"'|'\n'))*;

/* definicao de numeros inteiros    */
/* formados pela sequencia de 1 ou + caracteres numericos de 0 a 9.*/
NUM_INT: ('0'..'9')+; 

/* definicao de numeros reais  */
/* seelhante ao numero int, com a regra de ter o caractere '.' entre duas sequencias de caracteres numericos de 0 a 9.*/
NUM_REAL: ('0'..'9')+'.'('0'..'9')+; 

/* definicao para erro - simbolo nao identificado, nao faz parte da linguagem  */
ERRO_SIMBOLO: SIMBOLO_NAODEFINIDO | '}'; 

/* definicao para erro - simbolo nao identificado, nao faz parte da linguagem  */
SIMBOLO_NAODEFINIDO:'@' | '$' | '¨' | '~' | '!' | ';' | '%' | '?'| '|'; 

/* nao gerar token para espacos, tabulacoes, quebras de linha */
WS: [ \t\r\n]+ -> skip;

// definicao da gramatica da linguagem LA definida no manual disponibilizado
// ? para sintaxe nao gananciosa  - zero ou uma vez

programa: declaracoes 'algoritmo' corpo 'fim_algoritmo';

declaracoes: (decl_local_global)*;

decl_local_global: declaracao_local | declaracao_global;

declaracao_local:  'declare' variavel 
                  | 'constante' IDENT ':' tipo_basico '=' valor_constante
                  | 'tipo' IDENT ':' tipo;

variavel: identificador (',' identificador)* ':' tipo;

identificador: IDENT (reg='.' IDENT)* dimensao;

dimensao: ('[' exp_aritmetica ']')*;

tipo: registro | tipo_estendido;

tipo_basico: 'literal' | 'inteiro' | 'real' | 'logico';

tipo_basico_ident: tipo_basico | IDENT;

tipo_estendido: (pont='^')? tipo_basico_ident;

valor_constante: CADEIA | NUM_INT | NUM_REAL | 'verdadeiro' | 'falso';

registro: 'registro' (variavel)* 'fim_registro';

declaracao_global: inicioProc='procedimento' IDENT '(' (parametros)? ')' (declaracao_local)* (cmd)* fimProc='fim_procedimento'
                    | inicioFuncao='funcao' IDENT '(' (parametros)? ')' ':' tipo_estendido (declaracao_local)* (cmd)* fimFunc='fim_funcao';

parametro: ('var')? identificador (',' identificador)* ':' tipo_estendido;

parametros: parametro (',' parametro)*;

corpo: (declaracao_local)* (cmd)*;

cmd: cmdleia | cmdescreva | cmdse | cmdcaso | cmdpara | cmdenquanto
     | cmdfaca | cmdatribuicao | cmdchamada | cmdretorne;

cmdleia: 'leia' '(' (pont='^')? identificador (',' (pont='^')? identificador)* ')';
cmdescreva: 'escreva' '(' expressao (',' expressao)* ')';
cmdse: 'se' expressao 'entao' (cmd)* ('senao' (cmd)*)? 'fim_se';
cmdcaso: 'caso' exp_aritmetica 'seja' selecao ('senao' (cmd)*)? 'fim_caso';
cmdpara: 'para' IDENT '<-' exp_aritmetica 'ate' exp_aritmetica 'faca' (cmd)* 'fim_para';
cmdenquanto: 'enquanto' expressao 'faca' (cmd)* 'fim_enquanto';
cmdfaca: 'faca' (cmd)* 'ate' expressao;
cmdatribuicao: (pont='^')? identificador '<-' expressao;
cmdchamada: IDENT '(' expressao (',' expressao)* ')';
cmdretorne: 'retorne' expressao;

selecao: (item_selecao)*;
item_selecao: constantes ':' (cmd)*;

constantes: numero_intervalo (',' numero_intervalo)*;

numero_intervalo: (op_unario)? NUM_INT ('..' (op_unario)? NUM_INT)?;

op_unario: '-';

exp_aritmetica: termo (op1 termo)*;
termo: fator (op2 fator)*;
fator: parcela (op3 parcela)*;

op1: '+' | '-';
op2: '*' | '/';
op3: '%';

parcela: (op_unario)? parcela_unario | parcela_nao_unario;
parcela_unario: (pont='^')? identificador
                | IDENT '(' expressao (',' expressao)* ')'
                | NUM_INT
                | NUM_REAL
                | parUnExp='(' expressao ')';
parcela_nao_unario: '&' identificador | CADEIA;

exp_relacional: exp_aritmetica (op_relacional exp_aritmetica)*;
/* define operadores relacionais */
op_relacional: '=' | '<>' | '>=' | '<=' | '>' | '<';

expressao: termo_logico (op_logico_1 termo_logico)*;
termo_logico: fator_logico (op_logico_2 fator_logico)*;
fator_logico: (not='nao')? parcela_logica;
parcela_logica: ('verdadeiro' | 'falso') | exp_relacional;