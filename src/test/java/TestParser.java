import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.parsetree.annotations.GenerateNode;
import com.github.fge.grappa.rules.Rule;

/**
 * Created by Chris on 10/03/2016.
 */
public class TestParser extends BaseParser<Object>{

	@GenerateNode(TestNode.class)
	@Label("Test")
	public Rule test(){
		return oneOrMore(alpha(), digit()); // adding rule3() means that rule1() is never matched in output.
	}

}

