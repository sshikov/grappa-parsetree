package com.github.chrisbrenton.grappa.parsetree.example.calculator;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public final class ExpressionNode
    extends ParseNode
    implements Supplier<BigDecimal>
{
    public ExpressionNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public String getValue()
    {
        return "EXPRESSION";
    }

    @SuppressWarnings({ "BadOddness", "unchecked", "CastToConcreteClass" })
    @Override
    public BigDecimal get()
    {
        final Iterator<ParseNode> iterator = children.iterator();

        BigDecimal ret = ((Supplier<BigDecimal>) iterator.next()).get();

        OperatorNode operator;
        BigDecimal operand;

        while (iterator.hasNext()) {
            operator = (OperatorNode) iterator.next();
            operand = ((Supplier<BigDecimal>) iterator.next()).get();
            ret = operator.apply(ret, operand);
        }

        return ret;
    }
}
