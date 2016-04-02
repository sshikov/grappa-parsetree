package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
