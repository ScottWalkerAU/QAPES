package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

/**
 * A boolean TRUE
 * @author Scott Walker
 */
public class TrueBlock extends Block {

    public TrueBlock() {
        super(BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return true;
    }
}
