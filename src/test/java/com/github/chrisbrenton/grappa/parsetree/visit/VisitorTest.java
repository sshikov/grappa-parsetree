package com.github.chrisbrenton.grappa.parsetree.visit;

import com.github.chrisbrenton.grappa.parsetree.build.ParseNodeConstructorProvider;
import com.github.chrisbrenton.grappa.parsetree.build.ParseTreeBuilder;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.Grappa;
import com.github.fge.grappa.run.ParseRunner;
import com.github.fge.grappa.run.ParsingResult;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

public final class VisitorTest {
	private DummyParser parser;
	private ParseTreeBuilder<Void> listener;

	@BeforeMethod
	public void init() {
		final Class<DummyParser> parserClass = DummyParser.class;
		final ParseNodeConstructorProvider provider
				= new ParseNodeConstructorProvider(parserClass);

		parser = Grappa.createParser(parserClass);
		listener = new ParseTreeBuilder<>(provider);
	}

	/*
	 * Ensure that all children are visited, and then the parents are visited. No further
	 * interactions should occur.
	 */
	@Test
	public void visitOrderTest() {
		final ParseRunner<Void> runner
				= new ParseRunner<>(parser.parent());

		runner.registerListener(listener);

		final ParsingResult<Void> result = runner.run("");

		assertThat(result.isSuccess()).isTrue();

		final ParseNode node = listener.getTree();

		assertThat(node).isInstanceOf(ParentNode.class);

		for (final ParseNode child : node.getChildren()) {
			assertThat(child).isInstanceOf(ChildNode.class);
		}

		final DummyVisitor visitor = spy(new DummyVisitor());

		final VisitorRunner visitorRunner = new VisitorRunner(node);
		visitorRunner.registerVisitor(visitor);

		visitorRunner.run();

		final InOrder inOrder = Mockito.inOrder(visitor);
		inOrder.verify(visitor, times(2)).visit(any(ChildNode.class));
		inOrder.verify(visitor).visit(any(ParentNode.class));
		inOrder.verifyNoMoreInteractions();
	}
}
