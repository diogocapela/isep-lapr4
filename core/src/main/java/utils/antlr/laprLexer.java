// Generated from C:/Users/rafa_/Desktop/lapr4-2018-2019-grupona-3/core/src/main/java/utils/antlr\lapr.g4 by ANTLR 4.7.2
package utils.antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class laprLexer extends Lexer {
    public static final int
        T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, OUT = 5, LIM = 6, VAL = 7, OP = 8, NL = 9, WS = 10;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\fb\b\1\4\2\t\2\4" +
            "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t" +
            "\13\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3" +
            "\5\3\5\3\6\3\6\7\6,\n\6\f\6\16\6/\13\6\3\7\6\7\62\n\7\r\7\16\7\63\3\7" +
            "\3\7\3\7\3\7\3\b\6\b;\n\b\r\b\16\b<\3\b\3\b\6\bA\n\b\r\b\16\bB\5\bE\n" +
            "\b\3\b\3\b\3\b\5\bJ\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tV\n" +
            "\t\3\n\5\nY\n\n\3\n\3\n\5\n]\n\n\3\13\3\13\3\13\3\13\2\2\f\3\3\5\4\7\5" +
            "\t\6\13\7\r\b\17\t\21\n\23\13\25\f\3\2\6\3\2\63;\3\2\62;\3\2\60\60\3\2" +
            "\"\"\2n\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2" +
            "\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3\27\3" +
            "\2\2\2\5\32\3\2\2\2\7\34\3\2\2\2\t$\3\2\2\2\13)\3\2\2\2\r\61\3\2\2\2\17" +
            "I\3\2\2\2\21U\3\2\2\2\23\\\3\2\2\2\25^\3\2\2\2\27\30\7k\2\2\30\31\7h\2" +
            "\2\31\4\3\2\2\2\32\33\7*\2\2\33\6\3\2\2\2\34\35\7+\2\2\35\36\7\"\2\2\36" +
            "\37\7v\2\2\37 \7j\2\2 !\7g\2\2!\"\7p\2\2\"#\7\"\2\2#\b\3\2\2\2$%\7g\2" +
            "\2%&\7n\2\2&\'\7u\2\2\'(\7g\2\2(\n\3\2\2\2)-\t\2\2\2*,\t\3\2\2+*\3\2\2" +
            "\2,/\3\2\2\2-+\3\2\2\2-.\3\2\2\2.\f\3\2\2\2/-\3\2\2\2\60\62\t\3\2\2\61" +
            "\60\3\2\2\2\62\63\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\65\3\2\2\2\65" +
            "\66\t\4\2\2\66\67\t\3\2\2\678\t\3\2\28\16\3\2\2\29;\t\3\2\2:9\3\2\2\2" +
            ";<\3\2\2\2<:\3\2\2\2<=\3\2\2\2=D\3\2\2\2>@\t\4\2\2?A\t\3\2\2@?\3\2\2\2" +
            "AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2CE\3\2\2\2D>\3\2\2\2DE\3\2\2\2EJ\3\2\2\2" +
            "FG\7x\2\2GH\7c\2\2HJ\7n\2\2I:\3\2\2\2IF\3\2\2\2J\20\3\2\2\2KV\7>\2\2L" +
            "M\7>\2\2MV\7?\2\2NV\7@\2\2OP\7@\2\2PV\7?\2\2QR\7?\2\2RV\7?\2\2ST\7#\2" +
            "\2TV\7?\2\2UK\3\2\2\2UL\3\2\2\2UN\3\2\2\2UO\3\2\2\2UQ\3\2\2\2US\3\2\2" +
            "\2V\22\3\2\2\2WY\7\17\2\2XW\3\2\2\2XY\3\2\2\2YZ\3\2\2\2Z]\7\f\2\2[]\7" +
            "\17\2\2\\X\3\2\2\2\\[\3\2\2\2]\24\3\2\2\2^_\t\5\2\2_`\3\2\2\2`a\b\13\2" +
            "\2a\26\3\2\2\2\f\2-\63<BDIUX\\\3\b\2\2";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {
        "DEFAULT_TOKEN_CHANNEL", "HIDDEN"
    };
    public static String[] modeNames = {
        "DEFAULT_MODE"
    };

    static {
        RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
    }

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

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public laprLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
            "T__0", "T__1", "T__2", "T__3", "OUT", "LIM", "VAL", "OP", "NL", "WS"
        };
    }

    private static String[] makeLiteralNames() {
        return new String[]{
            null, "'if'", "'('", "') then '", "'else'"
        };
    }

    private static String[] makeSymbolicNames() {
        return new String[]{
            null, null, null, null, null, "OUT", "LIM", "VAL", "OP", "NL", "WS"
        };
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

    @Override
    public String getGrammarFileName() {
        return "lapr.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}
