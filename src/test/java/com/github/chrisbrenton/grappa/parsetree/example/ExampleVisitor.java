package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.visit.Visitor;
import com.google.common.eventbus.Subscribe;

/**
 *
 */
public class ExampleVisitor implements Visitor {

	private String person = "";
	private String verb = "";
	private String article = "";
	private String object = "";

	@Subscribe
	public void visitNounNode(final NounNode node){
		if (person.isEmpty())
			person = node.getValue();
		else
			object = node.getValue();
	}

	@Subscribe
	public void visitVerbNode(final VerbNode node){
		verb = node.getValue();
	}

	@Subscribe
	public void visitArticleNode(final ArticleNode node){
		article = node.getValue();
	}

	@Subscribe
	public void visitNounPhraseNode(final NounPhraseNode node){
		//we aren't interested in branch nodes!
	}

	@Subscribe
	public void visitVerbPhraseNode(final VerbPhraseNode node){
		//we aren't interested in branch nodes!
	}

	@Subscribe
	public void visitSentenceNode(final SentenceNode node){
		//we aren't interested in the root node!
	}

	public String getSillySentence(){
		return article + " " +  object + " " +  verb + " " + person;
	}

}
