package me.scottwalkerau.qapes.block.blocks;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.VOID;

public class VoidBlock extends Block {

    public VoidBlock() {
        super(VOID);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return null;
    }
}
