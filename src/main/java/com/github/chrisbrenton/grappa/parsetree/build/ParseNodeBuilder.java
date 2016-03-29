package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.internal.NonFinalForTesting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The basic class for constructing a
 * <a href="https://en.wikipedia.org/wiki/Parse_tree">Parse Tree</a>.
 *
 * <p>This class represents a single {@link ParseNode} and provides the operations to interact
 * with the {@link ParseNode} that is represents.
 * </p>
 * <p>The main purpose of this class is to provide a Builder pattern to the Parse Tree. The
 * {@link #build()} method provides a mechanism for recursively building the node represented by
 * the builder, and all its children. The net result of this is that a Parse Tree is created.
 * </p>
 *
 * {@see ParseNode}
 */
@NonFinalForTesting
class ParseNodeBuilder
{
    private final Constructor<? extends ParseNode> constructor;

    private final List<ParseNodeBuilder> builders = new ArrayList<>();
    private MatchTextSupplier supplier;

	/**
     * Constructor
     *
     * @param constructor   The constructor of the node that this builder will represent.
     */
    ParseNodeBuilder(final Constructor<? extends ParseNode> constructor)
    {
        this.constructor = Objects.requireNonNull(constructor);
    }

    void setMatchTextSupplier(final MatchTextSupplier supplier)
    {
        this.supplier = Objects.requireNonNull(supplier);
    }

	/**
     * Add a ParseNodeBuilder presenting a node and its children as a child of this
     * ParseNodeBuilder.
     *
     * @param builder   The ParseNodeBuilder to add as a child.
     */
    void addChild(final ParseNodeBuilder builder)
    {
        builders.add(Objects.requireNonNull(builder));
    }

	/**
     * Recursively build all the children of this ParseNodeBuilder, and then build the node of that
     * this builder represents. The net result of calling this method is that a ParseNode with all its
     * children is returned, and thus a ParseTree is returned.
     *
     * @return A {@link ParseNode}
     */
    ParseNode build()
    {
        final List<ParseNode> children = builders.stream()
            .map(ParseNodeBuilder::build)
            .collect(Collectors.toList());

        try {
            return constructor.newInstance(supplier, children);
        } catch (InstantiationException | IllegalAccessException
            | InvocationTargetException e) {
            String message = String.format("Unable to build an instance of class %s",
                                           constructor.getDeclaringClass().getSimpleName());
            throw new IllegalStateException(message, e);
        }
    }

	/**
     * @return A {@link String} representation of this ParseNodeBuilder.
     */
    @Override
    public String toString()
    {
        return constructor.getDeclaringClass().getSimpleName();
    }
}
