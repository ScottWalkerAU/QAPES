package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.qap.Solution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SizeBlockTest extends BlockTestBase {

    @Mock private Solution solution;

    @Test
    public void evaluate() {
        when(solution.getSize()).thenReturn(256);
        AlgorithmNode node = fromBlocks(BlockType.SIZE);
        assertEquals(256L, node.evaluate(solution));
    }
}