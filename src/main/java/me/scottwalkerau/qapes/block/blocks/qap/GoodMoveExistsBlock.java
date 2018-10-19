package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;

/**
 * Returns if a good move is possible in the current state
 * @author Scott Walker
 */
public class GoodMoveExistsBlock extends Block {

    public GoodMoveExistsBlock() {
        super(BOOLEAN);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        int size = getSolution().getSize();
        for (int facA = 0; facA < size; facA++) {
            for (int facB = facA + 1; facB < size; facB++) {
                int delta = getSolution().getSwapDelta(facA, facB);
                if (delta < 0) {
                    return true;
                }
            }
        }
        // Didn't return from the loop
        return false;
    }
}
