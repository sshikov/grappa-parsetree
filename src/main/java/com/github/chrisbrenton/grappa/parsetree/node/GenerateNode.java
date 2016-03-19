package com.github.chrisbrenton.grappa.parsetree.node;

/**
 * The annotation required above {@link com.github.fge.grappa.rules.Rule} methods in order to
 * create a {@link ParseNode} instance in the Parse Tree generated.
 */

import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateNode {
	Class<? extends ParseNode> value();
}
