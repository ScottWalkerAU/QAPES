package me.scottwalkerau.qapes.qap;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Test class for Pair.java
 */
public class PairTest {

    /**
     * Test that the set values match the retrieved values.
     */
    @Test
    public void testPair() {
        // Data isn't malformed upon object creation
        int a = new Random().nextInt();
        int b = new Random().nextInt();
        Pair pair = new Pair(a, b);
        assertEquals(a, pair.getA());
        assertEquals(b, pair.getB());

        Pair pair2 = new Pair(a, b);
        // Not the same object despite having same internal data
        assertNotSame(pair, pair2);
        assertEquals(pair.getA(), pair2.getA());
        assertEquals(pair.getB(), pair2.getB());
    }

}