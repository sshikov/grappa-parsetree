package com.github.chrisbrenton.grappa.parsetree.issue7;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;


public class Issue7Parser
    extends BaseParser<Void>
{
    @GenerateNode(ANode.class)
    public Rule a()
    {
        return ch('a');
    }

    @GenerateNode(AfterA.class)
    public Rule b()
    {
        return ch('b');
    }

    @GenerateNode(AfterA.class)
    public Rule c()
    {
        return ch('c');
    }

    @GenerateNode(Root.class)
    public Rule input()
    {
        return firstOf(
            sequence(a(), b()),
            sequence(a(), c())
        );
    }

    @GenerateNode(Root.class)
    public Rule joinedInput()
    {
        return join(a()).using(b()).min(1);
    }
}
