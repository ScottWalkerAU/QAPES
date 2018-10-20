package me.scottwalkerau.qapes.block;

import org.junit.Test;

import static org.junit.Assert.*;

public class BlockCatalogueTest {

    @Test
    public void testCatalogue() {
        assertEquals(ReturnType.VOID, BlockCatalogue.getRandomBlock(ReturnType.VOID).getReturnType());
        assertEquals(ReturnType.NUMBER, BlockCatalogue.getRandomBlock(ReturnType.NUMBER).getReturnType());

        assertTrue(BlockCatalogue.getRandomBlockChildless(ReturnType.NUMBER).isTerminal());
    }
}