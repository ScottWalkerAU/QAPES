package me.scottwalkerau.qapes.block.blocks.qap.facility;

import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.qap.Pair;

import static me.scottwalkerau.qapes.block.ReturnType.FACILITY_PAIR;

public class FirstBadMoveBlock extends Block {

    public FirstBadMoveBlock() {
        super(FACILITY_PAIR);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        int size = getSolution().getSize();
        for (int facA = 0; facA < size; facA++) {
            for (int facB = facA + 1; facB < size; facB++) {
                int delta = getSolution().getSwapDelta(facA, facB);
                if (delta > 0) {
                    return new Pair(facA, facB);
                }
            }
        }

        // Return no move if all moves are bad
        return new Pair(0, 0);
    }
}
