package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.chrisbrenton.grappa.parsetree.visit.ChildNode;
import org.mockito.InOrder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public final class ParseNodeBuilderTest {
	private static final MatchTextSupplier SUPPLIER
		= mock(MatchTextSupplier.class);

	/*
	 * The result of .build() on child builders will be mocked; we don't care
	 * about the constructor, only that it is valid.
	 *
	 * However, Constructor<?> is final, so we have to grab a real constructor
	 * there.
	 */
	private static final Constructor<? extends ParseNode> CONSTRUCTOR;

	static {
		try {
			CONSTRUCTOR = ChildNode.class.getConstructor(MatchTextSupplier.class, List.class);
		}
		catch (NoSuchMethodException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final ParseNode childNode1 = mock(ParseNode.class);
	private final ParseNode childNode2 = mock(ParseNode.class);

	private ParseNodeBuilder parent;
	private ParseNodeBuilder child1;
	private ParseNodeBuilder child2;

	@BeforeMethod
	public void initBuilders() {
		parent = spy(new ParseNodeBuilder(CONSTRUCTOR));
		child1 = spy(new ParseNodeBuilder(CONSTRUCTOR));
		child2 = spy(new ParseNodeBuilder(CONSTRUCTOR));
	}

	@Test
	public void childrenBuildersCalled() {
		doReturn(childNode1).when(child1).build();
		doReturn(childNode2).when(child2).build();

		parent.setMatchTextSupplier(SUPPLIER);
		parent.addChild(child1);
		parent.addChild(child2);

		final InOrder inOrder = inOrder(child1, child2);

		final ParseNode node = parent.build();

		inOrder.verify(child1).build();
		inOrder.verify(child2).build();
		inOrder.verifyNoMoreInteractions();

		assertThat(node.getChildren()).containsExactly(childNode1, childNode2);
	}
}
