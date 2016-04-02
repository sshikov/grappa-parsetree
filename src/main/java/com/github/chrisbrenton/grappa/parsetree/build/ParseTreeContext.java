package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.Collections;
import java.util.List;

interface ParseTreeContext
{
    void addChild(ParseTreeContext context);

    void setMatch(MatchTextSupplier supplier);

    default boolean hasNode()
    {
        return false;
    }

    default ParseNode build()
    {
        throw new IllegalStateException();
    }

    default List<ParseNodeBuilder> getBuilders()
    {
        return Collections.emptyList();
    }
}
