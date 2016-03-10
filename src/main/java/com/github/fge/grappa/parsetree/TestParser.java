package com.github.fge.grappa.parsetree;

import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class TestParser
    extends BaseParser<Object>
{
    public Rule rule1()
    {
        return oneOrMore(rule2());
    }

    @GenerateNode(ParseNodeImpl.class)
    @Label("meh")
    public Rule rule2()
    {
        return ANY;
    }
}
