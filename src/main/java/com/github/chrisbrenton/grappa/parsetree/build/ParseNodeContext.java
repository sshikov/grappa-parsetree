package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;

final class ParseNodeContext
    implements ParseTreeContext
{
    private final ParseNodeBuilder builder;

    ParseNodeContext(final Constructor<? extends ParseNode> constructor)
    {
        builder = new ParseNodeBuilder(constructor);
    }

    @Override
    public void addChild(final ParseTreeContext context)
    {
        context.getBuilders().forEach(builder::addChild);
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
        return builder.build();
    }

    @Override
    public List<ParseNodeBuilder> getBuilders()
    {
        return Collections.singletonList(builder);
    }
}
