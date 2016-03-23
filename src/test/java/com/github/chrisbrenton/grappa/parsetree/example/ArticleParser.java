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
public class ArticleParser extends BaseParser<Void> {
	@GenerateNode(ArticleNode.class)
	@Label("article")
	public Rule article(){
		return string("the");
	}
}
