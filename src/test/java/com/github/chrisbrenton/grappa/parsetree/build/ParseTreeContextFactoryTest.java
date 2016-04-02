package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.issue7.Issue7Parser;
import com.github.fge.grappa.run.context.Context;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.shouldHaveThrown;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public final class ParseTreeContextFactoryTest
{
    @Test
    public void noRootNodeIsAnError()
    {
        final ParseNodeConstructorProvider provider
            = new ParseNodeConstructorProvider(Issue7Parser.class);
        final ParseTreeContextFactory factory
            = spy(new ParseTreeContextFactory(provider));
        doReturn(null).when(factory).findConstructor(any(Context.class));

        final Context<?> context = mock(Context.class);

        try {
            factory.createRootContext(context);
            shouldHaveThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(ParseTreeContextFactory.NO_ANNOTATION_ON_ROOT_RULE);
        }
    }
}
