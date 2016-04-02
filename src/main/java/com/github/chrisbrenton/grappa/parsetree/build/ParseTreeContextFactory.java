package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.internal.NonFinalForTesting;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.matchers.delegate.SequenceMatcher;
import com.github.fge.grappa.matchers.join.JoinMatcher;
import com.github.fge.grappa.run.context.Context;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@NonFinalForTesting
class ParseTreeContextFactory
{
    private static final Map<Class<?>, Supplier<ParseTreeContext>> NO_NODES;

    static {
        NO_NODES = new HashMap<>();

        NO_NODES.put(SequenceMatcher.class, SequenceContext::new);
        NO_NODES.put(JoinMatcher.class, JoinContext::new);
    }

    private final ParseNodeConstructorProvider provider;

    ParseTreeContextFactory(final ParseNodeConstructorProvider provider)
    {
        this.provider = provider;
    }

    ParseTreeContext createContext(final Context<?> context)
    {
        final Constructor<? extends ParseNode> constructor
            = findConstructor(context);

        final Matcher matcher = context.getMatcher();

        if (constructor != null)
            return new ParseNodeContext(constructor,
                matcher instanceof JoinMatcher);

        if (matcher == null)
            return EmptyContext.INSTANCE;

        final Supplier<ParseTreeContext> supplier = Optional
            .ofNullable(NO_NODES.get(matcher.getClass()))
            .orElse(EmptyContext.INSTANCE);

        return supplier.get();
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
