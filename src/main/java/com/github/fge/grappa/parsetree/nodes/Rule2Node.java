package com.github.fge.grappa.parsetree.nodes;

import com.github.fge.grappa.parsetree.visitors.Visitor;

/**
 * Created by Chris on 10/03/2016.
 */
public class Rule2Node extends ParseNode {
	public Rule2Node(final String value){
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
