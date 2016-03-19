package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.util.Collections;

public final class IllegalNode extends ParseNode {
    public IllegalNode(final String value) {
        super(value, Collections.emptyList());
    }
}
