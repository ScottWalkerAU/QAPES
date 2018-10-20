package me.scottwalkerau.qapes.algorithm;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResultSetTest {

    private ResultSet resultSet;

    @Mock
    private AlgorithmTree tree;

    @Mock
    private Result resultA;

    @Mock
    private Result resultB;

    @Mock
    private Result resultC;

    @Before
    public void setUp() {
        resultSet = new ResultSet(tree);
        when(resultA.getFitness()).thenReturn(10.0);
        when(resultB.getFitness()).thenReturn(20.0);
        when(resultC.getFitness()).thenReturn(30.0);
        when(resultA.getGap()).thenReturn(1.0);
        when(resultB.getGap()).thenReturn(1.1);
        when(resultC.getGap()).thenReturn(1.2);
    }

    @Test
    public void testGetters() {
        final double delta = 0.001;
        assertSame("Tree should be same object", tree, resultSet.getTree());
        assertEquals("Sizes should be 0", 0, resultSet.getSize());
        assertNull("No fitness yet", resultSet.getMeanFitness());

        resultSet.addResult(resultA);
        assertEquals("Sizes should be 1", 1, resultSet.getSize());
        assertEquals("Mean fitness did not equal", 10.0, resultSet.getMeanFitness(), delta);
        assertEquals("Gap should be equal", 1.0, resultSet.getMeanGap(), delta);


        resultSet.addResult(resultB);
        assertEquals("Sizes should be 2", 2, resultSet.getSize());
        assertEquals("Mean fitness did not equal", 15.0, resultSet.getMeanFitness(), delta);
        assertEquals("Gap should be equal", 1.05, resultSet.getMeanGap(), delta);


        resultSet.addResult(resultC);
        assertEquals("Sizes should be 3", 3, resultSet.getSize());
        assertEquals("Mean fitness did not equal", 20.0, resultSet.getMeanFitness(), delta);
        assertEquals("Gap should be equal", 1.1, resultSet.getMeanGap(), delta);
    }

    @Test
    public void testCompareTo() {
        ResultSet other = new ResultSet(tree);
        resultSet.addResult(resultA);
        other.addResult(resultB);
        other.addResult(resultC);

        assertTrue("resultSet should have a lower fitness", resultSet.compareTo(other) < 0);
        assertTrue("other should have a higher fitness", other.compareTo(resultSet) > 0);
    }
}