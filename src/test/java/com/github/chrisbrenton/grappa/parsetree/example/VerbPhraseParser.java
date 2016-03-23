package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class VerbPhraseParser extends BaseParser<Void> {

	protected VerbParser verbParser = Grappa.createParser(VerbParser.class);
	protected NounPhraseParser nounPhraseParser = Grappa.createParser(NounPhraseParser.class);

	@GenerateNode(VerbPhraseNode.class)
	public Rule verbPhrase(){
		return oneOrMore(verbParser.verb(), wsp(), nounPhraseParser
				.nounPhrase());
	}
}
