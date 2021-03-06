package com.github.chrisbrenton.grappa.parsetree.issue7;

import com.github.chrisbrenton.grappa.parsetree.api.ParseTree;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class Issue7Test
{
    @Test
    public void nodesFromFailedSequencesAreDiscarded()
    {
        final ParseTree<Root> tree = ParseTree.usingParser(Issue7Parser.class)
            .withRule(Issue7Parser::input)
            .withRoot(Root.class);

        final Root root = tree.parse("ac");
        assertThat(root.getMatchedText()).isEqualTo("ac");

        final List<ParseNode> children = root.getChildren();
        ParseNode node;

        assertThat(children).hasSize(2);

        node = children.get(0);
        assertThat(node).isExactlyInstanceOf(ANode.class);
        assertThat(node.getMatchedText()).isEqualTo("a");

        node = children.get(1);
        assertThat(node).isExactlyInstanceOf(AfterA.class);
        assertThat(node.getMatchedText()).isEqualTo("c");
    }

    @Test
    public void extraJoiningNodesAreDiscarded()
    {
        final ParseTree<Root> tree = ParseTree.usingParser(Issue7Parser.class)
            .withRule(Issue7Parser::input2)
            .withRoot(Root.class);

        final Root root = tree.parse("abab");

        List<ParseNode> children;
        ParseNode node;

        children = root.getChildren();
        assertThat(children).hasSize(2);

        node = children.get(0);
        assertThat(node).isExactlyInstanceOf(Root.class);

        children = node.getChildren();
        assertThat(children).hasSize(3);

        node = children.get(0);
        assertThat(node).isExactlyInstanceOf(ANode.class);
        assertThat(node.getMatchedText()).isEqualTo("a");

        node = children.get(1);
        assertThat(node).isExactlyInstanceOf(AfterA.class);
        assertThat(node.getMatchedText()).isEqualTo("b");

        node = children.get(2);
        assertThat(node).isExactlyInstanceOf(ANode.class);
        assertThat(node.getMatchedText()).isEqualTo("a");

        children = root.getChildren();
        node = children.get(1);
        assertThat(node).isExactlyInstanceOf(AfterA.class);
        assertThat(node.getMatchedText()).isEqualTo("b");
    }

    @Test
    public void emptyNodesAreAllIncluded()
    {
        final ParseTree<Root> tree = ParseTree.usingParser(Issue7Parser.class)
            .withRule(Issue7Parser::emptyInput)
            .withRoot(Root.class);

        final Root root = tree.parse("");

        final List<ParseNode> children = root.getChildren();
        ParseNode node;

        assertThat(children).hasSize(2);

        node = children.get(0);
        assertThat(node).isExactlyInstanceOf(EmptyNode.class);
        assertThat(node.getMatchedText()).isEqualTo("");

        node = children.get(1);
        assertThat(node).isExactlyInstanceOf(EmptyNode.class);
        assertThat(node.getMatchedText()).isEqualTo("");
    }
}
