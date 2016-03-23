package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class VerbParser extends BaseParser<Void> {

	@GenerateNode(VerbNode.class)
	public Rule verb(){
		return string("hit");
	}
}
