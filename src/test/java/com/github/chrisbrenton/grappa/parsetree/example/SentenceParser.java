package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class SentenceParser extends BaseParser<Void> {

	protected NounParser nounParser = Grappa.createParser(NounParser.class);
	protected VerbPhraseParser verbPhraseParser = Grappa.createParser(VerbPhraseParser.class);

	@GenerateNode(SentenceNode.class)
	public Rule sentence(){
		return oneOrMore(nounParser.noun(), wsp(), verbPhraseParser
				.verbPhrase());
	}

}
