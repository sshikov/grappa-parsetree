package com.github.fge.grappa.parsetree;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public interface Visitor<T extends Visitable> {
    /**
     * Visit the {@code ParseTree} provided.
     * @param node      The {@code ParseTree} to visit.
     * @return          The value held by the {@code ParseTree} visited
     */
     T visit(T node);
}
