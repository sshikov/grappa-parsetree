package com.github.chrisbrenton.grappa.parsetree.visitors;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.google.common.collect.TreeTraverser;

/**
 * A {@link TreeTraverser} for {@link ParseNode} instances
 *
 * @see VisitOrder
 */
public final class ParseTreeTraverser
    extends TreeTraverser<ParseNode>
{
    public static final TreeTraverser<ParseNode> INSTANCE
        = new ParseTreeTraverser();

    private ParseTreeTraverser()
    {
    }

    @Override
    public Iterable<ParseNode> children(final ParseNode root)
    {
        return root.getChildren();
    }
}
