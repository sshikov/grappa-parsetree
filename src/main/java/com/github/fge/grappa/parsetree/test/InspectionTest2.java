package com.github.fge.grappa.parsetree.test;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsetree.listeners.ParseNodeConstructorRepository;
import com.github.fge.grappa.parsetree.listeners.ParseTreeListenerAlternate;
import com.github.fge.grappa.run.ListeningParseRunner;

public final class InspectionTest2
{

	private InspectionTest2(){
			throw new Error("no instantiation is permitted");
	}

	public static void main(final String... args) {
		final Class<TestParser> parserClass = TestParser.class;

		final ParseNodeConstructorRepository repository
			= new ParseNodeConstructorRepository(parserClass);
		final TestParser parser = Grappa.createParser(parserClass);

		final ListeningParseRunner<Object> runner
			= new ListeningParseRunner<>(parser.rule1());

		final ParseTreeListenerAlternate<Object> listener
			= new ParseTreeListenerAlternate<>(repository);

		runner.registerListener(listener);
		runner.run("afk");
        System.out.println("done");
    }
}
