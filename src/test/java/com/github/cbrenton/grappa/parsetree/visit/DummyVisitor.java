package com.github.cbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.visitors.Visitor;
import com.google.common.eventbus.Subscribe;

public class DummyVisitor
    implements Visitor
{
    @Subscribe
    public void visit(final ParentNode node)
    {
    }

    @Subscribe
    public void visit(final ChildNode node)
    {
    }
}
