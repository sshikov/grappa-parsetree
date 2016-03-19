package com.github.chrisbrenton.grappa.parsetree.node;

import com.github.chrisbrenton.grappa.parsetree.build
	.ParseNodeConstructorProvider;
import com.github.fge.grappa.rules.Rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * The annotation required above {@link Rule} methods in order to create a
 * {@link ParseNode} instance in the generated parse tree generated.
 *
 * @see ParseNodeConstructorProvider
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateNode {
	Class<? extends ParseNode> value();
}
