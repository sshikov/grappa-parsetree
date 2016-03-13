package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.cbrenton.grappa.parsetree.visit.DuplicateLabelParser;
import com.github.cbrenton.grappa.parsetree.visit.IllegalNode;
import com.github.cbrenton.grappa.parsetree.visit.IllegalNodeParser;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;

public final class ParseNodeConstructorRepositoryTest {

    @Test
    public void failureOnIllegalConstructor() {
        try {
            new ParseNodeConstructorRepository(IllegalNodeParser.class);
            shouldHaveThrown(IllegalStateException.class);
        } catch (IllegalStateException e) {
            final String msg = String.format(ParseNodeConstructorRepository.NO_CONSTRUCTOR,
                IllegalNode.class.getSimpleName());
            assertThat(e).hasMessage(msg);
        }
    }

    @Test
    public void failureOnDuplicateRuleName() {
        try {
            new ParseNodeConstructorRepository(DuplicateLabelParser.class);
            shouldHaveThrown(IllegalStateException.class);
        } catch (IllegalStateException e) {
            final String msg = String.format(ParseNodeConstructorRepository.DUPLICATE_NAME,
                "theRule");
            assertThat(e).hasMessage(msg);
        }
    }
}
