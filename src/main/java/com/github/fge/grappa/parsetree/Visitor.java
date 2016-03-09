package com.github.fge.grappa.parsetree;

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
     Visitable visit(Visitable node);
}
