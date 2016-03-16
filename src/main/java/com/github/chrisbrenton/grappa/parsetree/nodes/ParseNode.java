package com.github.chrisbrenton.grappa.parsetree.nodes;

import com.google.common.collect.ImmutableList;

import java.util.List;

public abstract class ParseNode {

    protected final List<ParseNode> children;
    protected final String matchedText;

    /* Public Constructors */
    protected ParseNode(final String matchedText, final List<ParseNode> children){
        this.matchedText = matchedText;
        this.children = ImmutableList.copyOf(children);
    }


	/**
	 * Get the semantic value of this {@code ParseNode}.
     *
     * <p>By default, this method will return the same value as {@link #getMatchedText()}.</p>
     *
     * @return          The value held by this node.
     */
    public String getValue(){
        return matchedText;
    }

    /**
     * Returns whether or not this {@code ParseNode} has children.
     * @return          <b>true</b> if this parse node has children, <b>false</b> otherwise
     */
    public final boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get the children of this {@code ParseNode}
     * @return          The children of this {@code Tree}, returns empty list if no children are found.
     */
    public final List<ParseNode> getChildren() {
        // This is an ImmutableList; no risk of it being modified by the user
        return children;
    }

	/**
	 * Get the text matched by this {@link ParseNode}.
     *
     * <p>The difference between this method and {@link #getValue()} is that this method
     * will return only the text matched by this node, {@link #getValue()} is where any
     * implementation can add any semantic value to the match.</p>
     *
     * @return      The text matched by this {@link ParseNode}.
     */
    public final String getMatchedText(){
        return matchedText;
    }



}
