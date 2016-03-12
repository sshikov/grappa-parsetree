package com.github.fge.grappa.parsetree.builders;

import com.github.fge.grappa.internal.NonFinalForTesting;
import com.github.fge.grappa.parsetree.nodes.ParseNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NonFinalForTesting
public class ParseNodeBuilder
{
    private final Constructor<? extends ParseNode> constructor;
    private final List<ParseNodeBuilder> builders = new ArrayList<>();

    private String match = null;

    public ParseNodeBuilder(final Constructor<? extends ParseNode> constructor)
    {
        this.constructor = Objects.requireNonNull(constructor);
    }

    public void setMatch(final String match)
    {
        this.match = match;
    }

    public void addChild(final ParseNodeBuilder builder)
    {
        builders.add(Objects.requireNonNull(builder));
    }

    public ParseNode build()
    {
        Objects.requireNonNull(constructor);
        Objects.requireNonNull(match);

        final ParseNode ret;
        try {
            ret = constructor.newInstance(match);
        } catch (InstantiationException | IllegalAccessException
            | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        builders.stream().map(ParseNodeBuilder::build).forEach(ret::addChild);

        return ret;
    }

    @Override
    public String toString()
    {
        return constructor.getDeclaringClass().getSimpleName();
    }
}
