package com.github.fge.grappa.parsetree.test;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsetree.listeners.ParseNodeConstructorRepository;
import com.github.fge.grappa.parsetree.listeners.ParseTreeListener;
import com.github.fge.grappa.run.ListeningParseRunner;

public final class InspectionTest
{

	private InspectionTest(){
			throw new Error("no instantiation is permitted");
	}

	public static void main(final String... args) {
		final Class<TestParser> parserClass = TestParser.class;

		final ParseNodeConstructorRepository repository
			= new ParseNodeConstructorRepository(parserClass);
		final TestParser parser = Grappa.createParser(parserClass);

		final ListeningParseRunner<Object> runner
			= new ListeningParseRunner<>(parser.rule1());

		final ParseTreeListener<Object> listener
			= new ParseTreeListener<>(repository);



		runner.registerListener(listener);
		runner.run("afk");

		/* There is nothing stopping the user from creating a list of visitors and having them all be accepted in
		 * a loop, if they would like.
		 */
		TestVisitor v = new TestVisitor();
		listener.getRootNode().accept(v);


        System.out.println("done");
    }
}
