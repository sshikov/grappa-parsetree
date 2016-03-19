package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public final class ChildNode
    extends ParseNode
{
    public ChildNode(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }
}
