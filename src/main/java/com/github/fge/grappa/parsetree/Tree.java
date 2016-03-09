package com.github.fge.grappa.parsetree;

import java.util.List;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public interface Tree<E> {
    /**
     * Add a child to the {@code Tree} with this value
     * @param value     The value of the node to add
     * @return          Whether the value was successfully added
     */
    boolean addChild(E value);

	/**
	 * Get the parent {@code Tree} of this {@code Tree}.
     * @return          The {@code Tree} parent of this {@code Tree}. Returns <b>null</b> if this is a root node.
     */
    Tree<E> getParent();

    /**
     * Remove the first {@code Tree} child with value provided. If the {@code Tree} has children, these children are
     * also removed and returned. If no {@code Tree} is found, then <b>null</b> is returned.
     *
     * @param child     The value of the {@code Tree} to remove.
     * @return          A {@code Tree} representing the node removed and all of it's children.
     */
    Tree<E> removeChild(Tree<E> child);

    /**
     * Returns whether or not this {@code Tree} has children.
     * @return          <b>true</b> if this {@code Tree} has children, <b>false</b> otherwise
     */
    boolean hasChildren();

    /**
     * Get the children of this {@code Tree}
     * @return          The children of this {@code Tree}
     */
    List<? extends Tree<E>> getChildren();
}
