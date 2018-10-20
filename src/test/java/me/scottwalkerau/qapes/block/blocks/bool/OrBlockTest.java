package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        Block T = BlockType.TRUE.getBlock();
        Block F = BlockType.FALSE.getBlock();
        test(true,  T, T);
        test(true,  T, F);
        test(true,  F, T);
        test(false, F, F);
    }

    private void test(boolean expected, Block a, Block b) {
        AlgorithmNode node = fromBlocks(BlockType.OR, a, b);
        assertEquals(expected, node.evaluate(null));
    }
}