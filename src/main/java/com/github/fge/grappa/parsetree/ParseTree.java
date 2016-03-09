package com.github.fge.grappa.parsetree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author      ChrisBrenton
 * @version     09/03/2015
 */
public class ParseTree<T> implements Tree<T>{

    private List<ParseTree<T>> children = new ArrayList<>();
    private T value = null;

    public ParseTree(){
    }

    public ParseTree(T value){
        this.value = value;
    }

    public ParseTree(T value, List<ParseTree<T>> children){
        this.value = value;
        this.children = children;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addChild(T value) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tree removeChild(T value) {
        return null;
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


}
