package com.github.fge.grappa.parsetree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public class ParseTree<E> implements Tree<E>, Visitable{

    private List<Tree<E>> children = new ArrayList<>();
    private Tree<E> parent = null;
    private E value = null;

    /* Public Constructors */
    public ParseTree(E value){
        this.value = value;
    }

    public ParseTree(E value, List<Tree<E>> children){
        this.value = value;
        this.children = children;
    }

    /* Copy Constructor */
    public ParseTree(ParseTree<E> tree){
        this.value = tree.value;
        this.children = tree.children;
    }

    /* Private Constructor */
    private ParseTree(E value, ParseTree<E> parent){
        this.value = value;
        this.parent = parent;
    }

    ////////////////// TREE INTERFACE //////////////////////////////////////////////////////////////////////////////////
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addChild(E value) {
        return children.add(new ParseTree<>(value, this));
    }

	/**
     * {@inheritDoc}
     */
    @Override
    public Tree<E> getParent() {
        return parent;
    }

    @Override
    public Tree<E> removeChild(Tree<E> child) {
        return children.remove(children.indexOf(child));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasChildren() {
        return children.size() > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Tree<E>> getChildren() {
        return children;
    }

    ////////////////// VISITABLE INTERFACE /////////////////////////////////////////////////////////////////////////////
    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
