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
public class LastMoveImprovedBlockTest extends BlockTestBase {

    @Mock private Solution solution;

    @Test
    public void evaluate() {
        AlgorithmNode node = fromBlocks(BlockType.LAST_MOVE_IMPROVED);

        when(solution.isImproved()).thenReturn(true);
        assertEquals(true, node.evaluate(solution));

        when(solution.isImproved()).thenReturn(false);
        assertEquals(false, node.evaluate(solution));
    }
}