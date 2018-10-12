package me.scottwalkerau.qapes.qap;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Test class for SquareMatrix.java
 */
public class SquareMatrixTest {

    /**
     * Test case where symmetry should exist for a fixed size
     */
    @Test
    public void testIsSymmetricTrue() {
        SquareMatrix matrix = new SquareMatrix(2);
        matrix.set(0, 0, 0);
        matrix.set(0, 1, 1);
        matrix.set(1, 0, 1);
        matrix.set(1, 1, 0);

        validateSize(2, matrix);
        assertTrue("Matrix is not symmetric.", matrix.isSymmetric());
    }

    /**
     * Test case where symmetry should not exist for a fixed size
     */
    @Test
    public void testIsSymmetricFalse() {
        SquareMatrix matrix = new SquareMatrix(2);
        matrix.set(0, 0, 0);
        matrix.set(0, 1, 2);
        matrix.set(1, 0, 1);
        matrix.set(1, 1, 0);

        validateSize(2, matrix);
        assertFalse("Matrix is symmetric.", matrix.isSymmetric());
    }

    /**
     * Test case to check if values are being set and cannot be overridden
     */
    @Test
    public void testGetSet() {
        SquareMatrix matrix = new SquareMatrix(1);
        Integer value;

        validateSize(1, matrix);
        assertNull("Value should be null.", matrix.get(0, 0));

        value = new Random().nextInt();
        matrix.set(0, 0, value);
        assertEquals("Values do not equal.", value, matrix.get(0, 0));

        value = new Random().nextInt();
        matrix.set(0, 0, value);
        assertEquals("Value should be updated.", value, matrix.get(0, 0));
    }

    /**
     * Test case to check arbitrary sizes
     */
    @Test
    public void testSize() {
        // Test 8 random cases
        for (int i = 0; i < 8; i++) {
            Integer size = new Random().nextInt(1024);
            SquareMatrix matrix = new SquareMatrix(size);
            validateSize(size, matrix);
        }
    }

    /**
     * Helper to assert the size of a matrix
     * @param expected Expected size
     * @param matrix   The matrix to test
     */
    private void validateSize(int expected, SquareMatrix matrix) {
        assertFalse("Size cannot be negative.", expected < 0);
        assertEquals("Sizes do not match.", expected, matrix.getSize());
    }
}
