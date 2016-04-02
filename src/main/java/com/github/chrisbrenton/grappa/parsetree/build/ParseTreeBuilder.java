package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.buffers.InputBuffer;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.run.ParseEventListener;
import com.github.fge.grappa.run.ParsingResult;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.MatchFailureEvent;
import com.github.fge.grappa.run.events.MatchSuccessEvent;
import com.github.fge.grappa.run.events.PreMatchEvent;
import com.github.fge.grappa.support.Position;
import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Parse event listener used to build a parse tree
 *
 * <p>This listener is in charge of listening for all events and build the parse
 * tree when the user calls {@link #getTree()}. Only rules annotated with {@link
 * GenerateNode} will trigger the build of parse nodes.</p>
 *
 * <p>It is required that the root rule have such an annotation (it would be
 * impossible to build a parse tree otherwise); if this is not the case, an
 * {@link IllegalStateException} will be thrown (wrapped in a {@link
 * GrappaException}).</p>
 *
 * <p>An attempt to retrieve a parse tree of a failed match will throw
 * an {@link IllegalStateException} as well. To prevent this, any usage of this
 * class should check whether the match is a success (using {@link
 * ParsingResult#isSuccess()}) before retrieving the parse tree.</p>
 */
public final class ParseTreeBuilder<V> extends ParseEventListener<V>
{
    @VisibleForTesting
    static final String NO_ANNOTATION_ON_ROOT_RULE
        = "root rule has no @GenerateNode annotation";

    @VisibleForTesting
    static final String MATCH_FAILURE
        = "cannot retrieve a parse tree from a failing match";

    /*
     * Our stack of parse tree contexts
     */
    private final Deque<ParseTreeContext> parseTreeContexts
        = new ArrayDeque<>();

    /*
     * Placeholder for the root node (if the match succeeds, of course)
     */
    private ParseNode rootNode = null;

    /*
     * Factory to create the parse tree contexts
     */
    private final ParseTreeContextFactory factory;

    /**
     * Constructor
     *
     * @param provider the parse node constructors provider
     */
    public ParseTreeBuilder(final ParseNodeConstructorProvider provider)
    {
        factory = new ParseTreeContextFactory(provider);
    }

    @Override
    public void beforeMatch(final PreMatchEvent<V> event){
        final Context<V> context = event.getContext();
        final ParseTreeContext ctx = factory.createContext(context);
        /*
         * If we are at the root, the context MUST produce a node. If this is
         * not the case, this is an error.
         */
        if (parseTreeContexts.isEmpty() && !ctx.hasNode())
            throw new IllegalStateException(NO_ANNOTATION_ON_ROOT_RULE);
        parseTreeContexts.push(ctx);
    }

    @Override
    public void matchSuccess(final MatchSuccessEvent<V> event){
        final Context<V> context = event.getContext();

        /*
         * We have a success: remove the context at the top of the stack and
         * supply it with the matched text.
         */
        final ParseTreeContext ctx = parseTreeContexts.pop();
        final MatchTextSupplier supplier = MatchText.from(context);
        ctx.setSupplier(supplier);

        /*
         * If we are back at the root (ie, the stack is empty), we're done:
         * build the node.
         */
        if (parseTreeContexts.isEmpty()) {
            rootNode = ctx.getNode();
            return;
        }

        /*
         * If not, take the context at the top of the stack and add this
         * context as a child context.
         */
        parseTreeContexts.peek().addChild(ctx);
    }

    @Override
    public void matchFailure(final MatchFailureEvent<V> event) {
        /*
         * If the match is a failure, remove the context at the top of the stack
         * unconditionally.
         */
        parseTreeContexts.pop();
    }

    /**
     * Get the root {@code ParseNode} of the parse tree
     *
     * @return      the root node
     * @exception IllegalStateException attempt to retrieve the parse tree from
     * a failed match
     */
    public ParseNode getTree(){
        if (rootNode == null)
            throw new IllegalStateException(MATCH_FAILURE);
        return rootNode;
    }

    private static final class MatchText
        implements MatchTextSupplier
    {
        private final InputBuffer buffer;
        private final int start;
        private final int end;

        private String matchText = null;

        private static MatchText from(final Context<?> context)
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
        public synchronized String getText()
        {
            if (matchText == null)
                matchText = buffer.subSequence(start, end).toString();
            return matchText;
        }

        @Override
        public Position getStartPosition()
        {
            return buffer.getPosition(start);
        }

        @Override
        public Position getEndPosition()
        {
            return buffer.getPosition(end);
        }
    }
}
