package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrueBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        AlgorithmNode node = fromNodes(BlockType.TRUE);
        assertEquals(true, node.evaluate(null));
    }
}