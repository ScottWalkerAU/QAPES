package me.scottwalkerau.qapes.block.blocks;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.VOID;

/**
 * A simple list for running two statements after each other
 * @author Scott Walker
 */
public class VoidListBlock extends Block {

    public VoidListBlock() {
        super(VOID, VOID, VOID);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        evalVoid(0);
        evalVoid(1);
        return null;
    }
}
