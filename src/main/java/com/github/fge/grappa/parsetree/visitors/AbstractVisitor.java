package com.github.fge.grappa.parsetree.visitors;

import com.github.fge.grappa.parsetree.nodes.ParseNode;

/**
 *
 */
public abstract class AbstractVisitor implements Visitor {

	/**
	 * Visit the {@code ParseNode} provided.
	 *
	 * <p>To provide bespoke functionality for visiting a subclass of {@code ParseNode}, then overload this method.</p>
	 *
	 * @param node      The {@code ParseNode} to visit.
	 */
	@Override
	public abstract void visit(ParseNode node);

}
