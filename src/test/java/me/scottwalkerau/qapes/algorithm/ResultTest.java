package me.scottwalkerau.qapes.algorithm;

import me.scottwalkerau.qapes.qap.Instance;
import me.scottwalkerau.qapes.qap.Solution;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultTest {

    private static final Random RANDOM = new Random();
    private static final long BKV = 64;

    @Mock
    private AlgorithmTree tree;

    @Mock
    private Instance instance;

    @Mock
    private Solution solution;

    @Before
    public void setUp() {
        when(instance.getBkv()).thenReturn(BKV);

        when(solution.getInstance()).thenReturn(instance);
        when(solution.getFitness()).thenReturn(256L);
        when(solution.getGap()).thenReturn(4.0);

        assertEquals(256, solution.getFitness());
        assertEquals(BKV, solution.getInstance().getBkv());
        assertEquals(4.0, solution.getGap(), 0.00001);
    }

    @Test
    public void testsMembers() {
        int initialFitness = RANDOM.nextInt(512) + 512;
        long timeTaken = RANDOM.nextLong();
        Status status = Status.EXPIRED;
        Result result = new Result(tree, solution, initialFitness, status, timeTaken);

        assertSame("Tree should be same object", tree, result.getTree());
        assertSame("Solution should be same object", solution, result.getSolution());
        assertEquals("Initial fitness should equal", initialFitness, result.getInitialFitness());
        assertEquals("Status should match", status, result.getStatus());
        assertEquals("Time taken should match", timeTaken, result.getTimeTaken());
        assertEquals("Gap should be equal", 4.0, result.getGap(), 0.00001);
    }

    @Test
    public void testCompareTo() {
        Result result = new Result(tree, solution, 512, Status.COMPLETED, 1000);

        double fitness = result.getFitness() + RANDOM.nextInt(256) + 1;
        Result other = mock(Result.class);
        when(other.getFitness()).thenReturn(fitness);

        assertTrue("Result should have a better fitness", result.compareTo(other) < 0);
    }

}