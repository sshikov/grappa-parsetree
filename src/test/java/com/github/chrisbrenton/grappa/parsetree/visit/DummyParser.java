package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class DummyParser
    extends BaseParser<Void>
{
    @GenerateNode(ParentNode.class)
    public Rule parent()
    {
        return sequence(child(), child());
    }

    @GenerateNode(ChildNode.class)
    public Rule child()
    {
        return EMPTY;
    }

    public Rule noAnnotation() { return NOTHING; }

    /*
     * Make this one fail on purpose for testing
     */
    @GenerateNode(ParentNode.class)
    public Rule failingRule() {
        return NOTHING;
    }
}
