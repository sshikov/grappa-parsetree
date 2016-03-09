package com.github.fge.grappa.parsetree;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public interface Visitable {
    /**
     * Accept an {@code Visitor} on this {@code Tree}. Should allow the visitor to visit the node, any
     * behaviour for the visit should be implemented in any concrete implementation of {@code Visitor}.
     *
     * @param visitor   The visiting visitor.
     */
    void accept(Visitor visitor);
}
