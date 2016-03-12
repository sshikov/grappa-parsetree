package com.github.chrisbrenton.grappa.parsetree.visitors;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.google.common.eventbus.EventBus;

import java.util.Objects;

/**
 * Run one or more visitor(s) on a given parse tree
 *
 * <p>The scenario to visit a parse tree is as follows:</p>
 *
 * <ul>
 *     <li>create an instance of this class;</li>
 *     <li>register one or more {@link Visitor}s;</li>
 *     <li>call one of the run methods.</li>
 * </ul>
 *
 * <p>The order in which the nodes of a parse tree are visited depend on the
 * {@link VisitOrder visit order} that you define. By default, this will be a
 * {@link VisitOrder#POSTORDER postorder} traversal.</p>
 *
 * <h2>Implementation details</h2>
 *
 * <p>When you {@code run()} the visitors, you determine an order to traverse
 * the node; the order is an instance of {@link VisitOrder} which, given the
 * root node, will generate an {@code Iterable} over all the nodes using its
 * {@link VisitOrder#visit(ParseNode) visit()} method.</p>
 *
 * <p>The nodes are then fed to an {@link EventBus}, to which the visitors you
 * will have registered in the runner will subscribe to. When a node is posted
 * on the bus (see {@link EventBus#post(Object)}), the appropriate methods will
 * be invoked with this node as an argument.</p>
 *
 * @see Visitor
 * @see VisitOrder
 */
public final class VisitorRunner
{
    private final ParseNode node;
    private final EventBus bus = new EventBus();

    /**
     * Constructor
     *
     * @param node the node at the root of the parse tree
     */
    public VisitorRunner(final ParseNode node)
    {
        this.node = Objects.requireNonNull(node);
    }

    /**
     * Add one visitor to this runner
     *
     * @param visitor the visitor
     */
    public void addVisitor(final Visitor visitor)
    {
        bus.register(Objects.requireNonNull(visitor));
    }

    /**
     * Visit all nodes of the parse tree in a given visit order
     *
     * @param visitOrder the order
     */
    public void run(final VisitOrder visitOrder)
    {
        Objects.requireNonNull(visitOrder).visit(node).forEach(bus::post);
    }

    /**
     * Visit all nodes of the parse tree using a {@link VisitOrder#POSTORDER
     * postorder traversal}
     */
    public void run()
    {
        run(VisitOrder.POSTORDER);
    }
}
