package com.github.chrisbrenton.grappa.parsetree.issue7;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class Root
    extends ParseNode
{
    public Root(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }
}
