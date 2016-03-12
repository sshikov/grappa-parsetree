package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.chrisbrenton.grappa.parsetree.builders.ParseNodeBuilder;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.run.ParseRunnerListener;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.MatchSuccessEvent;
import com.github.fge.grappa.run.events.PreMatchEvent;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A {@link ParseTreeListener} listener is used as follows:
 *
 */
public final class ParseTreeListener<V> extends ParseRunnerListener<V>{
    @VisibleForTesting
    static final String NO_ANNOTATION_ON_ROOT_RULE
        = "root rule has no @GenerateNode annotation";

    @VisibleForTesting
    static final String MATCH_FAILURE
        = "cannot retrieve a parse tree from a failing match";

    private final ParseNodeConstructorRepository repository;

    private final SortedMap<Integer, ParseNodeBuilder> builders = new TreeMap<>();

    private boolean success = false;

    public ParseTreeListener(final ParseNodeConstructorRepository repository){
        this.repository = repository;
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void beforeMatch(final PreMatchEvent<V> event){
        final Context<V> context = event.getContext();

        if (context.inPredicate())
            return;

        final Matcher matcher = context.getMatcher();

        if (Objects.requireNonNull(matcher).getType() == MatcherType.ACTION)
            return;

        final int level = context.getLevel();
        final String ruleName = matcher.getLabel();
        final Constructor<? extends ParseNode> constructor
            = repository.getNodeConstructor(ruleName);

        if (constructor == null) {
            if (level == 0)
                throw new IllegalStateException(NO_ANNOTATION_ON_ROOT_RULE);
            return;
        }

        final ParseNodeBuilder builder = new ParseNodeBuilder(constructor);

        builders.put(level, builder);
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void matchSuccess(final MatchSuccessEvent<V> event){
        final Context<V> context = event.getContext();

        if (context.inPredicate())
            return;

        final Matcher matcher = context.getMatcher();


        if (Objects.requireNonNull(matcher).getType() == MatcherType.ACTION)
            return;

        final String ruleName = matcher.getLabel();
        final Constructor<? extends ParseNode> constructor
            = repository.getNodeConstructor(ruleName);

        if (constructor == null)
            return;

        final int level = context.getLevel();

        final String match = getMatch(context);

        final ParseNodeBuilder builder = builders.get(level);
        builder.setMatch(match);

        if (level == 0) {
            success = true;
            return;
        }

        final int previousLevel = builders.headMap(level).lastKey();

        builders.get(previousLevel).addChild(builder);
    }

    /**
     * Get the root {@code ParseNode} of the parse tree built by this {@code ParseTreeListener}
     * This recursively builds all children, thus building a parse tree.
     * @return      The root node.
     * @exception IllegalStateException Attempt to retrieve the parse tree from
     * a failed match
     */
    public ParseNode getRootNode(){
        if (!success)
            throw new IllegalStateException(MATCH_FAILURE);
        return builders.get(0).build();
    }

	/**
     * Get the matched {@code String} between the start and current index of the {@code Context} provided.
     * @param context       The context from which to retrieve a match.
     * @return              The match
     */
    private static String getMatch(final Context<?> context){
        final int start = context.getStartIndex();
        final int end = context.getCurrentIndex();
        return context.getInputBuffer().extract(start, end);
    }
}
