// Generated from C:/Users/rafa_/Desktop/lapr4-2018-2019-grupona-3/core/src/main/java/utils/antlr\lapr.g4 by ANTLR 4.7.2
package utils.antlr;

import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * This class provides an empty implementation of {@link laprVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public class laprBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements laprVisitor<T> {
    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitExpressao(laprParser.ExpressaoContext ctx) {
        return visitChildren(ctx);
    }

    /**
     * {@inheritDoc}
     *
     * <p>The default implementation returns the result of calling
     * {@link #visitChildren} on {@code ctx}.</p>
     */
    @Override
    public T visitComparador(laprParser.ComparadorContext ctx) {
        return visitChildren(ctx);
    }
}
