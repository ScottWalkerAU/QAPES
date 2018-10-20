package me.scottwalkerau.qapes.algorithm;

import org.junit.Test;

import static me.scottwalkerau.qapes.block.BlockType.*;
import static org.junit.Assert.assertEquals;

public class TreeMinimiserTest {

    @Test
    public void testMinimisation() {
        AlgorithmNode root = new AlgorithmNode(null, WHILE.getBlock());
        AlgorithmNode block = root;

        block.setChild(0, NOT.getBlock());
        block = block.getChild(0);
        block.setChild(0, NOT.getBlock());
        block = block.getChild(0);
        block.setChild(0, LESS_THAN.getBlock());
        block = block.getChild(0);
        block.setChild(0, FITNESS.getBlock());
        block.setChild(1, FITNESS.getBlock());

        block = root;
        block.setChild(1, SWAP_FACILITY.getBlock());
        block = block.getChild(1);
        block.setChild(0, RANDOM_FACILITY_PAIR.getBlock());

        AlgorithmTree tree = new AlgorithmTree(root);
        assertEquals(root, tree.getRoot());
        assertEquals(8, tree.getAllNodes().size());

        tree = new TreeMinimiser(tree).run();

        assertEquals(1, tree.getAllNodes().size());
    }
}