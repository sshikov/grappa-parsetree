package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.fge.grappa.parsers.BaseParser;
import com.google.common.collect.TreeTraverser;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

final class ParserTraverser
    extends TreeTraverser<Class<? extends BaseParser<?>>>
{
    static final ParserTraverser INSTANCE = new ParserTraverser();

    private ParserTraverser() {
    }

    @Override
    public Iterable<Class<? extends BaseParser<?>>> children(final Class<? extends BaseParser<?>> root) {
        final List<Class<? extends BaseParser<?>>> ret = new ArrayList<>();

        final Field[] fields = root.getDeclaredFields();

        Class<?> fieldType;
        for (final Field field: fields) {
            fieldType = field.getType();
            if (BaseParser.class.isAssignableFrom(fieldType))
                ret.add((Class<? extends BaseParser<?>>) fieldType);
        }

        return ret;
    }
}
