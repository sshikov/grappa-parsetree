import com.github.cbrenton.grappa.parsetree.visit.ChildNode;
import com.github.chrisbrenton.grappa.parsetree.builders.ParseNodeBuilder;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import org.mockito.InOrder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public final class ParseNodeBuilderTest {
	private static final String WHATEVER = "whatever";

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
			CONSTRUCTOR = ChildNode.class.getConstructor(String.class);
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

		parent.setMatch(WHATEVER);
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
