package com.github.chrisbrenton.grappa.parsetree.visitors;

import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;

/**
 * Marker interface for visitor instances
 *
 * <p>All visitor classes should implement this interface.</p>
 *
 * <p>Methods to visit a given type of node must obey the following conditions:
 * </p>
 *
 * <ul>
 *     <li>they must be {@code public};</li>
 *     <li>they must have one argument, whose class is a subclass of {@link
 *     ParseNode};</li>
 *     <li>they should return {@code void}.</li>
 * </ul>
 *
 * <p>For instance:</p>
 *
 * <pre>
 *     public final class MyVisitor
 *         implements Visitor
 *     {
 *         &#64;Subscribe
 *         public void visitParentNode(final ParentNode node)
 *         {
 *             // code
 *         }
 *
 *         &#64;Subscribe
 *         public void visitChildNode(final ChildNode node)
 *         {
 *             // code
 *         }
 *
 *         // etc
 *     }
 * </pre>
 *
 * <p><strong>Important note:</strong> if your nodes subclass each other, be
 * aware that all {@code &#64;Subscribe} methods whose argument is either of the
 * exact class <em>or any superclass</em> of that node will be called. Depending
 * on the situation, this may be either desirable or undesirable.</p>
 */
public interface Visitor
{
}
