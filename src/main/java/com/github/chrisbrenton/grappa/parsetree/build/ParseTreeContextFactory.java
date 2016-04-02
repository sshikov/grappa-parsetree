package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.rules.Action;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.PreMatchEvent;

import java.lang.reflect.Constructor;

/**
 * Factory to create {@link ParseTreeContext} instances
 *
 * @see ParseTreeBuilder#beforeMatch(PreMatchEvent)
 */
final class ParseTreeContextFactory
{
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

        return new ParseTreeContext(constructor);
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
    private Constructor<? extends ParseNode> findConstructor(
        final Context<?> context)
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
