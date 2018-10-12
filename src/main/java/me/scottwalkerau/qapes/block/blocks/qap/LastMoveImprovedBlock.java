package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

public class LastMoveImprovedBlock extends Block {

    public LastMoveImprovedBlock() {
        super(BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return getSolution().isImproved();
    }
}
