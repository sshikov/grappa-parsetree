package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;

import java.util.function.Supplier;

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
