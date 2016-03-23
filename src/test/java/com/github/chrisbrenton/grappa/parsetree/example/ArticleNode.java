package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.List;

/**
 * @author Chris <chrisbrenton90@gmail.com>
 * @date 23/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public class ArticleNode extends ParseNode{
	public ArticleNode(final String value, final List<ParseNode> children)
	{
		super(value, children);
	}
}
