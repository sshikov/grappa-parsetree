package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public class SentenceNode extends ParseNode{
	public SentenceNode(final String value, final List<ParseNode> children)
	{
		super(value, children);
	}
}
