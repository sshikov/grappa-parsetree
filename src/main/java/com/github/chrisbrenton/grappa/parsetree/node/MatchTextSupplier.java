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
    /**
     * Return the exact text matched
     *
     * @return the matched text, possibly empty
     */
    String getText();

    /**
     * Return the start position of the matched text (inclusive)
     *
     * @return the position
     */
    Position getStartPosition();

    /**
     * Return the end position of the matched text (exclusive)
     *
     * @return the position
     */
    Position getEndPosition();
}
