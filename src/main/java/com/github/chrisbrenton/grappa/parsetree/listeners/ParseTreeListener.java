package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.builders.ParseTreeBuilder;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.run.ParseRunnerListener;
import com.github.fge.grappa.run.ParsingResult;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.MatchFailureEvent;
import com.github.fge.grappa.run.events.MatchSuccessEvent;
import com.github.fge.grappa.run.events.PreMatchEvent;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Parse runner listener used to build a parse tree
 *
 * <p>This listener will create {@link ParseTreeBuilder} instances (for rules
 * annotated with {@link GenerateNode} only) and build the parse tree when the
 * user calls {@link #getRootNode()}.</p>
 *
 * <p>It is required that the root rule have such an annotation (it would be
 * impossible to build a parse tree otherwise); if this is not the case, an
 * {@link IllegalStateException} will be thrown (wrapped in a {@link
 * GrappaException}).</p>
 *
 * <p>An attempt to retrieve a parse tree of a failed match will throw
 * an {@link IllegalStateException} as well. To prevent this, any usage of this class should check
 * whether the match is a success (using {@link ParsingResult#isSuccess()})
 * before retrieving the parse tree.</p>
 */
public final class ParseTreeListener<V> extends ParseRunnerListener<V>{
    @VisibleForTesting
    static final String NO_ANNOTATION_ON_ROOT_RULE
        = "root rule has no @GenerateNode annotation";

    @VisibleForTesting
    static final String MATCH_FAILURE
        = "cannot retrieve a parse tree from a failing match";

    private final ParseNodeConstructorRepository repository;

    private final SortedMap<Integer, ParseTreeBuilder> builders = new TreeMap<>();

    /*
     * Check for success at the end of the parsing (that is, the matcher at
     * level 0 matches successfully).
     *
     * On failure, the parse tree is meaningless, so we use that to prevent the
     * user from returning a nonsensical parse tree (the build method of
     * ParseTreeBuilder would throw an NPE anyway).
     */
    private boolean success = false;

    /**
     * Constructor.
     *
     * @param repository the parse node constructors repository.
     */
    public ParseTreeListener(final ParseNodeConstructorRepository repository){
        this.repository = repository;
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void beforeMatch(final PreMatchEvent<V> event){
        final Context<V> context = event.getContext();
        final int level = context.getLevel();
        final Constructor<? extends ParseNode> constructor = findConstructor(context);

        if (constructor == null) {
            /*
             * If this is the root rule, a @GenerateNode annotation is required.
             *
             * If it was not found, this is an error: yell at the user.
             */
            if (level == 0) {
                throw new IllegalStateException(NO_ANNOTATION_ON_ROOT_RULE);
            }
            return;
        }

        final ParseTreeBuilder builder = new ParseTreeBuilder(constructor);

        builders.put(level, builder);
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public void matchSuccess(final MatchSuccessEvent<V> event){
        final Context<V> context = event.getContext();
        final int level = context.getLevel();
        final Constructor<? extends ParseNode> constructor = findConstructor(context);

        if (constructor == null)
            return;

        final String match = getMatch(context);

        final ParseTreeBuilder builder = builders.get(level);
        builder.setMatchedText(match);

        /*
         * If we are back to level 0, we are done. Declare success so that the
         * user can retrieve the parse tree.
         */
        if (level == 0) {
            success = true;
            return;
        }

        final int previousLevel = builders.headMap(level).lastKey();

        builders.get(previousLevel).addChild(builder);
    }


	/**
     * {@inheritDoc}
     */
    @Override
    public void matchFailure(final MatchFailureEvent<V> event) {
        /* If there is an error at all, remove this builder. */
        builders.remove(event.getContext().getLevel());
    }

    /**
     * Get the root {@code ParseNode} of the parse tree built by this {@code ParseTreeListener}
     * This recursively builds all children, thus building a parse tree.
     *
     * @return      The root node.
     * @exception IllegalStateException Attempt to retrieve the parse tree from
     * a failed match.
     */
    public ParseNode getRootNode(){
        if (!success)
            throw new IllegalStateException(MATCH_FAILURE);
        return builders.get(0).build();
    }

	/**
     * Get the matched {@code String} between the start and current index of the {@code Context}
     * provided.
     *
     * @param context       The context from which to retrieve a match.
     * @return              The match.
     */
    private static String getMatch(final Context<?> context){
        final int start = context.getStartIndex();
        final int end = context.getCurrentIndex();
        return context.getInputBuffer().extract(start, end);
    }

    private Constructor<? extends ParseNode> findConstructor(final Context<V> context) {
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

        return repository.getNodeConstructor(matcher.getLabel());
    }
}
