package me.scottwalkerau.qapes.population;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeNodeTest {

    private final int branches = 2;
    private final int depth = 1;

    @Test
    public void testParentsAndChildren() {
        TreeNode<Integer> node = new TreeNode<>(branches, depth);
        assertNull(node.getParent());
        assertEquals(node, node.getChildren().get(0).getParent());
        assertEquals(branches, node.getChildren().size());
    }

    @Test
    public void testData() {
        TreeNode<Integer> node = new TreeNode<>(branches, depth);

        assertNull(node.getData());
        node.setData(10);
        assertEquals(10, (int) node.getData());

        TreeNode<Integer> childA = node.getChildren().get(0);
        childA.setData(5);
        node.swapData(childA);
        assertEquals(5, (int) node.getData());
        assertEquals(10, (int) childA.getData());
    }
}