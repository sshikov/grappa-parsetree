package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

enum EmptyContext
    implements ParseTreeContext
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
}
