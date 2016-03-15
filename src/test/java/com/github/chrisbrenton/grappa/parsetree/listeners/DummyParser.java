package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.visit.ParentNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;

public class DummyParser
		extends BaseParser<Void> {
	@GenerateNode(ParentNode.class)
	@Label("voidRule")
	public void voidRule() {
		//do nothing
	}
}
