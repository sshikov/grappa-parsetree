package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

final class ParseNodeContext
    implements ParseTreeContext
{
    private final ParseNodeBuilder builder;
    private final boolean isJoin;
    private final List<ParseNodeBuilder> children = new ArrayList<>();

    ParseNodeContext(final Constructor<? extends ParseNode> constructor,
        final boolean isJoin)
    {
        builder = new ParseNodeBuilder(constructor);
        this.isJoin = isJoin;
    }

    @Override
    public void addChild(final ParseTreeContext context)
    {
        children.addAll(context.getBuilders());
    }

    @Override
    public void setMatch(final MatchTextSupplier supplier)
    {
        builder.setMatchTextSupplier(supplier);
    }

    @Override
    public boolean hasNode()
    {
        return true;
    }

    @Override
    public ParseNode build()
    {
        int size = children.size();
        if (isJoin && size % 2 == 0)
            size--;
        IntStream.range(0, size).mapToObj(children::get)
            .forEach(builder::addChild);
        return builder.build();
    }

    @Override
    public List<ParseNodeBuilder> getBuilders()
    {
        return Collections.singletonList(builder);
    }
}
