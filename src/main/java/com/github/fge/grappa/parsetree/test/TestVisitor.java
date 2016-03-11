package com.github.fge.grappa.parsetree.test;

import com.github.fge.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.parsetree.visitors.Visitor;

/**
 * Created by Chris on 10/03/2016.
 */
public class TestVisitor implements Visitor {


	@Override
	public void visit(ParseNode node) {
		System.out.println(node.getValue());
	}
}
