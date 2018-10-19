package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;
import static me.scottwalkerau.qapes.block.ReturnType.NUMBER;

/**
 * Compare the first block is greater than the second
 * @author Scott Walker
 */
public class GreaterThanBlock extends Block {

    public GreaterThanBlock() {
        super(BOOLEAN, NUMBER, NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        long valA = evalLong(0);
        long valB = evalLong(1);
        return valA > valB;
    }
}
