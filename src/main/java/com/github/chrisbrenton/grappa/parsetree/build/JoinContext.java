package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.fge.grappa.matchers.join.JoinMatcherBootstrap;
import com.github.fge.grappa.parsers.BaseParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A "no node attached", parse tree context for a {@linkplain
 * BaseParser#join(Object) join rule}
 *
 * <p>In the even where this rule succeeds, the list of parse node builders
 * needs to be inspected. We don't want it to end with a {@linkplain
 * JoinMatcherBootstrap#using(Object) joining rule}, which means that the number
 * of children parse node builers (returned by {@link #getBuilders()} must
 * always be odd. If it's even, we return all but the last builder.</p>
 */
final class JoinContext
    implements ParseTreeContext
{
    private final List<ParseNodeBuilder> builders = new ArrayList<>();

    @Override
    public void addChild(final ParseTreeContext context)
    {
        builders.addAll(context.getBuilders());
    }

    @Override
    public void setMatch(final MatchTextSupplier supplier)
    {
    }

    @Override
    public List<ParseNodeBuilder> getBuilders()
    {
        int size = builders.size();
        if (size % 2 == 0)
            size--;
        return Collections.unmodifiableList(builders.subList(0, size));
    }
}
