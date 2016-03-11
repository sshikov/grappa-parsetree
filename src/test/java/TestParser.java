import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.parsetree.annotations.GenerateNode;
import com.github.fge.grappa.rules.Rule;

/**
 * Created by Chris on 10/03/2016.
 */
public class TestParser extends BaseParser<Object>{

	@GenerateNode(Rule1Node.class)
	@Label("Rule 1")
	public Rule rule1(){
		return oneOrMore(rule2()); // adding rule3() means that rule1() is never matched in output.
	}

	@GenerateNode(Rule2Node.class)
	@Label("Rule 2")
	public Rule rule2()
	{
		return oneOrMore(rule4(), rule5(), rule6());
	}

	@GenerateNode(Rule3Node.class)
	@Label("Rule 3")
	public Rule rule3(){
		return oneOrMore(rule7(), rule8());
	}

	@GenerateNode(Rule4Node.class)
	@Label("Rule 4")
	public Rule rule4(){
		return charRange('a','e');
	}

	@GenerateNode(Rule5Node.class)
	@Label("Rule 5")
	public Rule rule5(){
		return charRange('f', 'j');
	}

	@GenerateNode(Rule6Node.class)
	@Label("Rule 6")
	public Rule rule6(){
		return charRange('k','r');
	}

	@GenerateNode(Rule7Node.class)
	@Label("Rule 7")
	public Rule rule7(){
		return charRange('s','z');
	}

	@GenerateNode(Rule8Node.class)
	@Label("Rule 8")
	public Rule rule8(){
		return oneOrMore(digit());
	}

}

