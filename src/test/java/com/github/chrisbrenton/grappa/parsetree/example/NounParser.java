package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

/**
 * @author Chris <chrisbrenton90@gmail.com>
 * @date 23/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public class NounParser extends BaseParser<Void> {
	@GenerateNode(NounNode.class)
	@Label("noun")
	public Rule noun(){
		return firstOf("John", "ball");
	}
}
