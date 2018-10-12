package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

public class OrBlock extends Block {

    public OrBlock() {
        super(BOOLEAN, BOOLEAN, BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return evalBoolean(0) || evalBoolean(1);
    }
}
