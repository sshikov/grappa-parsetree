package com.github.fge.grappa.parsetree.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public abstract class ParseNode implements Visitable {

    private List<ParseNode> children = new ArrayList<>();
    private ParseNode parent = null;
    private String value = null;

    /* Public Constructors */
    public ParseNode(String value){
        this.value = value;
    }

    public ParseNode(String value, List<ParseNode> children){
        this.value = value;
        this.children = children;
    }

    /* Copy Constructor */
    public ParseNode(ParseNode tree){
        this.value = tree.value;
        this.children = tree.children;
    }

    /* Private Constructor */
    private ParseNode(String value, ParseNode parent){
        this.value = value;
        this.parent = parent;
    }

	/**
	 * Get the value held by this node.
     * @return          The value held by this node.
     */
    public String getValue(){
        return value;
    }

    /**
     * Add a child to the {@code Tree} with this value
     * @param value     The value of the node to add
     * @return          Whether the value was successfully added
     */
    public boolean addChild(ParseNode value) {
        return children.add(value);
    }

    /**
     * Get the parent {@code Tree} of this {@code Tree}.
     * @return          The {@code Tree} parent of this {@code Tree}. Returns <b>null</b> if this is a root node.
     */
    public ParseNode getParent() {
        return parent;
    }

    /**
     * Remove the first {@code Tree} child with value provided. If the {@code Tree} has children, these children are
     * also removed and returned. If no {@code Tree} is found, then <b>null</b> is returned.
     *
     * @param child     The value of the {@code Tree} to remove.
     * @return          A {@code Tree} representing the node removed and all of it's children.
     */
    public ParseNode removeChild(ParseNode child) {
        return children.remove(children.indexOf(child));
    }


    /**
     * Returns whether or not this {@code Tree} has children.
     * @return          <b>true</b> if this {@code Tree} has children, <b>false</b> otherwise
     */
    public boolean hasChildren() {
        return children.size() > 0;
    }

    /**
     * Get the children of this {@code Tree}
     * @return          The children of this {@code Tree}, <b>null</b> if no children are found.
     */
    public List<ParseNode> getChildren() {
        return children;
    }
}
