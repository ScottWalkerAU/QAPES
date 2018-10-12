package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

public class NotBlock extends Block {

    public NotBlock() {
        super(BOOLEAN, BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        boolean bool = evalBoolean(0);
        return !bool;
    }
}
