package me.scottwalkerau.qapes.block.blocks;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.VOID;

/**
 * A block that does absolutely nothing. It's used for tree minimisation
 * @author Scott Walker
 */
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
