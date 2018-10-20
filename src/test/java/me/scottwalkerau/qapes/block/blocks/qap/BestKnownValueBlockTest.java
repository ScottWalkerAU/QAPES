package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.qap.Instance;
import me.scottwalkerau.qapes.qap.Solution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BestKnownValueBlockTest extends BlockTestBase {

    @Mock private Instance instance;
    @Mock private Solution solution;

    @Test
    public void evaluate() {
        when(instance.getBkv()).thenReturn(256L);
        when(solution.getInstance()).thenReturn(instance);
        AlgorithmNode node = fromBlocks(BlockType.BEST_KNOWN_VALUE);
        assertEquals(256L, node.evaluate(solution));
    }
}