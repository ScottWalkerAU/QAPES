package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NotBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        Block T = BlockType.TRUE.getBlock();
        Block F = BlockType.FALSE.getBlock();
        test(true,  F);
        test(false, T);
    }

    private void test(boolean expected, Block a) {
        AlgorithmNode node = fromBlocks(BlockType.NOT, a);
        assertEquals(expected, node.evaluate(null));
    }
}