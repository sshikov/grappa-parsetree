package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.run.events.PreMatchEvent;
import com.github.fge.grappa.support.Position;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * One parse tree context, used during parsing
 *
 * @see ParseTreeBuilder#beforeMatch(PreMatchEvent)
 */
final class ParseTreeContext
{
    /**
     * The parse node builder, if any (null if none)
     */
    private final ParseNodeBuilder builder;

    /**
     * List of children contexts
     */
    private final List<ParseTreeContext> children = new ArrayList<>();

    /**
     * The text matched by that context
     */
    private MatchTextSupplier supplier;

    /**
     * Constructor
     *
     * @param constructor the constructor for the parse node; {@code null} if
     *                    none
     */
    ParseTreeContext(final Constructor<? extends ParseNode> constructor)
    {
        builder = constructor == null ? null
            : new ParseNodeBuilder(constructor);
    }

    /**
     * Does this context have an associated parse node?
     *
     * @return true if and only if this context has an associated node
     *
     * @see GenerateNode
     */
    boolean hasNode()
    {
        return builder != null;
    }

    /**
     * Add one child context to this context
     *
     * @param context the context to add; never null
     */
    void addChild(final ParseTreeContext context)
    {
        children.add(context);
    }

    /**
     * Set the text matched by this context
     *
     * @param supplier the matched text; never null
     */
    void setSupplier(final MatchTextSupplier supplier)
    {
        this.supplier = supplier;
        if (builder != null)
            builder.setMatchTextSupplier(supplier);
    }

    /**
     * Get the children parse node builders for this context
     *
     * <p>Potentially returns an empty list if this context has no children, or
     * none of its children generate a parse node.</p>
     *
     * @return see description
     */
    private List<ParseNodeBuilder> getBuilders()
    {
        final Position end = supplier.getEndPosition();

        final List<ParseNodeBuilder> ret = new ArrayList<>();

        Position pos;

        for (final ParseTreeContext child: children) {
            pos  = child.supplier.getEndPosition();
            ret.addAll(child.getBuilders());
            /*
             * HACK...
             *
             * This is there only to take care of a join() rule which would have
             * matched an extra joining rule but not a joined rule; the matcher
             * itself will have correctly assessed the situation, but here we
             * need to do the work again...
             */
            if (pos.equals(end))
                break;
        }

        /*
         * If there is no node builder at this level we just return the
         * generated list; otherwise we attach all the children builders to this
         * context's builder and return a singleton with it.
         */
        if (builder == null)
            return ret;

        ret.forEach(builder::addChild);

        return Collections.singletonList(builder);
    }

    /**
     * Build the node for this context
     *
     * <p>Only called from the root context. Note that the null checks in there
     * are in fact redundant.</p>
     *
     * @return the parse tree
     */
    ParseNode getNode()
    {
        Objects.requireNonNull(builder);
        Objects.requireNonNull(supplier);
        return getBuilders().get(0).build();
    }

    @Override
    public String toString()
    {
        return builder == null ? "(no builder)" : builder.toString();
    }
}
