import com.github.fge.grappa.parsetree.builders.ParseNodeBuilder;
import com.github.fge.grappa.parsetree.nodes.ParseNode;
import org.testng.annotations.Test;
import java.lang.reflect.Constructor;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * @author Chris <chrisbrenton90@gmail.com>
 * @date 11/03/2016
 * <p>
 * <a href="www.github.com/ChrisBrenton">GitHub</a>
 */
public class ParseNodeBuilderTest {

	private ParseNodeBuilder builder1, builder2, builder3;
	private ParseNode node1, node2, node3, node4, node5, node6;
	private final String VALUE = "value";
	private Constructor<? extends ParseNode> con1, con2, con3, con4, con5, con6;

	public ParseNodeBuilderTest() throws NoSuchMethodException {
		node1 = new Rule1Node("afk");
		node2 = new Rule2Node("afk");
		node4 = new Rule4Node("a");
		node5 = new Rule5Node("f");
		node6 = new Rule6Node("k");
		con1 = Rule1Node.class.getConstructor(String.class);
		con2 = Rule2Node.class.getConstructor(String.class);
		con3 = Rule3Node.class.getConstructor(String.class);
		con4 = Rule4Node.class.getConstructor(String.class);
		con5 = Rule5Node.class.getConstructor(String.class);
		con6 = Rule6Node.class.getConstructor(String.class);
	}

	@Test
	public void initBuilder(){
		builder1 = new ParseNodeBuilder(con1);
		builder1.setMatch(VALUE);
		assertThat(builder1.build()).isInstanceOf(Rule1Node.class);
		assertThat(builder1.build().hasChildren()).isFalse();
	}

	@Test
	public void addSingleChildTest(){
		builder3 = new ParseNodeBuilder(con3);
		builder3.setMatch(VALUE);
		builder1.addChild(builder3);
		assertThat(builder1.build().hasChildren()).isTrue();
		assertThat(builder1.build().getChildren().contains(builder3);
	}

	@Test
	public void addMultipleChildrenTest(){
		builder2 = new ParseNodeBuilder(con2);
		builder2.setMatch(VALUE);

	}

	@Test
	public void testChildHasValue(){
		assertThat(builder1.build().getValue()).isEqualTo(VALUE);
	}


}
