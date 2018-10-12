package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.NUMBER;

public class SizeBlock extends Block {

    public SizeBlock() {
        super(NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return (long) getSolution().getSize();
    }
}
