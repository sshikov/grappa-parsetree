package com.github.chrisbrenton.grappa.parsetree.build;

import com.github.chrisbrenton.grappa.parsetree.node.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.node.MatchTextSupplier;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * A class to collect all constructors for parse nodes defined in a parser and
 * its "children"
 *
 * <p>This class takes a parser class as an argument and will look up all the
 * rules (methods returning a {@link Rule}), in this class and in all children
 * parsers (that is, fields whose type is also a parser). If a rule is further
 * annotated with {@link GenerateNode}, it will take the argument to this
 * annotation and find a suitable constructor.</p>
 *
 * <p>The constructor must obey two criteria:</p>
 * <ul>
 * <li>it must be {@code public},</li>
 * <li>it must take two arguments: a {@link String} (the text matched by the
 * node) and a {@link List} of {@link ParseNode}s (its children nodes).</li>
 * </ul>
 *
 * <p>Furthermore, it will associate the found constructor with a rule label; it
 * is either the method name or, if the rule is further annotated with {@link
 * Label}, then the value of this annotation.</p>
 * <p>It is illegal for two rules to have the same label: should that happen,
 * an {@link IllegalStateException} is thrown.</p>
 *
 * <h2>Limitation</h2>
 *
 * <p>It is not possible, at runtime, to differentiate rules according to the
 * parser class. Which means that in this situation, both methods name {@code
 * theRule()} will be considered to have the same label, even though they are in
 * a different class:</p>
 *
 * <pre>
 *     // Sub parser class
 *     public class SubParser
 *         extends BaseParser&lt;Object&gt;
 *     {
 *         &#64;GenerateNode(SubNode.class)
 *         public Rule theRule()
 *         {
 *             // ...
 *         }
 *     }
 *
 *     // "Main" parser class
 *     public class SubParser
 *         extends BaseParser&lt;Object&gt;
 *     {
 *         // Our sub parser
 *         protected final SubParser sub = Grappa.createParser(SubParser.class);
 *
 *         // Error! This rule and the sub parser's have the same label!
 *         &#64;GenerateNode(MainNode.class)
 *         public Rule theRule()
 *         {
 *             // ...
 *         }
 *     }
 * </pre>
 *
 * @see ParseNode
 * @see ParseTreeBuilder#ParseTreeBuilder(ParseNodeConstructorProvider)
 */
public final class ParseNodeConstructorProvider {
	@VisibleForTesting
	static final String NO_CONSTRUCTOR
			= "no suitable constructor found for node class %s";

	@VisibleForTesting
	static final String DUPLICATE_NAME = "duplicate rule label %s";

	private final Map<String, Constructor<? extends ParseNode>> constructors
			= new HashMap<>();

	/**
	 * Constructor
	 *
	 * @param parserClass the parser class
	 */
	public ParseNodeConstructorProvider(final Class<? extends BaseParser<?>> parserClass) {

		Objects.requireNonNull(parserClass);

		final Set<Class<?>> classes = new HashSet<>();

		final Iterable<Class<? extends BaseParser<?>>> traversal
			= ParserTraverser.INSTANCE.preOrderTraversal(parserClass);

		for (final Class<? extends BaseParser<?>> c: traversal)
			if (classes.add(c))
                findConstructors(c);
	}

	private void findConstructors(final Class<? extends BaseParser<?>> parserClass)
	{
		Constructor<? extends ParseNode> constructor;
		String ruleName;

		for (final Method method : parserClass.getMethods()) {
			constructor = findConstructor(method);
			if (constructor == null)
				continue;
			ruleName = getRuleName(method);
			if (constructors.put(ruleName, constructor) != null)
				throw new IllegalStateException(String.format(DUPLICATE_NAME, ruleName));
		}
	}

	/**
	 * Get the constructor for a given parse node class.
	 *
	 * @param ruleName the name of the rule.
	 *
	 * @return the constructor; {@code null} if not found.
	 */
	Constructor<? extends ParseNode> getNodeConstructor(final String ruleName) {
		return constructors.get(ruleName);
	}

	private static String getRuleName(final Method method) {
		final Label ann = method.getAnnotation(Label.class);

		return ann != null ? ann.value() : method.getName();
	}

	// returns null if method is not a rule OR has no @GenerateNode annotation
	private static Constructor<? extends ParseNode> findConstructor(final Method method) {
		if (!Rule.class.isAssignableFrom(method.getReturnType()))
			return null;

		final GenerateNode ann = method.getAnnotation(GenerateNode.class);

		if (ann == null)
			return null;

		final Class<? extends ParseNode> nodeClass = ann.value();

		try {
			return nodeClass.getConstructor(MatchTextSupplier.class, List.class);
		}
		catch (NoSuchMethodException e) {
			final String msg = String.format(NO_CONSTRUCTOR, nodeClass.getSimpleName());
			throw new IllegalStateException(msg, e);
		}
	}
}
