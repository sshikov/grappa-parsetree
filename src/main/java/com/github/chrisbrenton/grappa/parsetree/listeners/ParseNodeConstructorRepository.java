package com.github.chrisbrenton.grappa.parsetree.listeners;

import com.github.chrisbrenton.grappa.parsetree.annotations.GenerateNode;
import com.github.chrisbrenton.grappa.parsetree.nodes.ParseNode;
import com.github.fge.grappa.annotations.Label;
import com.github.fge.grappa.parsers.BaseParser;
import com.github.fge.grappa.rules.Rule;
import com.google.common.annotations.VisibleForTesting;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A class to collect all constructors for parse nodes defined in a parser
 * <p>This class takes a parser class as an argument and will look up all the
 * rules (methods returning a {@link Rule}. If a rule is further annotated with
 * {@link GenerateNode}, it will take the argument to this annotation and find
 * a suitable constructor.</p>
 * <p>The constructor must obey two criteria:</p>
 * <ul>
 * <li>it must be {@code public},</li>
 * <li>it must take two arguments: a {@link String} (the text matched by the
 * node) and a {@link List} of {@link ParseNode}s (its children nodes).</li>
 * </ul>
 * <p>Furthermore, it will associate the found constructor with a rule label; it
 * is either the method name or, if the rule is further annotated with {@link
 * Label}, then the value of this annotation.</p>
 * <p>It is illegal for two rules to have the same label: should that happen,
 * an {@link IllegalStateException} is thrown.</p>
 *
 * @see ParseNode
 * @see ParseTreeListener#ParseTreeListener(ParseNodeConstructorRepository)
 */
public final class ParseNodeConstructorRepository {
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
	public ParseNodeConstructorRepository(final Class<? extends BaseParser<?>> parserClass) {

		Objects.requireNonNull(parserClass);

		Constructor<? extends ParseNode> constructor;
		String ruleName;

		for (final Method method : parserClass.getMethods()) {
			constructor = getNodeConstructor(method);
			if (constructor == null)
				continue;
			ruleName = getRuleName(method);
			if (constructors.put(ruleName, constructor) != null)
				throw new IllegalStateException(String.format(DUPLICATE_NAME, ruleName));
		}
	}

	/**
	 * Get the constructor for a given parse node class
	 *
	 * @param ruleName the name of the rule
	 *
	 * @return the constructor; {@code null} if not found
	 */
	public Constructor<? extends ParseNode> getNodeConstructor(final String ruleName) {
		return constructors.get(ruleName);
	}

	private static String getRuleName(final Method method) {
		final Label ann = method.getAnnotation(Label.class);

		return ann != null ? ann.value() : method.getName();
	}

	// returns null if method is not a rule OR has no @GenerateNode annotation
	private static Constructor<? extends ParseNode> getNodeConstructor(final Method method) {
		if (!Rule.class.isAssignableFrom(method.getReturnType()))
			return null;

		final GenerateNode ann = method.getAnnotation(GenerateNode.class);

		if (ann == null)
			return null;

		final Class<? extends ParseNode> nodeClass = ann.value();

		try {
			return nodeClass.getConstructor(String.class, List.class);
		}
		catch (NoSuchMethodException e) {
			final String msg = String.format(NO_CONSTRUCTOR, nodeClass.getSimpleName());
			throw new IllegalStateException(msg, e);
		}
	}
}
