package com.github.chrisbrenton.grappa.parsetree.node;

import com.github.fge.grappa.support.Position;

import java.util.List;

/**
 * A supplier of the matched text for a {@link ParseNode}
 *
 * @see ParseNode#ParseNode(MatchTextSupplier, List)
 * @see ParseNode#getMatchedText()
 */
public interface MatchTextSupplier
{
    String getText();

    Position getStartPosition();

    Position getEndPosition();
}
