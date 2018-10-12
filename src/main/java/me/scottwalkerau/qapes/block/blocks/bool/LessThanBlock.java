package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;
import static me.scottwalkerau.qapes.block.ReturnType.NUMBER;

public class LessThanBlock extends Block {

    public LessThanBlock() {
        super(BOOLEAN, NUMBER, NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        long valA = evalLong(0);
        long valB = evalLong(1);
        return valA < valB;
    }
}
