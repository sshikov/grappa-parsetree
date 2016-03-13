package com.github.chrisbrenton.grappa.parsetree.builders;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.internal.NonFinalForTesting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NonFinalForTesting
public class ParseNodeBuilder
{
    private final Constructor<? extends ParseNode> constructor;
    private final List<ParseNodeBuilder> builders = new ArrayList<>();

    private String match;

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

        final List<ParseNode> children = builders.stream()
            .map(ParseNodeBuilder::build)
            .collect(Collectors.toList());

        try {
            return constructor.newInstance(match, children);
        } catch (InstantiationException | IllegalAccessException
            | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString()
    {
        return constructor.getDeclaringClass().getSimpleName();
    }
}
