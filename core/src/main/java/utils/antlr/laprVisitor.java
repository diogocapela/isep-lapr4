// Generated from C:/Users/rafa_/Desktop/lapr4-2018-2019-grupona-3/core/src/main/java/utils/antlr\lapr.g4 by ANTLR 4.7.2
package utils.antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link laprParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface laprVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link laprParser#expressao}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpressao(laprParser.ExpressaoContext ctx);

    /**
     * Visit a parse tree produced by {@link laprParser#comparador}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitComparador(laprParser.ComparadorContext ctx);
}
