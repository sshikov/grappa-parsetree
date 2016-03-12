package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ParseNodeConstructorRepository
{
    private final Map<String, Constructor<? extends ParseNode>> constructors
        = new HashMap<>();

    public ParseNodeConstructorRepository(
        final Class<? extends BaseParser<?>> parserClass)
    {
        Constructor<? extends ParseNode> constructor;
        String ruleName;

        for (final Method method: parserClass.getMethods()) {
            constructor = getNodeConstructor(method);
            if (constructor == null)
                continue;
            ruleName = getRuleName(method);
            if (constructors.put(ruleName, constructor) != null)
                throw new IllegalStateException("more than one node for rule "
                    + ruleName);
        }
    }

    public Constructor<? extends ParseNode> getNodeConstructor(
        final String ruleName)
    {
        return constructors.get(ruleName);
    }

    private static String getRuleName(final Method method)
    {
        final Label ann = method.getAnnotation(Label.class);

        return ann != null ? ann.value() : method.getName();
    }

    // returns null if method is not a rule OR has no @GenerateNode annotation
    private static Constructor<? extends ParseNode> getNodeConstructor(
        final Method method)
    {
        if (!Rule.class.isAssignableFrom(method.getReturnType()))
            return null;

        final GenerateNode ann = method.getAnnotation(GenerateNode.class);

        if (ann == null)
            return null;

        try {
            return ann.value().getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            // TODO: better error message
            throw new RuntimeException(e);
        }
    }
}
