package me.scottwalkerau.qapes.block.blocks.bool;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

/**
 * A boolean FALSE
 * @author Scott Walker
 */
public class FalseBlock extends Block {

    public FalseBlock() {
        super(BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return false;
    }
}
