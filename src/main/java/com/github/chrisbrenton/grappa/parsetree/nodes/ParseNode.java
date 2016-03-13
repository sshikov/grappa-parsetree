package com.github.chrisbrenton.grappa.parsetree.nodes;

import com.google.common.collect.ImmutableList;

import java.util.List;


public abstract class ParseNode {

    private final List<ParseNode> children;
    private final String value;

    /* Public Constructors */
    protected ParseNode(final String value, final List<ParseNode> children){
        this.value = value;
        this.children = ImmutableList.copyOf(children);
    }


	/**
	 * Get the value held by this {@code ParseNode}.
     * @return          The value held by this node.
     */
    public String getValue(){
        return value;
    }

    /**
     * Returns whether or not this {@code ParseNode} has children.
     * @return          <b>true</b> if this parse node has children, <b>false</b> otherwise
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get the children of this {@code ParseNode}
     * @return          The children of this {@code Tree}, returns empty list if no children are found.
     */
    public List<ParseNode> getChildren() {
        // This is an ImmutableList; no risk of it being modified by the user
        return children;
    }

}
