package com.github.fge.grappa.parsetree.listeners;

import com.github.fge.grappa.matchers.MatcherType;
import com.github.fge.grappa.matchers.base.Matcher;
import com.github.fge.grappa.parsetree.builders.ParseTreeBuilder;
import com.github.fge.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.run.ParseRunnerListener;
import com.github.fge.grappa.run.context.Context;
import com.github.fge.grappa.run.events.MatchSuccessEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public final class ParseTreeListener<V> extends ParseRunnerListener<V> {

    private final Map<String, Class<? extends ParseNode>> nodeMap;
    private final Map<Class<?>, Constructor<?>> constructors = new HashMap<>();

    private ParseTreeBuilder builder = new ParseTreeBuilder();


    public ParseTreeListener(final Map<String, Class<? extends ParseNode>> nodeMap){
        this.nodeMap = nodeMap;
    }

    @Override
    public void matchSuccess(final MatchSuccessEvent<V> event){

        final Context<V> context = event.getContext();

        // No nodes if we are in a predicate, please
        if (context.inPredicate())
            return;

        final Matcher matcher = context.getMatcher();

        // Nothing for actions either
        if (matcher.getType() == MatcherType.ACTION)
            return;

        //get the label from the rule
        final String label = matcher.getLabel();

        //get the ParseNode subclass from our cache
        final Class<? extends ParseNode> nodeClass = nodeMap.get(label);

        //if there was no mapping found, return
        if (nodeClass == null)
            return;

        //get the constructor of our ParseNode subclass
        final Constructor<? extends ParseNode> constructor = getConstructor(
                nodeClass);

        //get the start and end of the match, and extract the match
        final int start = context.getStartIndex();
        final int end = context.getCurrentIndex();
        final String match = context.getInputBuffer().extract(start, end);

        //create our ParseNode subclass with the matched string passed in
        final ParseNode node;
        try {
            node = constructor.newInstance(match);
        } catch (InstantiationException | InvocationTargetException
                | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //if (context.getLevel() == 0 && )
        //builder.append(node);
        System.out.println(node.getClass().getSimpleName() + " " +node.getValue() + ": " + context.getLevel());
    }



    private <T extends ParseNode> Constructor<T> getConstructor(
            final Class<T> nodeClass)
    {
        try {
            return nodeClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
