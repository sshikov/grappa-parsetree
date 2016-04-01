package com.github.chrisbrenton.grappa.parsetree.example.calculator;


import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Supplier;

public final class NumberNode
    extends ParseNode
    implements Supplier<BigDecimal>
{
    public NumberNode(final MatchTextSupplier supplier,
        final List<ParseNode> children)
    {
        super(supplier, children);
    }

    @Override
    public BigDecimal get()
    {
        return new BigDecimal(getMatchedText());
    }
}
