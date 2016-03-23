package com.github.chrisbrenton.grappa.parsetree.node;

import java.util.function.Supplier;

/**
 * A supplier of the matched text for a {@link ParseNode}
 */
@FunctionalInterface
public interface MatchTextSupplier
    extends Supplier<String>
{
}
