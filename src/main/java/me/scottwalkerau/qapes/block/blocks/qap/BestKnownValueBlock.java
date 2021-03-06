package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.NUMBER;

/**
 * Returns the BKV of the instance
 * @author Scott Walker
 */
public class BestKnownValueBlock extends Block {

    public BestKnownValueBlock() {
        super(NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return (long) getSolution().getInstance().getBkv();
    }
}
