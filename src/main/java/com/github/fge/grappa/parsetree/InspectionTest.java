package com.github.fge.grappa.parsetree;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.rules.Rule;
import com.github.fge.grappa.run.ListeningParseRunner;
import com.github.fge.grappa.run.ParseRunnerListener;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class InspectionTest
{
    private InspectionTest()
    {
        throw new Error("no instantiation is permitted");
    }

    public static void main(final String... args)
    {
        final Class<TestParser> parserClass = TestParser.class;

        final Method[] methods = parserClass.getMethods();

        final Map<String, Class<? extends ParseNode>> map = new HashMap<>();

        for (final Method method: methods) {
            final Class<?> returnType = method.getReturnType();
            if (!Rule.class.isAssignableFrom(returnType))
                continue;
            final GenerateNode annotation = method
                .getAnnotation(GenerateNode.class);
            if (annotation == null)
                continue;
            final Label label = method.getAnnotation(Label.class);
            final String name = label == null ? method.getName()
                : label.value();

            map.put(name, annotation.value());
        }

        final TestParser parser = Grappa.createParser(parserClass);

        final ListeningParseRunner<Object> runner
            = new ListeningParseRunner<>(parser.rule1());

        final ParseRunnerListener<Object> listener
            = new ParseTreeListener<>(map);

        runner.registerListener(listener);

        runner.run("abc");

    }
}
