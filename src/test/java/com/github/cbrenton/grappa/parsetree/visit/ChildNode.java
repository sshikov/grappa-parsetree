package com.github.cbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class ChildNode
    extends ParseNode
{
    public ChildNode(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }
}
