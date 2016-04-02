package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.PreMatchEvent;

import java.util.Collections;
import java.util.List;

/**
 * A parse tree context
 *
 * <p>One such context is created for each level of the matching process (see
 * {@link ParseTreeBuilder#beforeMatch(PreMatchEvent)}.</p>
 *
 * <p>Depending on whether the matcher has an attached node to it (that is,
 * depending on whether the matcher is annotated with {@link GenerateNode}), the
 * implementation will differ.</p>
 *
 * @see ParseTreeContextFactory#createContext(Context)
 */
interface ParseTreeContext
{
    /**
     * Add one child context to this context
     *
     * <p>The context as an argument is guaranteed never to be null.</p>
     *
     * <p>For contexts with no node attached, this method does nothing.</p>
     *
     * @param context the child context
     */
    void addChild(ParseTreeContext context);

    /**
     * Set the matched text for this context
     *
     * <p>The matched text as an argument is guaranteed never to be null.</p>
     *
     * <p>For contexts with no node attached, this method does nothing.</p>
     *
     * @param supplier the matched text
     */
    void setMatch(MatchTextSupplier supplier);

    /**
     * Tell whether this context has an attached node
     *
     * <p>Only contexts whose matchers are annotated with {@link GenerateNode}
     * will return true here. The default implementation returns false.</p>
     *
     * @return see description
     */
    default boolean hasNode()
    {
        return false;
    }

    /**
     * Build a parse node from this context if possible
     *
     * <p>Only contexts whose matchers are annotated with {@link GenerateNode}
     * will return true here. The default implementation throws an {@link
     * IllegalStateException}.</p>
     *
     * @return the node for this context, if possible
     */
    default ParseNode build()
    {
        throw new IllegalStateException();
    }

    /**
     * Get the list of parse node builders from this context
     *
     * <p>Only contexts whose matchers are annotated with {@link GenerateNode}
     * will return a meaningful list here (possibly a singleton list). The
     * default implementation returns an empty list.</p>
     *
     * @return see description
     */
    default List<ParseNodeBuilder> getBuilders()
    {
        return Collections.emptyList();
    }
}
