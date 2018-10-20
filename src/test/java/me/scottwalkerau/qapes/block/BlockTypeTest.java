package me.scottwalkerau.qapes.block;

import me.scottwalkerau.qapes.block.blocks.control.IfBlock;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockTypeTest {

    @Test
    public void test() {
        assertFalse(BlockType.VOID.isCatalogued());
        assertTrue(BlockType.FITNESS.isCatalogued());
        assertTrue(BlockType.IF.getBlock() instanceof IfBlock);
    }

}