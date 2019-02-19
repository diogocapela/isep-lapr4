// Generated from C:/Users/rafa_/Desktop/lapr4-2018-2019-grupona-3/core/src/main/java/utils/antlr\lapr.g4 by ANTLR 4.7.2
package utils.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link laprParser}.
 */
public interface laprListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link laprParser#expressao}.
     *
     * @param ctx the parse tree
     */
    void enterExpressao(laprParser.ExpressaoContext ctx);

    /**
     * Exit a parse tree produced by {@link laprParser#expressao}.
     *
     * @param ctx the parse tree
     */
    void exitExpressao(laprParser.ExpressaoContext ctx);

    /**
     * Enter a parse tree produced by {@link laprParser#comparador}.
     *
     * @param ctx the parse tree
     */
    void enterComparador(laprParser.ComparadorContext ctx);

    /**
     * Exit a parse tree produced by {@link laprParser#comparador}.
     *
     * @param ctx the parse tree
     */
    void exitComparador(laprParser.ComparadorContext ctx);
}
