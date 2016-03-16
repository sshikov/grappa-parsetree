package com.github.chrisbrenton.grappa.parsetree.nodes;

import com.github.chrisbrenton.grappa.parsetree.listeners.ParseTreeListener;
import com.github.chrisbrenton.grappa.parsetree.visitors.VisitOrder;
import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * The basic class for a parse node of a generated parse tree
 *
 * <p>The generated parse tree (which you retrieve using {@link
 * ParseTreeListener#getRootNode()}) is in fact an instance of an implementation
 * of this class. This class provides access to all of its children (using
 * {@link #getChildren()}, which is what allows several traversal modes; see
 * {@link VisitOrder} for more details. A convenience method is also provided to
 * check whether a node has children at all: {@link #hasChildren()}.</p>
 *
 * <p>Two text representations of a node can be retrieved:</p>
 *
 * <ul>
 *     <li>the full text matched (using {@link #getMatchedText()};</li>
 *     <li>a semantically meaningful text value for this node (using {@link
 *     #getValue()}.</li>
 * </ul>
 *
 * <p>The default implementation of {@link #getValue()} is the same as that of
 * {@link #getMatchedText()}. Implementations are encouraged to override this
 * method in order to return a more semantically meaningful value when
 * applicable.</p>
 *
 * <h2>Note about {@link #toString()}</h2>
 *
 * <p>This class overrides {@code toString()}; by default, it returns the class
 * name of the node, minus its package (ie, {@code getClass().getSimpleName()}.
 * An implementation <strong>should not</strong> use this method to convey a
 * semantic significance to a parse node; use/override {@link #getValue()}
 * instead.</p>
 */
public abstract class ParseNode {

    protected final List<ParseNode> children;
    protected final String matchedText;

    /**
     * Base constructor
     *
     * @param matchedText the text matched by this node (see {@link
     * #getMatchedText()}
     * @param children the children of this node
     */
    protected ParseNode(final String matchedText, final List<ParseNode> children){
        this.matchedText = matchedText;
        this.children = ImmutableList.copyOf(children);
    }


	/**
	 * Get a semantically meaningful text representation of this node, if any
     *
     * <p>Implementations are encouraged to override the default implementation;
     * see this class's description for more details</p>
     *
     * @return a semantically meaningful text representation of this node
     */
    public String getValue(){
        return matchedText;
    }

    /**
     * Returns whether or not this node has children
     *
     * @return {@code true} if and only if this parse node has children
     */
    public final boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Get the children of this node, as a {@link List}
     *
     * <p>Note that the returned list is <em>immutable</em>.</p>
     *
     * @return the children of this node, if any; an empty list if no children
     * are present
     */
    public final List<ParseNode> getChildren() {
        // This is an ImmutableList; no risk of it being modified by the user
        return children;
    }

	/**
	 * Get the raw text matched by this node
     *
     * <p>As a recall, {@link #getValue()} returns the same value as this method
     * by default. Unless there is a specific need to do so, users are
     * encouraged to use {@link #getValue()} instead.</p>
     *
     * @return the exact text input matched by this node
     */
    public final String getMatchedText(){
        return matchedText;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName();
    }
}
