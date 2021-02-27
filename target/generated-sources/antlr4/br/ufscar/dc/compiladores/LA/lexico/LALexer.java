// Generated from br\u005Cufscar\dc\compiladores\LA\lexico\LALexer.g4 by ANTLR 4.7.2
package br.ufscar.dc.compiladores.LA.lexico;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class LALexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PALAVRAS_CHAVE=1, OPERADORES_ARITMETICOS=2, OP_RELACIONAL=3, OP_LOGICOs=4, 
		SIMBOLOS=5, IDENT=6, CADEIA=7, NUM_INT=8, NUM_REAL=9, ERRO=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"PALAVRAS_CHAVE", "OPERADORES_ARITMETICOS", "OP_RELACIONAL", "OP_LOGICOs", 
			"SIMBOLOS", "IDENT", "CADEIA", "NUM_INT", "NUM_REAL", "ERRO"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PALAVRAS_CHAVE", "OPERADORES_ARITMETICOS", "OP_RELACIONAL", "OP_LOGICOs", 
			"SIMBOLOS", "IDENT", "CADEIA", "NUM_INT", "NUM_REAL", "ERRO"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public LALexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "LALexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\f\u0143\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3"+
		"\2\3\2\5\2\u010d\n\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4\u0119"+
		"\n\4\3\5\3\5\3\5\5\5\u011e\n\5\3\6\3\6\3\6\3\6\5\6\u0124\n\6\3\7\3\7\3"+
		"\7\3\b\3\b\7\b\u012b\n\b\f\b\16\b\u012e\13\b\3\b\3\b\3\t\6\t\u0133\n\t"+
		"\r\t\16\t\u0134\3\n\6\n\u0138\n\n\r\n\16\n\u0139\3\n\3\n\6\n\u013e\n\n"+
		"\r\n\16\n\u013f\3\13\3\13\2\2\f\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\3\2\n\6\2\'\',-//\61\61\4\2>>@@\t\2*+..\60\60<<??]]_`\4\2((/"+
		"/\5\2C\\aac|\6\2\62;C\\aac|\4\2\f\f$$\b\2##&&\60\60BB\u0080\u0080\u00aa"+
		"\u00aa\2\u0171\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3"+
		"\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2"+
		"\3\u010c\3\2\2\2\5\u010e\3\2\2\2\7\u0118\3\2\2\2\t\u011d\3\2\2\2\13\u0123"+
		"\3\2\2\2\r\u0125\3\2\2\2\17\u0128\3\2\2\2\21\u0132\3\2\2\2\23\u0137\3"+
		"\2\2\2\25\u0141\3\2\2\2\27\30\7c\2\2\30\31\7n\2\2\31\32\7i\2\2\32\33\7"+
		"q\2\2\33\34\7t\2\2\34\35\7k\2\2\35\36\7v\2\2\36\37\7o\2\2\37\u010d\7q"+
		"\2\2 !\7h\2\2!\"\7k\2\2\"#\7o\2\2#$\7a\2\2$%\7c\2\2%&\7n\2\2&\'\7i\2\2"+
		"\'(\7q\2\2()\7t\2\2)*\7k\2\2*+\7v\2\2+,\7o\2\2,\u010d\7q\2\2-.\7f\2\2"+
		"./\7g\2\2/\60\7e\2\2\60\61\7n\2\2\61\62\7c\2\2\62\63\7t\2\2\63\u010d\7"+
		"g\2\2\64\65\7e\2\2\65\66\7q\2\2\66\67\7p\2\2\678\7u\2\289\7v\2\29:\7c"+
		"\2\2:;\7p\2\2;<\7v\2\2<\u010d\7g\2\2=>\7v\2\2>?\7k\2\2?@\7r\2\2@\u010d"+
		"\7q\2\2AB\7n\2\2BC\7k\2\2CD\7v\2\2DE\7g\2\2EF\7t\2\2FG\7c\2\2G\u010d\7"+
		"n\2\2HI\7k\2\2IJ\7p\2\2JK\7v\2\2KL\7g\2\2LM\7k\2\2MN\7t\2\2N\u010d\7q"+
		"\2\2OP\7t\2\2PQ\7g\2\2QR\7c\2\2R\u010d\7n\2\2ST\7n\2\2TU\7q\2\2UV\7i\2"+
		"\2VW\7k\2\2WX\7e\2\2X\u010d\7q\2\2YZ\7x\2\2Z[\7g\2\2[\\\7t\2\2\\]\7f\2"+
		"\2]^\7c\2\2^_\7f\2\2_`\7g\2\2`a\7k\2\2ab\7t\2\2b\u010d\7q\2\2cd\7h\2\2"+
		"de\7c\2\2ef\7n\2\2fg\7u\2\2g\u010d\7q\2\2hi\7t\2\2ij\7g\2\2jk\7i\2\2k"+
		"l\7k\2\2lm\7u\2\2mn\7v\2\2no\7t\2\2o\u010d\7q\2\2pq\7h\2\2qr\7k\2\2rs"+
		"\7o\2\2st\7a\2\2tu\7t\2\2uv\7g\2\2vw\7i\2\2wx\7k\2\2xy\7u\2\2yz\7v\2\2"+
		"z{\7t\2\2{\u010d\7q\2\2|}\7r\2\2}~\7t\2\2~\177\7q\2\2\177\u0080\7e\2\2"+
		"\u0080\u0081\7g\2\2\u0081\u0082\7f\2\2\u0082\u0083\7k\2\2\u0083\u0084"+
		"\7o\2\2\u0084\u0085\7g\2\2\u0085\u0086\7p\2\2\u0086\u0087\7v\2\2\u0087"+
		"\u010d\7q\2\2\u0088\u0089\7h\2\2\u0089\u008a\7k\2\2\u008a\u008b\7o\2\2"+
		"\u008b\u008c\7a\2\2\u008c\u008d\7r\2\2\u008d\u008e\7t\2\2\u008e\u008f"+
		"\7q\2\2\u008f\u0090\7e\2\2\u0090\u0091\7g\2\2\u0091\u0092\7f\2\2\u0092"+
		"\u0093\7k\2\2\u0093\u0094\7o\2\2\u0094\u0095\7g\2\2\u0095\u0096\7p\2\2"+
		"\u0096\u0097\7v\2\2\u0097\u010d\7q\2\2\u0098\u0099\7h\2\2\u0099\u009a"+
		"\7w\2\2\u009a\u009b\7p\2\2\u009b\u009c\7e\2\2\u009c\u009d\7c\2\2\u009d"+
		"\u010d\7q\2\2\u009e\u009f\7h\2\2\u009f\u00a0\7k\2\2\u00a0\u00a1\7o\2\2"+
		"\u00a1\u00a2\7a\2\2\u00a2\u00a3\7h\2\2\u00a3\u00a4\7w\2\2\u00a4\u00a5"+
		"\7p\2\2\u00a5\u00a6\7e\2\2\u00a6\u00a7\7c\2\2\u00a7\u010d\7q\2\2\u00a8"+
		"\u00a9\7x\2\2\u00a9\u00aa\7c\2\2\u00aa\u010d\7t\2\2\u00ab\u00ac\7n\2\2"+
		"\u00ac\u00ad\7g\2\2\u00ad\u00ae\7k\2\2\u00ae\u010d\7c\2\2\u00af\u00b0"+
		"\7g\2\2\u00b0\u00b1\7u\2\2\u00b1\u00b2\7e\2\2\u00b2\u00b3\7t\2\2\u00b3"+
		"\u00b4\7g\2\2\u00b4\u00b5\7x\2\2\u00b5\u010d\7c\2\2\u00b6\u010d\3\2\2"+
		"\2\u00b7\u00b8\7u\2\2\u00b8\u010d\7g\2\2\u00b9\u00ba\7g\2\2\u00ba\u00bb"+
		"\7p\2\2\u00bb\u00bc\7v\2\2\u00bc\u00bd\7c\2\2\u00bd\u010d\7q\2\2\u00be"+
		"\u00bf\7u\2\2\u00bf\u00c0\7g\2\2\u00c0\u00c1\7p\2\2\u00c1\u00c2\7c\2\2"+
		"\u00c2\u010d\7q\2\2\u00c3\u00c4\7h\2\2\u00c4\u00c5\7k\2\2\u00c5\u00c6"+
		"\7o\2\2\u00c6\u00c7\7a\2\2\u00c7\u00c8\7u\2\2\u00c8\u010d\7g\2\2\u00c9"+
		"\u00ca\7e\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7u\2\2\u00cc\u010d\7q\2\2"+
		"\u00cd\u00ce\7u\2\2\u00ce\u00cf\7g\2\2\u00cf\u00d0\7l\2\2\u00d0\u010d"+
		"\7c\2\2\u00d1\u00d2\7h\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7o\2\2\u00d4"+
		"\u00d5\7a\2\2\u00d5\u00d6\7e\2\2\u00d6\u00d7\7c\2\2\u00d7\u00d8\7u\2\2"+
		"\u00d8\u010d\7q\2\2\u00d9\u00da\7r\2\2\u00da\u00db\7c\2\2\u00db\u00dc"+
		"\7t\2\2\u00dc\u010d\7c\2\2\u00dd\u00de\7c\2\2\u00de\u00df\7v\2\2\u00df"+
		"\u010d\7g\2\2\u00e0\u00e1\7h\2\2\u00e1\u00e2\7c\2\2\u00e2\u00e3\7e\2\2"+
		"\u00e3\u010d\7c\2\2\u00e4\u00e5\7h\2\2\u00e5\u00e6\7k\2\2\u00e6\u00e7"+
		"\7o\2\2\u00e7\u00e8\7a\2\2\u00e8\u00e9\7r\2\2\u00e9\u00ea\7c\2\2\u00ea"+
		"\u00eb\7t\2\2\u00eb\u010d\7c\2\2\u00ec\u00ed\7g\2\2\u00ed\u00ee\7p\2\2"+
		"\u00ee\u00ef\7s\2\2\u00ef\u00f0\7w\2\2\u00f0\u00f1\7c\2\2\u00f1\u00f2"+
		"\7p\2\2\u00f2\u00f3\7v\2\2\u00f3\u010d\7q\2\2\u00f4\u00f5\7h\2\2\u00f5"+
		"\u00f6\7k\2\2\u00f6\u00f7\7o\2\2\u00f7\u00f8\7a\2\2\u00f8\u00f9\7g\2\2"+
		"\u00f9\u00fa\7p\2\2\u00fa\u00fb\7s\2\2\u00fb\u00fc\7w\2\2\u00fc\u00fd"+
		"\7c\2\2\u00fd\u00fe\7p\2\2\u00fe\u00ff\7v\2\2\u00ff\u010d\7q\2\2\u0100"+
		"\u0101\7t\2\2\u0101\u0102\7g\2\2\u0102\u0103\7v\2\2\u0103\u0104\7q\2\2"+
		"\u0104\u0105\7t\2\2\u0105\u0106\7p\2\2\u0106\u010d\7g\2\2\u0107\u0108"+
		"\7p\2\2\u0108\u0109\7c\2\2\u0109\u010d\7q\2\2\u010a\u010b\7>\2\2\u010b"+
		"\u010d\7/\2\2\u010c\27\3\2\2\2\u010c \3\2\2\2\u010c-\3\2\2\2\u010c\64"+
		"\3\2\2\2\u010c=\3\2\2\2\u010cA\3\2\2\2\u010cH\3\2\2\2\u010cO\3\2\2\2\u010c"+
		"S\3\2\2\2\u010cY\3\2\2\2\u010cc\3\2\2\2\u010ch\3\2\2\2\u010cp\3\2\2\2"+
		"\u010c|\3\2\2\2\u010c\u0088\3\2\2\2\u010c\u0098\3\2\2\2\u010c\u009e\3"+
		"\2\2\2\u010c\u00a8\3\2\2\2\u010c\u00ab\3\2\2\2\u010c\u00af\3\2\2\2\u010c"+
		"\u00b6\3\2\2\2\u010c\u00b7\3\2\2\2\u010c\u00b9\3\2\2\2\u010c\u00be\3\2"+
		"\2\2\u010c\u00c3\3\2\2\2\u010c\u00c9\3\2\2\2\u010c\u00cd\3\2\2\2\u010c"+
		"\u00d1\3\2\2\2\u010c\u00d9\3\2\2\2\u010c\u00dd\3\2\2\2\u010c\u00e0\3\2"+
		"\2\2\u010c\u00e4\3\2\2\2\u010c\u00ec\3\2\2\2\u010c\u00f4\3\2\2\2\u010c"+
		"\u0100\3\2\2\2\u010c\u0107\3\2\2\2\u010c\u010a\3\2\2\2\u010d\4\3\2\2\2"+
		"\u010e\u010f\t\2\2\2\u010f\6\3\2\2\2\u0110\u0119\7?\2\2\u0111\u0112\7"+
		">\2\2\u0112\u0119\7@\2\2\u0113\u0114\7@\2\2\u0114\u0119\7?\2\2\u0115\u0116"+
		"\7>\2\2\u0116\u0119\7?\2\2\u0117\u0119\t\3\2\2\u0118\u0110\3\2\2\2\u0118"+
		"\u0111\3\2\2\2\u0118\u0113\3\2\2\2\u0118\u0115\3\2\2\2\u0118\u0117\3\2"+
		"\2\2\u0119\b\3\2\2\2\u011a\u011b\7q\2\2\u011b\u011e\7w\2\2\u011c\u011e"+
		"\7g\2\2\u011d\u011a\3\2\2\2\u011d\u011c\3\2\2\2\u011e\n\3\2\2\2\u011f"+
		"\u0124\t\4\2\2\u0120\u0121\7\60\2\2\u0121\u0124\7\60\2\2\u0122\u0124\t"+
		"\5\2\2\u0123\u011f\3\2\2\2\u0123\u0120\3\2\2\2\u0123\u0122\3\2\2\2\u0124"+
		"\f\3\2\2\2\u0125\u0126\t\6\2\2\u0126\u0127\t\7\2\2\u0127\16\3\2\2\2\u0128"+
		"\u012c\7$\2\2\u0129\u012b\n\b\2\2\u012a\u0129\3\2\2\2\u012b\u012e\3\2"+
		"\2\2\u012c\u012a\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012f\3\2\2\2\u012e"+
		"\u012c\3\2\2\2\u012f\u0130\7$\2\2\u0130\20\3\2\2\2\u0131\u0133\4\62;\2"+
		"\u0132\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0132\3\2\2\2\u0134\u0135"+
		"\3\2\2\2\u0135\22\3\2\2\2\u0136\u0138\4\62;\2\u0137\u0136\3\2\2\2\u0138"+
		"\u0139\3\2\2\2\u0139\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u013b\3\2"+
		"\2\2\u013b\u013d\7\60\2\2\u013c\u013e\4\62;\2\u013d\u013c\3\2\2\2\u013e"+
		"\u013f\3\2\2\2\u013f\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\24\3\2\2"+
		"\2\u0141\u0142\t\t\2\2\u0142\26\3\2\2\2\13\2\u010c\u0118\u011d\u0123\u012c"+
		"\u0134\u0139\u013f\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}