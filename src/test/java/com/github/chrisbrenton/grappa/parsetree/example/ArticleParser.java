package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class ArticleParser extends BaseParser<Void> {

	@GenerateNode(ArticleNode.class)
	@Label("Definitive Article")
	public Rule article(){
		return string("the");
	}
}
