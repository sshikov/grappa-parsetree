package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.visit.ParentNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class DelegatingParser
		extends BaseParser<Void> {

	protected DelegateParser parser = Grappa.createParser(DelegateParser.class);

	@GenerateNode(ParentNode.class)
	@Label("voidRule")
	public void voidRule() {
		//do nothing
	}

	@GenerateNode(ParentNode.class)
	@Label("ruleRoot")
	public Rule ruleRoot(){
		return oneOrMore(ruleOne(), parser.ruleTwo(), ruleThree());
	}

	@GenerateNode(ParentNode.class)
	@Label("ruleOne")
	public Rule ruleOne(){
		return charRange('a','e');
	}


	@GenerateNode(ParentNode.class)
	@Label("ruleThree")
	public Rule ruleThree(){
		return charRange('k', 'z');
	}
}