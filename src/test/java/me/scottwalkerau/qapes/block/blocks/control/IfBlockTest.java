package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import org.junit.Test;

import static org.junit.Assert.*;

public class IfBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        AlgorithmNode T = new AlgorithmNode(null, BlockType.TRUE.getBlock());
        AlgorithmNode F = new AlgorithmNode(null, BlockType.FALSE.getBlock());
        test(1, T, makeAdder(1));
        test(0, F, makeFail());
    }

    private void test(long expected, AlgorithmNode a, AlgorithmNode b) {
        monitor = 0;
        AlgorithmNode node = fromNodes(BlockType.IF, a, b);
        node.evaluate(null);
        assertEquals(expected, monitor);
    }
}