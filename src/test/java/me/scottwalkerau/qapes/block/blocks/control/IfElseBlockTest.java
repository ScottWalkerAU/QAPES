package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IfElseBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        AlgorithmNode T = new AlgorithmNode(null, BlockType.TRUE.getBlock());
        AlgorithmNode F = new AlgorithmNode(null, BlockType.FALSE.getBlock());
        test(1, T, makeAdder(1), makeFail());
        test(2, F, makeFail(), makeAdder(2));
    }

    private void test(long expected, AlgorithmNode a, AlgorithmNode b, AlgorithmNode c) {
        monitor = 0;
        AlgorithmNode node = fromNodes(BlockType.IF_ELSE, a, b, c);
        node.evaluate(null);
        assertEquals(expected, monitor);
    }
}