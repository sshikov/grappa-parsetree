package com.github.chrisbrenton.grappa.parsetree.api;

import com.github.chrisbrenton.grappa.parsetree.build.ParseNodeConstructorProvider;
import com.github.chrisbrenton.grappa.parsetree.build.ParseTreeBuilder;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.fge.grappa.run.ParseRunner;

import java.util.Objects;

public final class ParseTree<N extends ParseNode>
{
    private final Rule rule;
    private final ParseNodeConstructorProvider provider;
    private final Class<N> nodeClass;

    public static <T, P extends BaseParser<T>> ParseTreeBootstrap<T, P>
        usingParser(final Class<P> parserClass)
    {
        Objects.requireNonNull(parserClass);
        return new ParseTreeBootstrap<>(parserClass);
    }

    ParseTree(final Rule rule, final ParseNodeConstructorProvider provider,
        final Class<N> nodeClass)
    {
        this.rule = rule;
        this.provider = provider;
        this.nodeClass = nodeClass;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public N parse(final CharSequence input)
    {
        /*
         * OK, this is ugly.
         *
         * We don't care about the type of values stored in the stack in this
         * scenario. Which means we must use raw type invocations of our runners
         * and event listeners.
         *
         * This works in practice, but... Well... We have to @SuppressWarnings
         * a lot :(
         */
        final ParseRunner runner = new ParseRunner(rule);

        final ParseTreeBuilder builder = new ParseTreeBuilder(provider);

        runner.registerListener(builder);

        if (!runner.run(input).isSuccess())
            throw new GrappaException("parsing failed! Cannot retrieve the tree");

        return nodeClass.cast(builder.getTree());
    }
}
