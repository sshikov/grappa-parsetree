package com.github.fge.grappa.parsetree.visitors;

import com.github.fge.grappa.parsetree.nodes.ParseNode;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public interface Visitor {
    /**
     * Visit the {@code Visitable} provided.
     * @param node      The {@code Visitable} to visit.
     * @return          The value held by the {@code Visitable} visited
     */
     void visit(ParseNode node);
}
