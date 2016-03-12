package com.github.chrisbrenton.grappa.parsetree.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class ParseNode {

    private List<ParseNode> children = new ArrayList<>();
    private String value = null;

    /* Public Constructors */
    public ParseNode(String value){
        this.value = value;
    }


	/**
	 * Get the value held by this {@code ParseNode}.
     * @return          The value held by this node.
     */
    public String getValue(){
        return value;
    }

    /**
     * Add a child to the {@code ParseNode} with this value
     * @param value     The value of the node to add
     * @return          Whether the value was successfully added
     */
    public boolean addChild(ParseNode value) {
        return children.add(value);
    }


    /**
     * Returns whether or not this {@code ParseNode} has children.
     * @return          <b>true</b> if this {@code Tree} has children, <b>false</b> otherwise
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get the children of this {@code ParseNode}
     * @return          The children of this {@code Tree}, returns empty list if no children are found.
     */
    public List<ParseNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

}
