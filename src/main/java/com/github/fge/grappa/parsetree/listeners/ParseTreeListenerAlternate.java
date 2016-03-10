package com.github.fge.grappa.parsetree.listeners;

import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.run.ParseRunnerListener;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.MatchSuccessEvent;
import com.github.fge.grappa.run.events.PreMatchEvent;

import java.lang.reflect.Constructor;
import java.util.SortedMap;
import java.util.TreeMap;

public final class ParseTreeListenerAlternate<V>
    extends ParseRunnerListener<V>
{
    private final ParseNodeConstructorRepository repository;

    private final SortedMap<Integer, ParseNodeBuilder> builders
        = new TreeMap<>();

    public ParseTreeListenerAlternate(
        final ParseNodeConstructorRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void beforeMatch(final PreMatchEvent<V> event)
    {
        final Context<V> context = event.getContext();

        if (context.inPredicate())
            return;

        final Matcher matcher = context.getMatcher();

        if (matcher.getType() == MatcherType.ACTION)
            return;

        final int level = context.getLevel();
        final String ruleName = matcher.getLabel();
        final Constructor<? extends ParseNode> constructor
            = repository.getNodeConstructor(ruleName);

        if (constructor == null) {
            if (level == 0)
                throw new IllegalStateException();
            return;
        }

        final ParseNodeBuilder builder = new ParseNodeBuilder(constructor);

        builders.put(level, builder);
    }

    @Override
    public void matchSuccess(final MatchSuccessEvent<V> event)
    {
        final Context<V> context = event.getContext();

        if (context.inPredicate())
            return;

        final Matcher matcher = context.getMatcher();

        if (matcher.getType() == MatcherType.ACTION)
            return;

        final String ruleName = matcher.getLabel();
        final Constructor<? extends ParseNode> constructor
            = repository.getNodeConstructor(ruleName);

        if (constructor == null)
            return;

        final int level = context.getLevel();

        final String match = getMatch(context);

        final ParseNodeBuilder builder = builders.get(level);
        builder.setMatch(match);

        if (level == 0)
            return;

        final int previousLevel = builders.headMap(level).lastKey();

        builders.get(previousLevel).addChild(builder);
    }

    public ParseNode getRootNode()
    {
        return builders.get(0).build();
    }

    private static String getMatch(final Context<?> context)
    {
        final int start = context.getStartIndex();
        final int end = context.getCurrentIndex();
        return context.getInputBuffer().extract(start, end);
    }
}
