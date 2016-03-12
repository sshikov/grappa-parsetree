package com.github.chrisbrenton.grappa.parsetree.visitors;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

/**
 * @author Chris <chrisbrenton90@gmail.com>
 * @date 10/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public abstract class AbstractVisitor {
	/**
	 * Visit the {@code ParseNode} provided.
	 *
	 * <p>To provide bespoke functionality for visiting a subclass of {@code ParseNode}, then overload this method.</p>
	 *
	 * @param node      The {@code ParseNode} to visit.
	 */
	public abstract void visit(ParseNode node);

}
