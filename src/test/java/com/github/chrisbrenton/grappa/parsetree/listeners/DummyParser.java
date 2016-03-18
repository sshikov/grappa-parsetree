package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.visit.ParentNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class DummyParser
		extends BaseParser<Void> {
	@GenerateNode(ParentNode.class)
	@Label("voidRule")
	public void voidRule() {
		//do nothing
	}

	@GenerateNode(ParentNode.class)
	@Label("ruleRoot")
	public Rule ruleRoot(){
		return oneOrMore(ruleOne(), ruleTwo(), ruleThree());
	}

	@GenerateNode(ParentNode.class)
	@Label("ruleOne")
	public Rule ruleOne(){
		return charRange('a','e');
	}

	@GenerateNode(ParentNode.class)
	@Label("ruleTwo")
	public Rule ruleTwo(){
		return charRange('f','j');
	}

	@GenerateNode(ParentNode.class)
	@Label("ruleThree")
	public Rule ruleThree(){
		return charRange('k', 'z');
	}
}