package com.github.fge.grappa.parsetree.nodes;

import com.github.fge.grappa.parsetree.visitors.Visitor;

/**
 * Created by Chris on 10/03/2016.
 */
public class Rule1Node extends ParseNode {
	public Rule1Node(final String value){
		super(value);
	}
	////////////////// VISITABLE INTERFACE /////////////////////////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
