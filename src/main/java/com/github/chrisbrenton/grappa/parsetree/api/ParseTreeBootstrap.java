package com.github.chrisbrenton.grappa.parsetree.api;

import com.github.chrisbrenton.grappa.parsetree.build
    .ParseNodeConstructorProvider;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

import java.util.Objects;
import java.util.function.Function;

/**
 * The first step involved in building a {@link ParseTree} instance
 *
 * <p>An instance of this class is created using {@link
 * ParseTree#usingParser(Class)}. Its only method, {@link #withRule(Function)},
 * yields an instance of a {@link ParseTreeRule} for method chaining.</p>
 *
 * @param <T> type parameter of the elements of the parser class
 * @param <P> type parameter of the parser
 */
public final class ParseTreeBootstrap<T, P extends BaseParser<T>>
{
    private final P parser;
    private final ParseNodeConstructorProvider provider;

    ParseTreeBootstrap(final Class<P> parserClass)
    {
        parser = Grappa.createParser(parserClass);
        provider = new ParseNodeConstructorProvider(parserClass);
    }

    public ParseTreeRule<T, P> withRule(final Function<? super P, Rule> fn)
    {
        Objects.requireNonNull(fn);
        return new ParseTreeRule<>(parser, provider, fn);
    }
}
