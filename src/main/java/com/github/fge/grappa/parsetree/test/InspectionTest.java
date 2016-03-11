package com.github.fge.grappa.parsetree.test;

import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.parsetree.builders.ParseNodeBuilder;
import com.github.fge.grappa.parsetree.listeners.ParseNodeConstructorRepository;
import com.github.fge.grappa.parsetree.listeners.ParseTreeListener;
import com.github.fge.grappa.run.ListeningParseRunner;

import java.util.Map;
import java.util.SortedMap;

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

		TestVisitor v = new TestVisitor();

		SortedMap<Integer, ParseNodeBuilder> tree = listener.getTree();
		for (Map.Entry e : tree.entrySet()){
			((ParseNodeBuilder)e.getValue()).build().accept(v);
		}


        System.out.println("done");
    }
}
