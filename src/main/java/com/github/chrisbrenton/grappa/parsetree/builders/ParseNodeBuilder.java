package com.github.chrisbrenton.grappa.parsetree.builders;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.lang.reflect.Constructor;

/**
 * This class is deprecated as of 1.0.2. Use {@link ParseTreeBuilder} instead.
 */
@Deprecated
public class ParseNodeBuilder extends ParseTreeBuilder {
	/**
	 * Constructor
	 *
	 * @param constructor The constructor of the node that this builder will represent.
	 */
	public ParseNodeBuilder(Constructor<? extends ParseNode> constructor) {
		super(constructor);
	}
}
