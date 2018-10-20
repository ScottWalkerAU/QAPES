package me.scottwalkerau.qapes;

import org.junit.Test;

import static org.junit.Assert.*;

public class QAPESTest {

    @Test
    public void testBadPopulation() {
        String arg = "-i chr25a -p error";
        String[] args = arg.split(" ");

        try {
            QAPES.main(args);
            fail("Should be an illegal argument");
        } catch (IllegalArgumentException e) {}
    }
}