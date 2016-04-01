package com.github.chrisbrenton.grappa.parsetree.example.calculator;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiFunction;

public final class OperatorNode
    extends ParseNode
    implements BiFunction<BigDecimal, BigDecimal, BigDecimal>
{
    public OperatorNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public BigDecimal apply(final BigDecimal t, final BigDecimal u)
    {
        switch (getMatchedText().trim()) {
            case "+":
                return t.add(u);
            case "-":
                return t.subtract(u);
            case "*":
                return t.multiply(u);
            case "/":
                return t.divide(u);
        }

        throw new IllegalStateException();
    }
}
