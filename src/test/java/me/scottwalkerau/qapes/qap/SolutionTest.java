package me.scottwalkerau.qapes.qap;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class SolutionTest {

    private static Instance instanceA;
    private static Instance instanceB;
    private static Instance instanceC;

    @BeforeClass
    public static void setup() throws FileNotFoundException {
        instanceA = new Instance("instances/chr25a.ins");
        instanceB = new Instance("instances/testSymmetric.ins");
        instanceC = new Instance("instances/testAsymmetric.ins");
    }

    @Test
    public void testEqualSwaps() {
        Solution solution = new Solution(instanceA);
        final long fitness = solution.getFitness();

        solution.swapFacilities(4, 8);
        solution.swapFacilities(4, 8);
        assertEquals("Should not change", fitness, solution.getFitness());

        solution.swapFacilities(7, 7);
        assertEquals("Should not change", fitness, solution.getFitness());
    }

    @Test
    public void testSwaps() {
        Solution solution = new Solution(instanceB);
        solution.setFacilityLocations(getBestKnownSolutionB());
        assertEquals("Fitness not equal", 790, solution.getFitness());

        // Three swaps
        swapAndAssert(solution, 0, 1, 834);
        swapAndAssert(solution, 1, 2, 930);
        swapAndAssert(solution, 2, 3, 964);

        // Undo those swaps
        swapAndAssert(solution, 2, 3, 930);
        swapAndAssert(solution, 1, 2, 834);
        swapAndAssert(solution, 0, 1, 790);
    }

    @Test
    public void testDeltas() {
        Solution solution = new Solution(instanceB);
        solution.setFacilityLocations(getBestKnownSolutionB());

        assertEquals("Delta not equal", 0, solution.getSwapDelta(0, 0));
        assertEquals("Delta not equal", 44, solution.getSwapDelta(0, 1));
        assertEquals("Delta not equal", 228, solution.getSwapDelta(1, 2));
        assertEquals("Delta not equal", 34, solution.getSwapDelta(2, 3));

        swapAndAssert(solution, 0, 1, instanceB.getBkv() + 44);
        swapAndAssert(solution, 0, 1, instanceB.getBkv());
        swapAndAssert(solution, 0, 1, instanceB.getBkv() + 44);
        assertEquals("Delta not equal", 0, solution.getSwapDelta(0, 0));
        assertEquals("Delta not equal", -44, solution.getSwapDelta(0, 1));
        assertEquals("Delta not equal", 96, solution.getSwapDelta(1, 2));
        assertEquals("Delta not equal", -10, solution.getSwapDelta(2, 3));
    }

    @Test
    public void testDeltasAsymmetric() {
        Solution solution = new Solution(instanceC);
        solution.setFacilityLocations(getBestKnownSolutionC());

        assertEquals("Delta not equal", 0, solution.getSwapDelta(0, 0));
        assertEquals("Delta not equal", 10, solution.getSwapDelta(0, 1));
        assertEquals("Delta not equal", 0, solution.getSwapDelta(1, 2));
        assertEquals("Delta not equal", 15, solution.getSwapDelta(0, 2));

        swapAndAssert(solution, 0, 1, instanceC.getBkv() + 10);
        swapAndAssert(solution, 0, 1, instanceC.getBkv());
        swapAndAssert(solution, 0, 1, instanceC.getBkv() + 10);
        assertEquals("Delta not equal", 0, solution.getSwapDelta(0, 0));
        assertEquals("Delta not equal", -10, solution.getSwapDelta(0, 1));
        assertEquals("Delta not equal", -10, solution.getSwapDelta(1, 2));
        assertEquals("Delta not equal", 5, solution.getSwapDelta(0, 2));
    }

    @Test
    public void testDeltaComputation() {
        Solution solution = new Solution(instanceA);
        solution.setFacilityLocations(getBestKnownSolutionA());

        Random random = new Random();
        for (int i = 0; i < 1000; i++) { // Swap lots in the first half, then perform a swap in the second
            int swapA = random.nextInt(solution.getSize() / 2);
            int swapB = random.nextInt(solution.getSize() / 2);
            solution.swapFacilities(swapA, swapB);
        }

        int swapA = random.nextInt(solution.getSize() / 2) + solution.getSize() / 2;
        int swapB = random.nextInt(solution.getSize() / 2) + solution.getSize() / 2;
        solution.swapFacilities(swapA, swapB);
        long fit = solution.getFitness();
        assertEquals(fit, solution.calculateFitness());
    }

    @Test
    public void testBestKnownSolution() {
        testBKS(instanceA, instanceA.getSize(), getBestKnownSolutionA());
        testBKS(instanceB, instanceB.getSize(), getBestKnownSolutionB());
    }

    private void testBKS(Instance instance, int size, List<Integer> bks) {
        Solution solution = new Solution(instance);
        solution.setFacilityLocations(bks);

        assertEquals("Instances should be the same object", instance, solution.getInstance());
        assertEquals("Size is wrong", size, solution.getSize());
        assertEquals("Should be BKV", instance.getBkv(), solution.getFitness());
        for (int fac = 0; fac < solution.getSize(); fac++) {
            assertEquals("Locations should match", bks.get(fac), solution.getLocation(fac));
        }
    }

    private void swapAndAssert(Solution solution, int facA, int facB, long fitness) {
        solution.swapFacilities(facA, facB);
        assertEquals("Fitness not equal", fitness, solution.getFitness());
    }

    private List<Integer> getBestKnownSolutionA() {
        return Arrays.asList(24,11,4,2,17,3,15,7,19,9,13,5,14,22,23,18,12,0,20,10,16,1,21,6,8);
    }

    private List<Integer> getBestKnownSolutionB() {
        return Arrays.asList(2,3,0,1);
    }

    private List<Integer> getBestKnownSolutionC() {
        return Arrays.asList(0,1,2);
    }
}