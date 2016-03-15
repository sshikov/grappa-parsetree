package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class DuplicateLabelParser
    extends BaseParser<Object> {

    @GenerateNode(ParentNode.class)
    public Rule theRule()
    {
        return NOTHING;
    }

    @GenerateNode(ParentNode.class)
    @Label("theRule")
    public Rule otherRule()
    {
        return NOTHING;
    }
}
