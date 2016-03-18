package com.github.chrisbrenton.grappa.parsetree.builders;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.internal.NonFinalForTesting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The basic class for constructing a
 * <a href="https://en.wikipedia.org/wiki/Parse_tree">Parse tree</a>.
 *
 * <p></p>
 */
@NonFinalForTesting
public class ParseTreeBuilder
{
    private final Constructor<? extends ParseNode> constructor;
    private final List<ParseTreeBuilder> builders = new ArrayList<>();

    private String match;

	/**
     *
     * @param constructor   The constructor of the node that this builder will represent.
     */
    public ParseTreeBuilder(final Constructor<? extends ParseNode> constructor)
    {
        this.constructor = Objects.requireNonNull(constructor);
    }

    public void setMatch(final String match)
    {
        this.match = match;
    }

    public void addChild(final ParseTreeBuilder builder)
    {
        builders.add(Objects.requireNonNull(builder));
    }

	/**
     * Recursively build all the children of this builder, and then build the node of the Parse
     * Tree that it represents.
     *
     * @return A {@link ParseNode}
     */
    public ParseNode build()
    {
        Objects.requireNonNull(constructor);
        Objects.requireNonNull(match);

        final List<ParseNode> children = builders.stream()
            .map(ParseTreeBuilder::build)
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
