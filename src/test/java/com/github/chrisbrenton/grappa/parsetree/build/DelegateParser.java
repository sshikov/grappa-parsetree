package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.visit.ChildNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;

/**
 * @author Chris <chrisbrenton90@gmail.com>
 * @date 19/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public class DelegateParser extends BaseParser<Void> {

	@GenerateNode(ChildNode.class)
	@Label("ruleTwo")
	public Rule ruleTwo(){
		return charRange('f','j');
	}
}
