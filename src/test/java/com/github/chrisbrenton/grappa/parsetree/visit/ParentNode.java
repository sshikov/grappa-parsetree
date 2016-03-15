package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.List;

public final class ParentNode
    extends ParseNode
{
    public ParentNode(final String value, final List<ParseNode> children)
    {
        super(value, children);
    }
}
