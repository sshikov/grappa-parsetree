import com.github.fge.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.parsetree.visitors.Visitor;

/**
 * Created by Chris on 10/03/2016.
 */
public class Rule7Node extends ParseNode {
	public Rule7Node(final String value){
		super(value);
	}
	////////////////// VISITABLE INTERFACE /////////////////////////////////////////////////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor visitor) {
		System.out.println("Rule7Node visited");
		super.accept(visitor);
	}
}