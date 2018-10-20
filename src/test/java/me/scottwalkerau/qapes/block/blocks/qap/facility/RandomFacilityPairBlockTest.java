package me.scottwalkerau.qapes.block.blocks.qap.facility;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.qap.Pair;
import me.scottwalkerau.qapes.qap.Solution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RandomFacilityPairBlockTest extends BlockTestBase {

    @Mock
    private Solution solution;

    @Test
    public void evaluate() {
        when(solution.getSize()).thenReturn(5);
        AlgorithmNode node = fromBlocks(BlockType.RANDOM_FACILITY_PAIR);
        Pair pair = (Pair) node.evaluate(solution);
        assertTrue(pair.getA() >= 0);
        assertTrue(pair.getB() >= 0);
        assertTrue(pair.getA() < 5);
        assertTrue(pair.getB() < 5);
    }
}