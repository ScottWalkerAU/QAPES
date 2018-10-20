package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.ReturnType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WhileBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        AlgorithmNode F = new AlgorithmNode(null, BlockType.FALSE.getBlock());
        test(10, loopNode(), makeAdder(1));
        test(0, F, makeFail());
    }

    private void test(long expected, AlgorithmNode a, AlgorithmNode b) {
        monitor = 0;
        AlgorithmNode node = fromNodes(BlockType.WHILE, a, b);
        node.evaluate(null);
        assertEquals(expected, monitor);
    }

    private AlgorithmNode loopNode() {
        Block block = new Block(ReturnType.BOOLEAN) {
            @Override
            protected Object evaluate() {
                return monitor < 10;
            }
        };
        return new AlgorithmNode(null, block);
    }
}