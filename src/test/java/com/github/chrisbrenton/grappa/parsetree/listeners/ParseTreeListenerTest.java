package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.cbrenton.grappa.parsetree.visit.DummyParser;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.run.ListeningParseRunner;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

/**
 * @author Chris <chrisbrenton90@gmail.com>
 * @date 12/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public class ParseTreeListenerTest {

    private DummyParser parser;
    private ParseTreeListener<Object> listener;

    @BeforeMethod
    public void init() {
        final ParseNodeConstructorRepository repository
            = new ParseNodeConstructorRepository(DummyParser.class);

        parser = Grappa.createParser(DummyParser.class);
        listener = new ParseTreeListener<>(repository);
    }

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
                .hasMessage(ParseTreeListener.NO_ANNOTATION_ON_ROOT_RULE);
        }
    }

    @Test
    public void failToRetrieveParseTreeOnParseFailure() {
        final ListeningParseRunner<Object> runner
            = new ListeningParseRunner<>(parser.failingRule());

        runner.registerListener(listener);

        runner.run("");

        try {
            listener.getRootNode();
            shouldHaveThrown(IllegalStateException.class);
        } catch (IllegalStateException e) {
            assertThat(e).hasMessage(ParseTreeListener.MATCH_FAILURE);
        }
    }
}
