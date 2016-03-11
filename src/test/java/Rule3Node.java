import com.github.fge.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.parsetree.visitors.Visitor;

/**
 * Created by Chris on 10/03/2016.
 */
public class Rule3Node extends ParseNode {
	public Rule3Node(final String value){
		super(value);
	}
	////////////////// VISITABLE INTERFACE /////////////////////////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor visitor) {
		System.out.println("Rule2Node visited");
		super.accept(visitor);
	}
}
