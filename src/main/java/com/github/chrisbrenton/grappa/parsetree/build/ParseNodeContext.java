package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.parsers.BaseParser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * A parse tree context with an attached parse node
 *
 * <p><strong>FIXME: hack!</strong> The constructor has to be aware as to
 * whether the matcher associated with this node is a {@linkplain
 * BaseParser#join(Object) join matcher}. The reason is that children are, at
 * the moment, added unconditionally to the parent on success. But if it is a
 * join matcher, the number of nodes we can generate MUST be odd.</p>
 */
final class ParseNodeContext
    implements ParseTreeContext
{
    private final ParseNodeBuilder builder;
    private final boolean isJoin;
    private final List<ParseNodeBuilder> children = new ArrayList<>();

    ParseNodeContext(final Constructor<? extends ParseNode> constructor,
        final boolean isJoin)
    {
        builder = new ParseNodeBuilder(constructor);
        this.isJoin = isJoin;
    }

    @Override
    public void addChild(final ParseTreeContext context)
    {
        children.addAll(context.getBuilders());
    }

    @Override
    public void setMatch(final MatchTextSupplier supplier)
    {
        builder.setMatchTextSupplier(supplier);
    }

    @Override
    public boolean hasNode()
    {
        return true;
    }

    @Override
    public ParseNode build()
    {
        int size = children.size();
        if (isJoin && size % 2 == 0)
            size--;
        IntStream.range(0, size).mapToObj(children::get)
            .forEach(builder::addChild);
        return builder.build();
    }

    @Override
    public List<ParseNodeBuilder> getBuilders()
    {
        return Collections.singletonList(builder);
    }
}
