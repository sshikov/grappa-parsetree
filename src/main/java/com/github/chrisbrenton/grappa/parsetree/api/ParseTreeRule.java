package com.github.chrisbrenton.grappa.parsetree.api;

import com.github.chrisbrenton.grappa.parsetree.build
    .ParseNodeConstructorProvider;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

import java.util.Objects;
import java.util.function.Function;

/**
 * The final step involved in building an instance of a {@link ParseTree}
 *
 * <p>An instance of this class is obtained via {@link
 * ParseTreeBootstrap#withRule(Function)}. Its only method ({@link
 * #withRoot(Class)}) produces an instance of a {@link ParseTree}.</p>
 *
 * @param <T> type parameter of the elements of the parser's stack
 * @param <P> type parameter of the parser's class
 */
public final class ParseTreeRule<T, P extends BaseParser<T>>
{
    private final Rule rule;
    private final ParseNodeConstructorProvider provider;

    ParseTreeRule(final P parser, final ParseNodeConstructorProvider provider,
        final Function<? super P, Rule> fn)
    {
        rule = fn.apply(parser);
        this.provider = provider;
    }

    public <N extends ParseNode> ParseTree<N> withRoot(final Class<N> nodeClass)
    {
        Objects.requireNonNull(nodeClass);
        return new ParseTree<>(rule, provider, nodeClass);
    }
}
