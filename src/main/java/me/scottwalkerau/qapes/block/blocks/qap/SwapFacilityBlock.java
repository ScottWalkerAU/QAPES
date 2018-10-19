package me.scottwalkerau.qapes.block.blocks.qap;

import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.qap.Pair;

import static me.scottwalkerau.qapes.block.ReturnType.FACILITY_PAIR;
import static me.scottwalkerau.qapes.block.ReturnType.VOID;

/**
 * Swaps two facilities given a pair
 * @author Scott Walker
 */
public class SwapFacilityBlock extends Block {

    public SwapFacilityBlock() {
        super(VOID, FACILITY_PAIR);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        Pair pair = evalPair(0);
        getSolution().swapFacilities(pair.getA(), pair.getB());
        return null;
    }
}
