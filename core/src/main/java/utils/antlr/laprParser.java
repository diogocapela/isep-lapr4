// Generated from C:/Users/rafa_/Desktop/lapr4-2018-2019-grupona-3/core/src/main/java/utils/antlr\lapr.g4 by ANTLR 4.7.2
package utils.antlr;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class laprParser extends Parser {
    public static final int
        T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, OUT = 5, LIM = 6, VAL = 7, OP = 8, NL = 9, WS = 10;
    public static final int
        RULE_expressao = 0, RULE_comparador = 1;
    public static final String[] ruleNames = makeRuleNames();
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN =
        "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\f#\4\2\t\2\4\3\t" +
            "\3\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\21\n\3\3\3\3\3\3\3\3\3" +
            "\3\3\3\3\3\3\3\3\3\3\3\3\5\3\35\n\3\3\3\3\3\5\3!\n\3\3\3\2\2\4\2\4\2\2" +
            "\2#\2\6\3\2\2\2\4 \3\2\2\2\6\7\7\3\2\2\7\b\5\4\3\2\b\3\3\2\2\2\t\n\7\4" +
            "\2\2\n\13\7\t\2\2\13\f\7\n\2\2\f\r\7\b\2\2\r\16\7\5\2\2\16\20\7\7\2\2" +
            "\17\21\7\13\2\2\20\17\3\2\2\2\20\21\3\2\2\2\21\22\3\2\2\2\22!\b\3\1\2" +
            "\23\24\7\4\2\2\24\25\7\t\2\2\25\26\7\n\2\2\26\27\7\b\2\2\27\30\7\5\2\2" +
            "\30\31\7\7\2\2\31\32\7\6\2\2\32\34\5\2\2\2\33\35\7\13\2\2\34\33\3\2\2" +
            "\2\34\35\3\2\2\2\35\36\3\2\2\2\36\37\b\3\1\2\37!\3\2\2\2 \t\3\2\2\2 \23" +
            "\3\2\2\2!\5\3\2\2\2\5\20\34 ";
    public static final ATN _ATN =
        new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache =
        new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = makeLiteralNames();
    private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

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

    public int valContador;
    public List<Integer> escalaValores;
    public Integer minEscala;
    public Integer maxEscala;

    public laprParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    private static String[] makeRuleNames() {
        return new String[]{
            "expressao", "comparador"
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
    public ATN getATN() {
        return _ATN;
    }

    private boolean dynamicInbounds(Double val, String op, Double lim) {
        if (op.equals("<")) {
            return val < lim;
        } else if (op.equals("<=")) {
            return val <= lim;
        } else if (op.equals(">")) {
            return val > lim;
        } else if (op.equals(">=")) {
            return val >= lim;
        } else if (op.equals("==")) {
            return val == lim;
        } else if (op.equals("!=")) {
            return val != lim;
        }
        return false;
    }

    private void processComparator(Token valT, Token opT, Token limT, Token outT) {
        Integer out = Integer.parseInt(outT.getText());
        if (valT.getText().equalsIgnoreCase("val")) {
            // caso de atribuicao
            this.valContador++;
            if (out < minEscala) {
                minEscala = out;
            }
            if (out > maxEscala) {
                maxEscala = out;
            }
        } else {
            // caso de teste da escala
            if (this.valContador == 0) {
                Double val = Double.parseDouble(valT.getText());
                String op = opT.getText();
                Double lim = Double.parseDouble(limT.getText());

                if (dynamicInbounds(val, op, lim)) {
                    // caso de sucesso no teste
                    this.escalaValores.add(out);
                }
            }
        }
    }

    public final ExpressaoContext expressao() throws RecognitionException {
        ExpressaoContext _localctx = new ExpressaoContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_expressao);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(4);
                match(T__0);
                setState(5);
                comparador();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ComparadorContext comparador() throws RecognitionException {
        ComparadorContext _localctx = new ComparadorContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_comparador);
        try {
            setState(30);
            _errHandler.sync(this);
            switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
                case 1:
                    enterOuterAlt(_localctx, 1);
                {
                    setState(7);
                    match(T__1);
                    setState(8);
                    ((ComparadorContext) _localctx).VAL = match(VAL);
                    setState(9);
                    ((ComparadorContext) _localctx).OP = match(OP);
                    setState(10);
                    ((ComparadorContext) _localctx).LIM = match(LIM);
                    setState(11);
                    match(T__2);
                    setState(12);
                    ((ComparadorContext) _localctx).OUT = match(OUT);
                    setState(14);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
                        case 1: {
                            setState(13);
                            match(NL);
                        }
                        break;
                    }
                    System.out.println("over");
                    processComparator(((ComparadorContext) _localctx).VAL, ((ComparadorContext) _localctx).OP, ((ComparadorContext) _localctx).LIM, ((ComparadorContext) _localctx).OUT);
                }
                break;
                case 2:
                    enterOuterAlt(_localctx, 2);
                {
                    setState(17);
                    match(T__1);
                    setState(18);
                    ((ComparadorContext) _localctx).VAL = match(VAL);
                    setState(19);
                    ((ComparadorContext) _localctx).OP = match(OP);
                    setState(20);
                    ((ComparadorContext) _localctx).LIM = match(LIM);
                    setState(21);
                    match(T__2);
                    setState(22);
                    ((ComparadorContext) _localctx).OUT = match(OUT);
                    setState(23);
                    match(T__3);
                    setState(24);
                    expressao();
                    setState(26);
                    _errHandler.sync(this);
                    switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
                        case 1: {
                            setState(25);
                            match(NL);
                        }
                        break;
                    }
                    System.out.println(" not over");
                    processComparator(((ComparadorContext) _localctx).VAL, ((ComparadorContext) _localctx).OP, ((ComparadorContext) _localctx).LIM, ((ComparadorContext) _localctx).OUT);
                }
                break;
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class ExpressaoContext extends ParserRuleContext {
        public ExpressaoContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public ComparadorContext comparador() {
            return getRuleContext(ComparadorContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_expressao;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof laprListener) ((laprListener) listener).enterExpressao(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof laprListener) ((laprListener) listener).exitExpressao(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof laprVisitor) return ((laprVisitor<? extends T>) visitor).visitExpressao(this);
            else return visitor.visitChildren(this);
        }
    }

    public static class ComparadorContext extends ParserRuleContext {
        public Token VAL;
        public Token OP;
        public Token LIM;
        public Token OUT;

        public ComparadorContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode VAL() {
            return getToken(laprParser.VAL, 0);
        }

        public TerminalNode OP() {
            return getToken(laprParser.OP, 0);
        }

        public TerminalNode LIM() {
            return getToken(laprParser.LIM, 0);
        }

        public TerminalNode OUT() {
            return getToken(laprParser.OUT, 0);
        }

        public TerminalNode NL() {
            return getToken(laprParser.NL, 0);
        }

        public ExpressaoContext expressao() {
            return getRuleContext(ExpressaoContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_comparador;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof laprListener) ((laprListener) listener).enterComparador(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof laprListener) ((laprListener) listener).exitComparador(this);
        }

        @Override
        public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
            if (visitor instanceof laprVisitor) return ((laprVisitor<? extends T>) visitor).visitComparador(this);
            else return visitor.visitChildren(this);
        }
    }
}
