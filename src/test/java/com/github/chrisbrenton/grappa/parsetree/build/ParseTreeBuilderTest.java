package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.visit.DummyParser;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.run.ListeningParseRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

/**
 * @author Chris <chrummyenton90@gmail.com>
 * @date 12/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public class ParseTreeBuilderTest {

    private DummyParser parser;
    private ParseTreeBuilder<Object> listener;

    @BeforeMethod
    public void init() {
      final ParseNodeConstructorProvider repository
            = new ParseNodeConstructorProvider(DummyParser.class);

        parser = Grappa.createParser(DummyParser.class);
        listener = new ParseTreeBuilder<>(repository);
    }

    /*
     * If root is not annotated, then no node can be created, and as such a tree cannot be
     * created beneath it.
     */
    @Test
    public void failIfRootRuleIsNotAnnotated() {
        final ListeningParseRunner<Object> runner
            = new ListeningParseRunner<>(parser.noAnnotation());

        runner.registerListener(listener);

        try {
            runner.run("");
            shouldHaveThrown(GrappaException.class);
        } catch (GrappaException e) {
            final Throwable cause = e.getCause();

            assertThat(cause)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(ParseTreeBuilder.NO_ANNOTATION_ON_ROOT_RULE);
        }
    }

	/**
     * If parsing fails, then ensure that no parse tree is returned.
     */
    @Test
    public void failToRetrieveParseTreeOnParseFailure() {
        final ListeningParseRunner<Object> runner
            = new ListeningParseRunner<>(parser.failingRule());

        runner.registerListener(listener);

        runner.run("");

        try {
            listener.getTree();
            shouldHaveThrown(IllegalStateException.class);
        } catch (IllegalStateException e) {
            assertThat(e).hasMessage(ParseTreeBuilder.MATCH_FAILURE);
        }
    }

}
