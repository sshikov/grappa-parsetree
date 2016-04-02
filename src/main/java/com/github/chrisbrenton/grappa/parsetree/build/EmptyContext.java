package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.fge.grappa.parsers.BaseParser;

import java.util.function.Supplier;

/**
 * A {@link ParseTreeContext} not attached to a node, and which is not either of
 * a {@linkplain BaseParser#sequence(Object, Object, Object...) sequence} or a
 * {@linkplain BaseParser#join(Object) join rule}
 *
 * <p><strong>TODO!</strong> Not sure whether all cases are covered here!</p>
 *
 * <p>Note that the only value of this enum is also a {@link Supplier} of itself
 * for convenience.</p>
 */
enum EmptyContext
    implements ParseTreeContext, Supplier<ParseTreeContext>
{
    INSTANCE,
    ;

    @Override
    public void addChild(final ParseTreeContext context)
    {
    }

    @Override
    public void setMatch(final MatchTextSupplier supplier)
    {
    }

    @Override
    public ParseTreeContext get()
    {
        return this;
    }
}
