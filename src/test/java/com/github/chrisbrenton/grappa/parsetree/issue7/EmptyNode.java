package com.github.chrisbrenton.grappa.parsetree.issue7;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class EmptyNode
    extends ParseNode
{
    public EmptyNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }
}
