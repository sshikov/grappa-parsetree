package com.github.fge.grappa.parsetree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public class ParseTree<T> implements Tree<T>, Visitable{

    private List<ParseTree<T>> children = new ArrayList<>();
    private T value = null;

    public ParseTree(T value){
        this.value = value;
    }

    public ParseTree(T value, List<ParseTree<T>> children){
        this.value = value;
        this.children = children;
    }

    public ParseTree(ParseTree<T> tree){
        this.value = tree.value;
        this.children = tree.children;
    }

    ////////////////// TREE INTERFACE //////////////////////////////////////////////////////////////////////////////////
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addChild(T value) {
        return children.add(new ParseTree<>(value));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tree removeChild(Tree value) {
        return children.remove(children.indexOf(value));
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
    public List<? extends Tree> getChildren() {
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
