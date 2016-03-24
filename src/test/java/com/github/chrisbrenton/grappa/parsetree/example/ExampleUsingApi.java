package com.github.chrisbrenton.grappa.parsetree.example;

import com.github.chrisbrenton.grappa.parsetree.api.ParseTree;
import com.github.chrisbrenton.grappa.parsetree.visit.VisitOrder;
import com.github.chrisbrenton.grappa.parsetree.visit.VisitorRunner;

/**
 * This is an example of how you might use grappa-parsetree
 */
public final class ExampleUsingApi
{
	/* Do not attempt to create an instance of this class! */
	private ExampleUsingApi() {
		throw new Error("no instantiation is permitted");
	}

	/* The main method! */
	public static void main(final String... args) {
		final ParseTree<Void, SentenceNode> parseTree = ParseTree
			.usingParser(SentenceParser.class)
			.withRule(SentenceParser::sentence)
			.withRoot(SentenceNode.class);

		final SentenceNode rootNode = parseTree.parse("John hit the ball");
		/* Create a visitor runner, and provide the root node to start visiting from. */
		VisitorRunner visitorRunner = new VisitorRunner(rootNode);

		/* Create a visitor */
		ExampleVisitor v = new ExampleVisitor();

		/* Register your visitor. */
		visitorRunner.registerVisitor(v);

		/* Run the visitors on the parse tree using a defined traversal order. The default is a
		post order traversal, here we specify a pre order traversal. A third option is breadth
		first traversal. */
		visitorRunner.run(VisitOrder.PREORDER);

		/* Done! */
		System.out.println(v.getSillySentence());
	}
}
