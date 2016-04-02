package com.github.chrisbrenton.grappa.parsetree.api;

import com.github.chrisbrenton.grappa.parsetree.build.ParseNodeConstructorProvider;
import com.github.chrisbrenton.grappa.parsetree.build.ParseTreeBuilder2;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.exceptions.GrappaException;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.github.fge.grappa.run.ParseRunner;

import java.util.Objects;

/**
 * Parse tree factory
 *
 * <p>Usage:</p>
 *
 * <pre>
 *     final ParseTree&lt;MyNode&gt; parseTree = ParseTree
 *         .usingParser(MyParser.class)
 *         .withRule(MyParser::theRule)
 *         .withRoot(MyNode.class);
 * </pre>
 *
 * <p>You can then use this class to generate parse trees from inputs, all of
 * which must implement {@link CharSequence}, with:</p>
 *
 * <pre>
 *     final MyNode node = parseTree.parse(someInput)
 * </pre>
 *
 * <p>Please note that {@link String} implements {@code CharSequence}.</p>
 *
 * @param <N> type parameter for the root node of the generated tree
 */
public final class ParseTree<N extends ParseNode>
{
    private final Rule rule;
    private final ParseNodeConstructorProvider provider;
    private final Class<N> nodeClass;

    /**
     * Bootstrap method
     *
     * @param parserClass the parser class
     * @param <T> parameter type of the elements of the parser's stack values
     * @param <P> parameter type of the parser class
     * @return a {@link ParseTreeBootstrap}
     */
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

	/**
     * Produce a parse tree from a given input.
     *
     * @param input The text to be parsed.
     * @return  The ParseNode that is found at the root of the Parse Tree.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public N parse(final CharSequence input)
    {
        /*
         * We don't care about the type of values stored in the stack in this
         * scenario. Which means we must use raw type invocations of our runners
         * and event listeners.
         */
        final ParseRunner runner = new ParseRunner(rule);

        final ParseTreeBuilder2 builder = new ParseTreeBuilder2(provider);

        runner.registerListener(builder);

        if (!runner.run(input).isSuccess())
            throw new GrappaException("Parsing failed! Cannot retrieve the tree");

        return nodeClass.cast(builder.getTree());
    }
}
