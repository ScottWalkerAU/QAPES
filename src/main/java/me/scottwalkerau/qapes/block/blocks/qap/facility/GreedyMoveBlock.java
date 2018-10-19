package me.scottwalkerau.qapes.block.blocks.qap.facility;

import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.qap.Pair;

import static me.scottwalkerau.qapes.block.ReturnType.FACILITY_PAIR;

/**
 * Finds the best move given the current configuration
 * @author Scott Walker
 */
public class GreedyMoveBlock extends Block {

    public GreedyMoveBlock() {
        super(FACILITY_PAIR);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        int size = getSolution().getSize();
        Pair pair = null;
        int bestDelta = 0;
        for (int facA = 0; facA < size; facA++) {
            for (int facB = facA + 1; facB < size; facB++) {
                int delta = getSolution().getSwapDelta(facA, facB);
                if (delta < bestDelta) {
                    pair = new Pair(facA, facB);
                    bestDelta = delta;
                }
            }
        }

        // Return best, or no move if all moves are bad
        return pair == null ? new Pair(0, 0) : pair;
    }
}
