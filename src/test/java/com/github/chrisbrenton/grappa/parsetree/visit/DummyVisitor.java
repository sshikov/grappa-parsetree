package com.github.chrisbrenton.grappa.parsetree.visit;

import com.google.common.eventbus.Subscribe;

public class DummyVisitor implements Visitor
{
    @Subscribe
    public void visit(final ParentNode node){
        //do nothing - for testing purposes only.
    }

    @Subscribe
    public void visit(final ChildNode node){
        //do nothing - for testing purposes only.
    }
}
