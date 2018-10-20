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
public class FitnessBlockTest extends BlockTestBase {

    @Mock private Solution solution;
    
    @Test
    public void evaluate() {
        when(solution.getFitness()).thenReturn(256L);
        AlgorithmNode node = fromBlocks(BlockType.FITNESS);
        assertEquals(256L, node.evaluate(solution));
    }
}