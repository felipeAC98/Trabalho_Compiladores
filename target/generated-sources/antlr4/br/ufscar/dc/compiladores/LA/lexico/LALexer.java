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
		COMENTARIO=1, PALAVRAS_CHAVE=2, SIMBOLOS=3, OPERADORES_ARITMETICOS=4, 
		OP_RELACIONAL=5, OP_LOGICOs=6, IDENT=7, CADEIA=8, NUM_INT=9, NUM_REAL=10, 
		ERRO=11, WS=12;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"COMENTARIO", "PALAVRAS_CHAVE", "SIMBOLOS", "OPERADORES_ARITMETICOS", 
			"OP_RELACIONAL", "OP_LOGICOs", "IDENT", "CADEIA", "NUM_INT", "NUM_REAL", 
			"ERRO", "WS"
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
			null, "COMENTARIO", "PALAVRAS_CHAVE", "SIMBOLOS", "OPERADORES_ARITMETICOS", 
			"OP_RELACIONAL", "OP_LOGICOs", "IDENT", "CADEIA", "NUM_INT", "NUM_REAL", 
			"ERRO", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\16\u0158\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\3\2\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\2\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\5\3\u011b\n\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0129"+
		"\n\6\3\7\3\7\3\7\5\7\u012e\n\7\3\b\3\b\7\b\u0132\n\b\f\b\16\b\u0135\13"+
		"\b\3\t\3\t\7\t\u0139\n\t\f\t\16\t\u013c\13\t\3\t\3\t\3\n\6\n\u0141\n\n"+
		"\r\n\16\n\u0142\3\13\6\13\u0146\n\13\r\13\16\13\u0147\3\13\3\13\6\13\u014c"+
		"\n\13\r\13\16\13\u014d\3\f\3\f\3\r\6\r\u0153\n\r\r\r\16\r\u0154\3\r\3"+
		"\r\2\2\16\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\3\2"+
		"\13\t\2\"\"*+..\62;C\\c|\u00c2\u00f2\t\2((*+./<<??]]_`\6\2\'\',-//\61"+
		"\61\4\2>>@@\5\2C\\aac|\6\2\62;C\\aac|\4\2\f\f$$\b\2##&&\60\60BB\u0080"+
		"\u0080\u00aa\u00aa\5\2\13\f\17\17\"\"\2\u0186\2\3\3\2\2\2\2\5\3\2\2\2"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33\3\2\2"+
		"\2\5\u011a\3\2\2\2\7\u011c\3\2\2\2\t\u011e\3\2\2\2\13\u0128\3\2\2\2\r"+
		"\u012d\3\2\2\2\17\u012f\3\2\2\2\21\u0136\3\2\2\2\23\u0140\3\2\2\2\25\u0145"+
		"\3\2\2\2\27\u014f\3\2\2\2\31\u0152\3\2\2\2\33\37\7}\2\2\34\36\t\2\2\2"+
		"\35\34\3\2\2\2\36!\3\2\2\2\37\35\3\2\2\2\37 \3\2\2\2 \"\3\2\2\2!\37\3"+
		"\2\2\2\"#\7\177\2\2#$\3\2\2\2$%\b\2\2\2%\4\3\2\2\2&\'\7c\2\2\'(\7n\2\2"+
		"()\7i\2\2)*\7q\2\2*+\7t\2\2+,\7k\2\2,-\7v\2\2-.\7o\2\2.\u011b\7q\2\2/"+
		"\60\7h\2\2\60\61\7k\2\2\61\62\7o\2\2\62\63\7a\2\2\63\64\7c\2\2\64\65\7"+
		"n\2\2\65\66\7i\2\2\66\67\7q\2\2\678\7t\2\289\7k\2\29:\7v\2\2:;\7o\2\2"+
		";\u011b\7q\2\2<=\7f\2\2=>\7g\2\2>?\7e\2\2?@\7n\2\2@A\7c\2\2AB\7t\2\2B"+
		"\u011b\7g\2\2CD\7e\2\2DE\7q\2\2EF\7p\2\2FG\7u\2\2GH\7v\2\2HI\7c\2\2IJ"+
		"\7p\2\2JK\7v\2\2K\u011b\7g\2\2LM\7v\2\2MN\7k\2\2NO\7r\2\2O\u011b\7q\2"+
		"\2PQ\7n\2\2QR\7k\2\2RS\7v\2\2ST\7g\2\2TU\7t\2\2UV\7c\2\2V\u011b\7n\2\2"+
		"WX\7k\2\2XY\7p\2\2YZ\7v\2\2Z[\7g\2\2[\\\7k\2\2\\]\7t\2\2]\u011b\7q\2\2"+
		"^_\7t\2\2_`\7g\2\2`a\7c\2\2a\u011b\7n\2\2bc\7n\2\2cd\7q\2\2de\7i\2\2e"+
		"f\7k\2\2fg\7e\2\2g\u011b\7q\2\2hi\7x\2\2ij\7g\2\2jk\7t\2\2kl\7f\2\2lm"+
		"\7c\2\2mn\7f\2\2no\7g\2\2op\7k\2\2pq\7t\2\2q\u011b\7q\2\2rs\7h\2\2st\7"+
		"c\2\2tu\7n\2\2uv\7u\2\2v\u011b\7q\2\2wx\7t\2\2xy\7g\2\2yz\7i\2\2z{\7k"+
		"\2\2{|\7u\2\2|}\7v\2\2}~\7t\2\2~\u011b\7q\2\2\177\u0080\7h\2\2\u0080\u0081"+
		"\7k\2\2\u0081\u0082\7o\2\2\u0082\u0083\7a\2\2\u0083\u0084\7t\2\2\u0084"+
		"\u0085\7g\2\2\u0085\u0086\7i\2\2\u0086\u0087\7k\2\2\u0087\u0088\7u\2\2"+
		"\u0088\u0089\7v\2\2\u0089\u008a\7t\2\2\u008a\u011b\7q\2\2\u008b\u008c"+
		"\7r\2\2\u008c\u008d\7t\2\2\u008d\u008e\7q\2\2\u008e\u008f\7e\2\2\u008f"+
		"\u0090\7g\2\2\u0090\u0091\7f\2\2\u0091\u0092\7k\2\2\u0092\u0093\7o\2\2"+
		"\u0093\u0094\7g\2\2\u0094\u0095\7p\2\2\u0095\u0096\7v\2\2\u0096\u011b"+
		"\7q\2\2\u0097\u0098\7h\2\2\u0098\u0099\7k\2\2\u0099\u009a\7o\2\2\u009a"+
		"\u009b\7a\2\2\u009b\u009c\7r\2\2\u009c\u009d\7t\2\2\u009d\u009e\7q\2\2"+
		"\u009e\u009f\7e\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1\7f\2\2\u00a1\u00a2"+
		"\7k\2\2\u00a2\u00a3\7o\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5\7p\2\2\u00a5"+
		"\u00a6\7v\2\2\u00a6\u011b\7q\2\2\u00a7\u00a8\7h\2\2\u00a8\u00a9\7w\2\2"+
		"\u00a9\u00aa\7p\2\2\u00aa\u00ab\7e\2\2\u00ab\u00ac\7c\2\2\u00ac\u011b"+
		"\7q\2\2\u00ad\u00ae\7h\2\2\u00ae\u00af\7k\2\2\u00af\u00b0\7o\2\2\u00b0"+
		"\u00b1\7a\2\2\u00b1\u00b2\7h\2\2\u00b2\u00b3\7w\2\2\u00b3\u00b4\7p\2\2"+
		"\u00b4\u00b5\7e\2\2\u00b5\u00b6\7c\2\2\u00b6\u011b\7q\2\2\u00b7\u00b8"+
		"\7x\2\2\u00b8\u00b9\7c\2\2\u00b9\u011b\7t\2\2\u00ba\u00bb\7n\2\2\u00bb"+
		"\u00bc\7g\2\2\u00bc\u00bd\7k\2\2\u00bd\u011b\7c\2\2\u00be\u00bf\7g\2\2"+
		"\u00bf\u00c0\7u\2\2\u00c0\u00c1\7e\2\2\u00c1\u00c2\7t\2\2\u00c2\u00c3"+
		"\7g\2\2\u00c3\u00c4\7x\2\2\u00c4\u011b\7c\2\2\u00c5\u00c6\7u\2\2\u00c6"+
		"\u011b\7g\2\2\u00c7\u00c8\7g\2\2\u00c8\u00c9\7p\2\2\u00c9\u00ca\7v\2\2"+
		"\u00ca\u00cb\7c\2\2\u00cb\u011b\7q\2\2\u00cc\u00cd\7u\2\2\u00cd\u00ce"+
		"\7g\2\2\u00ce\u00cf\7p\2\2\u00cf\u00d0\7c\2\2\u00d0\u011b\7q\2\2\u00d1"+
		"\u00d2\7h\2\2\u00d2\u00d3\7k\2\2\u00d3\u00d4\7o\2\2\u00d4\u00d5\7a\2\2"+
		"\u00d5\u00d6\7u\2\2\u00d6\u011b\7g\2\2\u00d7\u00d8\7e\2\2\u00d8\u00d9"+
		"\7c\2\2\u00d9\u00da\7u\2\2\u00da\u011b\7q\2\2\u00db\u00dc\7u\2\2\u00dc"+
		"\u00dd\7g\2\2\u00dd\u00de\7l\2\2\u00de\u011b\7c\2\2\u00df\u00e0\7h\2\2"+
		"\u00e0\u00e1\7k\2\2\u00e1\u00e2\7o\2\2\u00e2\u00e3\7a\2\2\u00e3\u00e4"+
		"\7e\2\2\u00e4\u00e5\7c\2\2\u00e5\u00e6\7u\2\2\u00e6\u011b\7q\2\2\u00e7"+
		"\u00e8\7r\2\2\u00e8\u00e9\7c\2\2\u00e9\u00ea\7t\2\2\u00ea\u011b\7c\2\2"+
		"\u00eb\u00ec\7c\2\2\u00ec\u00ed\7v\2\2\u00ed\u011b\7g\2\2\u00ee\u00ef"+
		"\7h\2\2\u00ef\u00f0\7c\2\2\u00f0\u00f1\7e\2\2\u00f1\u011b\7c\2\2\u00f2"+
		"\u00f3\7h\2\2\u00f3\u00f4\7k\2\2\u00f4\u00f5\7o\2\2\u00f5\u00f6\7a\2\2"+
		"\u00f6\u00f7\7r\2\2\u00f7\u00f8\7c\2\2\u00f8\u00f9\7t\2\2\u00f9\u011b"+
		"\7c\2\2\u00fa\u00fb\7g\2\2\u00fb\u00fc\7p\2\2\u00fc\u00fd\7s\2\2\u00fd"+
		"\u00fe\7w\2\2\u00fe\u00ff\7c\2\2\u00ff\u0100\7p\2\2\u0100\u0101\7v\2\2"+
		"\u0101\u011b\7q\2\2\u0102\u0103\7h\2\2\u0103\u0104\7k\2\2\u0104\u0105"+
		"\7o\2\2\u0105\u0106\7a\2\2\u0106\u0107\7g\2\2\u0107\u0108\7p\2\2\u0108"+
		"\u0109\7s\2\2\u0109\u010a\7w\2\2\u010a\u010b\7c\2\2\u010b\u010c\7p\2\2"+
		"\u010c\u010d\7v\2\2\u010d\u011b\7q\2\2\u010e\u010f\7t\2\2\u010f\u0110"+
		"\7g\2\2\u0110\u0111\7v\2\2\u0111\u0112\7q\2\2\u0112\u0113\7t\2\2\u0113"+
		"\u0114\7p\2\2\u0114\u011b\7g\2\2\u0115\u0116\7p\2\2\u0116\u0117\7c\2\2"+
		"\u0117\u011b\7q\2\2\u0118\u0119\7>\2\2\u0119\u011b\7/\2\2\u011a&\3\2\2"+
		"\2\u011a/\3\2\2\2\u011a<\3\2\2\2\u011aC\3\2\2\2\u011aL\3\2\2\2\u011aP"+
		"\3\2\2\2\u011aW\3\2\2\2\u011a^\3\2\2\2\u011ab\3\2\2\2\u011ah\3\2\2\2\u011a"+
		"r\3\2\2\2\u011aw\3\2\2\2\u011a\177\3\2\2\2\u011a\u008b\3\2\2\2\u011a\u0097"+
		"\3\2\2\2\u011a\u00a7\3\2\2\2\u011a\u00ad\3\2\2\2\u011a\u00b7\3\2\2\2\u011a"+
		"\u00ba\3\2\2\2\u011a\u00be\3\2\2\2\u011a\u00c5\3\2\2\2\u011a\u00c7\3\2"+
		"\2\2\u011a\u00cc\3\2\2\2\u011a\u00d1\3\2\2\2\u011a\u00d7\3\2\2\2\u011a"+
		"\u00db\3\2\2\2\u011a\u00df\3\2\2\2\u011a\u00e7\3\2\2\2\u011a\u00eb\3\2"+
		"\2\2\u011a\u00ee\3\2\2\2\u011a\u00f2\3\2\2\2\u011a\u00fa\3\2\2\2\u011a"+
		"\u0102\3\2\2\2\u011a\u010e\3\2\2\2\u011a\u0115\3\2\2\2\u011a\u0118\3\2"+
		"\2\2\u011b\6\3\2\2\2\u011c\u011d\t\3\2\2\u011d\b\3\2\2\2\u011e\u011f\t"+
		"\4\2\2\u011f\n\3\2\2\2\u0120\u0129\7?\2\2\u0121\u0122\7>\2\2\u0122\u0129"+
		"\7@\2\2\u0123\u0124\7@\2\2\u0124\u0129\7?\2\2\u0125\u0126\7>\2\2\u0126"+
		"\u0129\7?\2\2\u0127\u0129\t\5\2\2\u0128\u0120\3\2\2\2\u0128\u0121\3\2"+
		"\2\2\u0128\u0123\3\2\2\2\u0128\u0125\3\2\2\2\u0128\u0127\3\2\2\2\u0129"+
		"\f\3\2\2\2\u012a\u012b\7q\2\2\u012b\u012e\7w\2\2\u012c\u012e\7g\2\2\u012d"+
		"\u012a\3\2\2\2\u012d\u012c\3\2\2\2\u012e\16\3\2\2\2\u012f\u0133\t\6\2"+
		"\2\u0130\u0132\t\7\2\2\u0131\u0130\3\2\2\2\u0132\u0135\3\2\2\2\u0133\u0131"+
		"\3\2\2\2\u0133\u0134\3\2\2\2\u0134\20\3\2\2\2\u0135\u0133\3\2\2\2\u0136"+
		"\u013a\7$\2\2\u0137\u0139\n\b\2\2\u0138\u0137\3\2\2\2\u0139\u013c\3\2"+
		"\2\2\u013a\u0138\3\2\2\2\u013a\u013b\3\2\2\2\u013b\u013d\3\2\2\2\u013c"+
		"\u013a\3\2\2\2\u013d\u013e\7$\2\2\u013e\22\3\2\2\2\u013f\u0141\4\62;\2"+
		"\u0140\u013f\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0140\3\2\2\2\u0142\u0143"+
		"\3\2\2\2\u0143\24\3\2\2\2\u0144\u0146\4\62;\2\u0145\u0144\3\2\2\2\u0146"+
		"\u0147\3\2\2\2\u0147\u0145\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u0149\3\2"+
		"\2\2\u0149\u014b\7\60\2\2\u014a\u014c\4\62;\2\u014b\u014a\3\2\2\2\u014c"+
		"\u014d\3\2\2\2\u014d\u014b\3\2\2\2\u014d\u014e\3\2\2\2\u014e\26\3\2\2"+
		"\2\u014f\u0150\t\t\2\2\u0150\30\3\2\2\2\u0151\u0153\t\n\2\2\u0152\u0151"+
		"\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0152\3\2\2\2\u0154\u0155\3\2\2\2\u0155"+
		"\u0156\3\2\2\2\u0156\u0157\b\r\2\2\u0157\32\3\2\2\2\r\2\37\u011a\u0128"+
		"\u012d\u0133\u013a\u0142\u0147\u014d\u0154\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}