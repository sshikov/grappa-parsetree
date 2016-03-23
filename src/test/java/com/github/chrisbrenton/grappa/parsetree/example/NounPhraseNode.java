package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

public class NounPhraseNode extends ParseNode {
	public NounPhraseNode(final String value, final List<ParseNode> children)
	{
		super(value, children);
	}
}
