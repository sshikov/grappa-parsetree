package com.github.fge.grappa.parsetree;

import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.run.ParseRunnerListener;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.MatchSuccessEvent;

public final class ParseTreeListener<V> extends ParseRunnerListener<V> {

    private ParseTree tree;

    @Override
    public void matchSuccess(final MatchSuccessEvent<V> event)
    {
        final Context<V> context = event.getContext();

        // No nodes if we are in a predicate, please
        if (context.inPredicate())
            return;

        final Matcher matcher = context.getMatcher();

        // Nothing for actions either
        if (matcher.getType() == MatcherType.ACTION)
            return;

        final String matched = context.getMatch();

        // TODO: build tree node here


    }
}
