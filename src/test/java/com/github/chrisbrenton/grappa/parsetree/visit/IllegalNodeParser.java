package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class IllegalNodeParser extends BaseParser<Object> {
    @GenerateNode(IllegalNode.class)
    public Rule theRule()
    {
        return NOTHING;
    }
}
