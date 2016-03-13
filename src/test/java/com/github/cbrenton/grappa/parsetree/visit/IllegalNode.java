package com.github.cbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

import java.util.Collections;

public final class IllegalNode extends ParseNode {
    public IllegalNode(final String value) {
        super(value, Collections.emptyList());
    }
}
