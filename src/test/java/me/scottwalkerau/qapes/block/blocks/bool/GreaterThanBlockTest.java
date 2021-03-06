package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GreaterThanBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        AlgorithmNode A = makeConstant(20);
        AlgorithmNode B = makeConstant(10);
        AlgorithmNode C = makeConstant(20);
        test(true,  A, B);
        test(false, B, A);
        test(false, A, C);
        test(false, C, A);
    }

    private void test(boolean expected, AlgorithmNode a, AlgorithmNode b) {
        AlgorithmNode node = fromNodes(BlockType.GREATER_THAN, a, b);
        assertEquals(expected, node.evaluate(null));
    }
}