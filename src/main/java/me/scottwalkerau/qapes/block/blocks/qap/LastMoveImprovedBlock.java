package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

/**
 * Returns if the last move improved the fitness. If not moves have occurred it defaults to true
 * @author Scott Walker
 */
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
