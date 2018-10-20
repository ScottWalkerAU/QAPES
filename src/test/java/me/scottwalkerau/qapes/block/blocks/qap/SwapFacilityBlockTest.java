package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.block.ReturnType;
import me.scottwalkerau.qapes.block.blocks.BlockTestBase;
import me.scottwalkerau.qapes.qap.Instance;
import me.scottwalkerau.qapes.qap.Pair;
import me.scottwalkerau.qapes.qap.Solution;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

public class SwapFacilityBlockTest extends BlockTestBase {

    @Test
    public void evaluate() {
        try {
            Instance instance = new Instance("instances/chr25a.ins");
            AlgorithmNode swap = new AlgorithmNode(null, new Block(ReturnType.FACILITY_PAIR) {
                @Override
                protected Object evaluate() {
                    return new Pair(0, 1);
                }
            });
            AlgorithmNode node = fromNodes(BlockType.SWAP_FACILITY, swap);

            Solution solution = new Solution(instance);
            int loc = solution.getLocation(0);
            node.evaluate(solution);
            assertNotEquals(loc, (int) solution.getLocation(0));
            node.evaluate(solution);
            assertEquals(loc, (int) solution.getLocation(0));

        } catch (FileNotFoundException e) {
            fail();
        }
    }
}