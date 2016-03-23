package com.github.chrisbrenton.grappa.parsetree.node;

import java.util.List;
import java.util.function.Supplier;

/**
 * A supplier of the matched text for a {@link ParseNode}
 *
 * @see ParseNode#ParseNode(MatchTextSupplier, List)
 * @see ParseNode#getMatchedText()
 */
@FunctionalInterface
public interface MatchTextSupplier
    extends Supplier<String>
{
}
