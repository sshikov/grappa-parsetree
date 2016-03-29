package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.fge.grappa.Grappa;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseTreeDelegationTest {

	private DelegatingParser parser;
	private ParseTreeBuilder<Object> listener;
	private ParseNodeConstructorProvider repository;

	@BeforeMethod
	public void init() {
		repository = new ParseNodeConstructorProvider(DelegatingParser.class);
		parser = Grappa.createParser(DelegatingParser.class);
		listener = new ParseTreeBuilder<>(repository);
	}

	@Test
	public void canFindAnnotationsInDelegatedParsers(){
		assertThat(repository.getNodeConstructor("ruleTwo")).isNotNull();
	}

}
