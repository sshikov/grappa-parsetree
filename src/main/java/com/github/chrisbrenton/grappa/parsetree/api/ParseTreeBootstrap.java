package com.github.chrisbrenton.grappa.parsetree.api;

import com.github.chrisbrenton.grappa.parsetree.build
    .ParseNodeConstructorProvider;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

import java.util.function.Function;

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
        return new ParseTreeRule<>(parser, provider, fn);
    }
}
