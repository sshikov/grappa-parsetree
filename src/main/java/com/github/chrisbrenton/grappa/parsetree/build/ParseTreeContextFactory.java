package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.internal.NonFinalForTesting;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.matchers.delegate.SequenceMatcher;
import com.github.fge.grappa.matchers.join.JoinMatcher;
import com.github.fge.grappa.rules.Action;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.PreMatchEvent;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Factory to create {@link ParseNodeContext} instances
 *
 * @see ParseTreeBuilder#beforeMatch(PreMatchEvent)
 */
@NonFinalForTesting
class ParseTreeContextFactory
{
    /**
     * Map of context suppliers in the event where no node is associated with
     * that context (ie, the {@link Matcher} is not annotated with {@link
     * GenerateNode}
     */
    private static final Map<Class<?>, Supplier<ParseTreeContext>> NO_NODES;

    static {
        NO_NODES = new HashMap<>();

        NO_NODES.put(SequenceMatcher.class, SequenceContext::new);
        NO_NODES.put(JoinMatcher.class, JoinContext::new);
    }

    private final ParseNodeConstructorProvider provider;

    /**
     * Constructor
     *
     * @param provider the parse node constructor provider; guaranteed never to
     *                 be null
     */
    ParseTreeContextFactory(final ParseNodeConstructorProvider provider)
    {
        this.provider = provider;
    }

    /**
     * Create a parse tree context given a parsing {@link Context}
     *
     * @param context the context, guaranteed never to be null
     * @return an appropriate parse tree context instance
     */
    ParseTreeContext createContext(final Context<?> context)
    {
        final Constructor<? extends ParseNode> constructor
            = findConstructor(context);

        final Matcher matcher = context.getMatcher();

        // FIXME...
        if (matcher == null)
            return EmptyContext.INSTANCE;

        if (constructor != null)
            return new ParseNodeContext(constructor,
                matcher instanceof JoinMatcher);

        final Supplier<ParseTreeContext> supplier = Optional
            .ofNullable(NO_NODES.get(matcher.getClass()))
            .orElse(EmptyContext.INSTANCE);

        return supplier.get();
    }

    /**
     * Get a constructor for a given parsing {@link Context}, if applicable
     *
     * <p>The method returns {@code null} if no constructor is found.</p>
     *
     * <p>Note that this method will always return null in the following
     * situations:</p>
     *
     * <ul>
     *     <li>we are currently {@linkplain Context#inPredicate() in a
     *     predicate} (ie, {@code test()} or {@code testNot()});</li>
     *     <li>the matcher is in fact an {@link Action} (its {@linkplain
     *     Matcher#getType() type} is {@link MatcherType#ACTION};</li>
     *     <li>no matcher exists for that context (FIXME...).</li>
     * </ul>
     *
     * <p>If none of the above situations apply, then the method grabs the
     * {@linkplain Matcher#getLabel() label} of the matcher for this context and
     * returns the result of {@link
     * ParseNodeConstructorProvider#getNodeConstructor(String)} (which may also
     * be {@code null}.</p>
     *
     * @param context the parsing context
     * @return the parse node constructor, if any; see description
     */
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
