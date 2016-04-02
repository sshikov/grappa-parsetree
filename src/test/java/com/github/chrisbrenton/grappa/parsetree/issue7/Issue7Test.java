package com.github.chrisbrenton.grappa.parsetree.issue7;

import com.github.chrisbrenton.grappa.parsetree.api.ParseTree;
import com.github.chrisbrenton.grappa.parsetree.node.ParseNode;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class Issue7Test
{
    private static final String INPUT = "ac";

    @Test
    public void nodesFromFailedSequencesAreDiscarded()
    {
        final ParseTree<Root> tree = ParseTree.usingParser(Issue7Parser.class)
            .withRule(Issue7Parser::input)
            .withRoot(Root.class);

        final Root root = tree.parse(INPUT);
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
}