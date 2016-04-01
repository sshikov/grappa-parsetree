package com.github.chrisbrenton.grappa.parsetree.example.calculator;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

public class SimpleExpressionParser
    extends BaseParser<Void>
{
    @GenerateNode(NumberNode.class)
    public Rule number()
    {
        return oneOrMore(digit());
    }

    @GenerateNode(OperatorNode.class)
    public Rule operator()
    {
        return anyOf("+-*/");
    }

    public Rule operand()
    {
        return firstOf(
            number(),
            sequence(
                '(',
                zeroOrMore(wsp()),
                expression(),
                zeroOrMore(wsp()),
                ')'
            )
        );
    }

    @GenerateNode(ExpressionNode.class)
    public Rule expression()
    {
        return join(operand())
            .using(zeroOrMore(wsp()), operator(), zeroOrMore(wsp()))
            .min(1);
    }

    public Rule input()
    {
        return sequence(expression(), EOI);
    }
}
