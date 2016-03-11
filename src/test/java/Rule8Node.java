import com.github.fge.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.parsetree.visitors.Visitor;

/**
 * Created by Chris on 10/03/2016.
 */
public class Rule8Node extends ParseNode {
	public Rule8Node(final String value){
		super(value);
	}
	////////////////// VISITABLE INTERFACE /////////////////////////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor visitor) {
		System.out.println("Rule8Node visited");
		super.accept(visitor);
	}
}