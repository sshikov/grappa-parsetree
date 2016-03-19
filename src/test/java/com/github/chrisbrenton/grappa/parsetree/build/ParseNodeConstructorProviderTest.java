package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.visit.DuplicateLabelParser;
import com.github.chrisbrenton.grappa.parsetree.visit.IllegalNode;
import com.github.chrisbrenton.grappa.parsetree.visit.IllegalNodeParser;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

public final class ParseNodeConstructorProviderTest {

	/*
	 * Ensures that a legal constructor is available for the {@link ParseNode} instance.
	 */
	@Test
	public void failureOnIllegalConstructor() {
		try {
			new ParseNodeConstructorProvider(IllegalNodeParser.class);
			shouldHaveThrown(IllegalStateException.class);
		}
		catch (IllegalStateException e) {
			final String msg = String.format(ParseNodeConstructorProvider.NO_CONSTRUCTOR,
			                                 IllegalNode.class.getSimpleName());
			assertThat(e).hasMessage(msg);
		}
	}

	/*
	 * Ensures that no rules can be labelled identically.
	 */
	@Test
	public void failureOnDuplicateRuleName() {
		try {
			new ParseNodeConstructorProvider(DuplicateLabelParser.class);
			shouldHaveThrown(IllegalStateException.class);
		}
		catch (IllegalStateException e) {
			final String msg = String.format(ParseNodeConstructorProvider.DUPLICATE_NAME,
			                                 "theRule");
			assertThat(e).hasMessage(msg);
		}
	}

	/*
	 * Ensures that no constructor is found by the ParseNodeConstructorProviderTest for
	 * an annotated rule that is reached that does not return a Rule.
	 */
	@Test
	public void ensureNoConstructorOnVoidRule() {
		ParseNodeConstructorProvider repo = new ParseNodeConstructorProvider(DelegatingParser
					                                                                      .class);
		assertThat(repo.getNodeConstructor("voidRule")).isNull();
	}
}
