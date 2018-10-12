package me.scottwalkerau.qapes.qap;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Test class for Instance.java
 */
public class InstanceTest {

    private static final String SYMMETRIC = "instances/testSymmetric.ins";
    private static final String SYMMETRIC_SPLIT = "testSymmetric.ins";
    private static final String ASYMMETRIC = "instances/testAsymmetric.ins";
    private static final String ASYMMETRIC_SPLIT = "testAsymmetric.ins";

    /**
     * Test a symmetric instance
     */
    @Test
    public void testSymmetric() throws FileNotFoundException {
        Instance instance = new Instance(SYMMETRIC);
        assertTrue("Instance is not symmetric", instance.isSymmetric());
        assertTrue("Distance is not symmetric", instance.getDistance().isSymmetric());
        assertTrue("Flow is not symmetric", instance.getFlow().isSymmetric());

        assertMetadata(instance, 4, 790, SYMMETRIC_SPLIT);
    }

    /**
     * Test an asymmetric instance
     */
    @Test
    public void testAsymmetric() throws FileNotFoundException {
        Instance instance = new Instance(ASYMMETRIC);
        assertFalse("Instance is symmetric", instance.isSymmetric());
        assertFalse("Distance is symmetric", instance.getDistance().isSymmetric());
        assertFalse("Flow is symmetric", instance.getFlow().isSymmetric());

        assertMetadata(instance, 3, 5, ASYMMETRIC_SPLIT);
    }

    /**
     * Helper method to assert values
     * @param instance The instance to compare everything to
     * @param size Expected size
     * @param bkv Expected BKV
     * @param fileName Expected filename
     */
    private void assertMetadata(Instance instance, int size, int bkv, String fileName) {
        assertEquals("Wrong size", size, instance.getSize());
        assertEquals("Wrong BKV", bkv, instance.getBkv());
        assertEquals("Wrong fileName", fileName, instance.getFileName());
        assertEquals("Wrong instanceName", fileName.split("\\.")[0], instance.getInsName());
    }

}