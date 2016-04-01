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

    @GenerateNode(OperatorNode.class)
    public Rule mult()
    {
        return operator('*');
    }

    @GenerateNode(OperatorNode.class)
    public Rule divide()
    {
        return operator('/');
    }

    @GenerateNode(OperatorNode.class)
    public Rule plus()
    {
        return operator('+');
    }

    @GenerateNode(OperatorNode.class)
    public Rule minus()
    {
        return operator('-');
    }

    Rule operator(final char c)
    {
        return sequence(zeroOrMore(wsp()), c, zeroOrMore(wsp()));
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
        return firstOf(
            sequence(operand(), mult(), expression()),
            sequence(operand(), divide(), expression()),
            sequence(operand(), plus(), expression()),
            sequence(operand(), minus(), expression()),
            operand()
        );
    }

    @GenerateNode(ExpressionNode.class)
    public Rule input()
    {
        return sequence(expression(), EOI);
    }
}
