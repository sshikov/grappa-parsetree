package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.buffers.InputBuffer;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.run.ParseEventListener;
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
 * <p>This listener will create {@link ParseNodeBuilder} instances (for rules
 * annotated with {@link GenerateNode} only) and build the parse tree when the
 * user calls {@link #getTree()}.</p>
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
public final class ParseTreeBuilder<V> extends ParseEventListener<V> {
    @VisibleForTesting
    static final String NO_ANNOTATION_ON_ROOT_RULE
        = "root rule has no @GenerateNode annotation";

    @VisibleForTesting
    static final String MATCH_FAILURE
        = "cannot retrieve a parse tree from a failing match";

    private final ParseNodeConstructorProvider repository;

    private final SortedMap<Integer, ParseNodeBuilder> builders = new TreeMap<>();

    /*
     * Check for success at the end of the parsing (that is, the matcher at
     * level 0 matches successfully).
     *
     * On failure, the parse tree is meaningless, so we use that to prevent the
     * user from returning a nonsensical parse tree (the build method of
     * ParseNodeBuilder would throw an NPE anyway).
     */
    private boolean success = false;

    /**
     * Constructor.
     *
     * @param repository the parse node constructors repository.
     */
    public ParseTreeBuilder(final ParseNodeConstructorProvider repository){
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

        final ParseNodeBuilder builder = new ParseNodeBuilder(constructor);

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

        final ParseNodeBuilder builder = builders.get(level);
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
        /*
         * If the match is a failure, remove the builder at this level, if any.
         *
         * Failure to do so would mean that successful children would get
         * attached to an invalid parent and lost at generation time...
         */
        builders.remove(event.getContext().getLevel());
    }

    /**
     * Get the root {@code ParseNode} of the parse tree built by this {@code ParseTreeBuilder}
     *
     * <p>This recursively builds all children, thus building a parse tree.</p>
     *
     * @return      The root node.
     * @exception IllegalStateException Attempt to retrieve the parse tree from
     * a failed match.
     */
    public ParseNode getTree(){
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

    private static final class MatchText
        implements MatchTextSupplier
    {
        private final InputBuffer buffer;
        private final int start;
        private final int end;

        private String matchText = null;

        public static MatchText from(final Context<?> context)
        {
            final InputBuffer buffer = context.getInputBuffer();
            final int start = context.getStartIndex();
            final int end = context.getCurrentIndex();
            return new MatchText(buffer, start, end);
        }

        private MatchText(final InputBuffer buffer, final int start,
            final int end)
        {
            this.buffer = buffer;
            this.start = start;
            this.end = end;
        }

        @Override
        public synchronized String get()
        {
            if (matchText == null)
                matchText = buffer.subSequence(start, end).toString();
            return matchText;
        }
    }
}
