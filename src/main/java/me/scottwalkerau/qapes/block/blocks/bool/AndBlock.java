package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

public class AndBlock extends Block {

    public AndBlock() {
        super(BOOLEAN, BOOLEAN, BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return evalBoolean(0) && evalBoolean(1);
    }
}
