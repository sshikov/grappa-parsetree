package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.google.common.collect.TreeTraverser;

/**
 * A {@link TreeTraverser} for {@link ParseNode} instances
 *
 * @see VisitOrder
 */
final class ParseTreeTraverser
    extends TreeTraverser<ParseNode>
{
    public static final TreeTraverser<ParseNode> INSTANCE
        = new ParseTreeTraverser();

    private ParseTreeTraverser(){}

    @Override
    public Iterable<ParseNode> children(final ParseNode root)
    {
        return root.getChildren();
    }
}
