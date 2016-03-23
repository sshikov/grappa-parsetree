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
public class VerbPhraseParser extends BaseParser<Void> {

	protected VerbParser verbParser = new VerbParser();
	protected NounPhraseParser nounPhraseParser = new NounPhraseParser();

	@GenerateNode(VerbPhraseNode.class)
	@Label("verbPhrase")
	public Rule verbPhrase(){
		return oneOrMore(verbParser.verb(), wsp(), nounPhraseParser
				.nounPhrase());
	}
}
