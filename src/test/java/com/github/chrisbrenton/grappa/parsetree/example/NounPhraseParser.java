package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class NounPhraseParser extends BaseParser<Void> {

	protected NounParser nounParser = Grappa.createParser(NounParser.class);
	protected ArticleParser articleParser = Grappa.createParser(ArticleParser.class);

	@GenerateNode(NounPhraseNode.class)
	public Rule nounPhrase(){
		return oneOrMore(articleParser.article(), wsp(), nounParser.noun());
	}

}
