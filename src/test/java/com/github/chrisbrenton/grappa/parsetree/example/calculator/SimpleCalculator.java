package com.github.chrisbrenton.grappa.parsetree.example.calculator;

import com.github.chrisbrenton.grappa.parsetree.api.ParseTree;
import com.github.fge.grappa.exceptions.GrappaException;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * This is an example of how you might use grappa-parsetree
 */
public final class SimpleCalculator
{
    private static final String MESSAGE
        = "Enter an expression (q to quit):";

	/* Do not attempt to create an instance of this class! */
	private SimpleCalculator()
    {
		throw new Error("no instantiation is permitted");
	}

	/* The main method! */
	public static void main(final String... args) {
		final ParseTree<ExpressionNode> parseTree = ParseTree
			.usingParser(SimpleExpressionParser.class)
			.withRule(SimpleExpressionParser::expression)
			.withRoot(ExpressionNode.class);

        final Scanner scanner = new Scanner(System.in);

        String line;
        ExpressionNode node;
        BigDecimal result;

        while (true) {
            System.out.println(MESSAGE);
            line = scanner.nextLine().trim();
            if ("q".equals(line))
                break;
            try {
                node = parseTree.parse(line);
            } catch (GrappaException ignored) {
                System.out.println("Parsing failure :(");
                continue;
            }
            try {
                result = node.get();
            } catch (ArithmeticException e) {
                System.out.println("ERROR: " + e.getMessage());
                continue;
            }
            System.out.printf("Result: %s%n", result);
        }

        System.out.println("Goodbye!");
	}
}
