package com.github.fge.grappa.parsetree.annotations;

/**
 * Created by Chris on 10/03/2016.
 */

import com.github.fge.grappa.parsetree.nodes.ParseNode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateNode
{
	Class<? extends ParseNode> value();
}
