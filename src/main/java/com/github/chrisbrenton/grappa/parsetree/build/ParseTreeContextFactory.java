package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.internal.NonFinalForTesting;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.matchers.delegate.SequenceMatcher;
import com.github.fge.grappa.run.context.Context;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;

@NonFinalForTesting
class ParseTreeContextFactory
{
    private final ParseNodeConstructorProvider provider;

    ParseTreeContextFactory(final ParseNodeConstructorProvider provider)
    {
        this.provider = provider;
    }

    ParseTreeContext createContext(final Context<?> context)
    {
        final Constructor<? extends ParseNode> constructor
            = findConstructor(context);

        if (constructor != null)
            return new ParseNodeContext(constructor);

        final Matcher matcher = context.getMatcher();

        return matcher instanceof SequenceMatcher
            ? new SequenceContext()
            : EmptyContext.INSTANCE;
    }

    @VisibleForTesting
    Constructor<? extends ParseNode> findConstructor(final Context<?> context)
    {
        /*
         * Never attempt to retrieve a constructor if we are in a predicate!
         */
        if (context.inPredicate())
            return null;

        final Matcher matcher = context.getMatcher();

        // FIXME: in theory this should never happen. In theory.
        if (matcher == null)
            return null;

        /*
         * Actions are no good either.
         *
         * TODO: this should not happen either; actions are not rules
         */
        if (matcher.getType() == MatcherType.ACTION)
            return null;

        return provider.getNodeConstructor(matcher.getLabel());
    }
}
