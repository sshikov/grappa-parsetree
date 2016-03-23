package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class NounParser extends BaseParser<Void> {

	@GenerateNode(NounNode.class)
	public Rule noun(){
		return firstOf("John", "ball");
	}
}
