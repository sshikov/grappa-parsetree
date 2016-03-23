package com.github.chrisbrenton.grappa.parsetree;

import com.github.chrisbrenton.grappa.parsetree.build.DelegatingParser;
import com.github.chrisbrenton.grappa.parsetree.build.ParseNodeConstructorProvider;
import com.github.chrisbrenton.grappa.parsetree.build.ParseTreeBuilder;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.chrisbrenton.grappa.parsetree.visit.DummyVisitor;
import com.github.chrisbrenton.grappa.parsetree.visit.VisitOrder;
import com.github.chrisbrenton.grappa.parsetree.visit.Visitor;
import com.github.chrisbrenton.grappa.parsetree.visit.VisitorRunner;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.run.ListeningParseRunner;

/**
 * This is an example of how you might use grappa-parsetree
 */
public final class Example {
	/* Do not attempt to create an instance of this class! */
	private Example() {
		throw new Error("no instantiation is permitted");
	}

	/* The main method! */
	public static void main(final String... args) {
		/* The class of our parser */
		final Class<DelegatingParser> parserClass = DelegatingParser.class;

		/* The constructor repository for our parser */
		final ParseNodeConstructorProvider repository
				= new ParseNodeConstructorProvider(parserClass);

		/* The grappa parser! */
		final DelegatingParser parser = Grappa.createParser(parserClass);

		/* The runner that listens for events from the parser */
		final ListeningParseRunner<Object> runner
				= new ListeningParseRunner<>(parser.ruleRoot());

		/* The class that will build the parse tree */
		final ParseTreeBuilder<Object> listener
				= new ParseTreeBuilder<>(repository);

		/* Register the parse tree builder to the runner. This must be done before you run. */
		runner.registerListener(listener);
		/* Run on the given input. */
		runner.run("afk");

		/* Get the root node of the parse tree built. */
		final ParseNode rootNode = listener.getTree();

		/* Create a visitor runner, and provide the root node to start visiting from. */
		VisitorRunner visitorRunner = new VisitorRunner(rootNode);

		/* Create a visitor */
		Visitor v = new DummyVisitor();

		/* Register your visitor. */
		visitorRunner.registerVisitor(v);

		/* Run the visitors on the parse tree using a defined traversal order. The default is a
		post order traversal, here we specify a pre order traversal. A third option is breadth
		first traversal. */
		visitorRunner.run(VisitOrder.PREORDER);

		/* Done! */
		System.out.println("Done!");
	}
}
