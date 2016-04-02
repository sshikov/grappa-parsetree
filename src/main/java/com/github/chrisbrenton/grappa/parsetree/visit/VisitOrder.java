package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.google.common.collect.TreeTraverser;

import java.util.function.Function;

/**
 * Determine which order we want to use when traversing a parse tree
 *
 * @see TreeTraverser
 */
public enum VisitOrder
{
    /**
     * Preorder traversal
     *
     * @see TreeTraverser#preOrderTraversal(Object)
     */
    PREORDER(ParseTreeTraverser.INSTANCE::preOrderTraversal),

    /**
     * Postorder traversal
     *
     * @see TreeTraverser#postOrderTraversal(Object)
     */
    POSTORDER(ParseTreeTraverser.INSTANCE::postOrderTraversal),

    /**
     * Breadth first traversal
     *
     * @see TreeTraverser#breadthFirstTraversal(Object)
     */
    BREADTHFIRST(ParseTreeTraverser.INSTANCE::breadthFirstTraversal),
    ;

    private final Function<ParseNode, Iterable<ParseNode>> traverser;

    VisitOrder(final Function<ParseNode, Iterable<ParseNode>> traverser)
    {
        this.traverser = traverser;
    }

    public Iterable<ParseNode> visit(final ParseNode node)
    {
        return traverser.apply(node);
    }
}
