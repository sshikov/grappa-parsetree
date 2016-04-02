package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.fge.grappa.parsers.BaseParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A parse tree context for a {@linkplain BaseParser#sequence(Object, Object,
 * Object...) sequence}, with no node attached
 */
final class SequenceContext
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
        return Collections.unmodifiableList(builders);
    }
}
