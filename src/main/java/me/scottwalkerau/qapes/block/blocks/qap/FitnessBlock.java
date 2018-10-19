package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.NUMBER;

/**
 * Returns the current solution fitness
 * @author Scott Walker
 */
public class FitnessBlock extends Block {

    public FitnessBlock() {
        super(NUMBER);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return (long) getSolution().getFitness();
    }
}
