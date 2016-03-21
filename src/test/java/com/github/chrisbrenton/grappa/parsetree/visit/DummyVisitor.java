package com.github.chrisbrenton.grappa.parsetree.visit;

import com.google.common.eventbus.Subscribe;

public class DummyVisitor implements Visitor
{
    @Subscribe
    public void visit(final ParentNode node){
        //do nothing - for testing purposes only.
        System.out.println("Parent: I just visited the value: " + node.getValue());
    }

    @Subscribe
    public void visit(final ChildNode node){
        //do nothing - for testing purposes only.
        System.out.println("Child: I just visited the value:" + node.getValue());
    }
}
