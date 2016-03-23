package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class SentenceParser extends BaseParser<Void> {

	protected NounParser nounParser = new NounParser();
	protected VerbPhraseParser verbPhraseParser = new VerbPhraseParser();

	@GenerateNode(SentenceNode.class)
	@Label("sentence")
	public Rule sentence(){
		return oneOrMore(nounParser.noun(), wsp(), verbPhraseParser
				.verbPhrase());
	}

}
