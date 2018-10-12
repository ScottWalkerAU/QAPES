package me.scottwalkerau.qapes.block.blocks.qap.facility;

import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.qap.Pair;

import static me.scottwalkerau.qapes.block.ReturnType.FACILITY_PAIR;

public class RandomFacilityPairBlock extends Block {

    public RandomFacilityPairBlock() {
        super(FACILITY_PAIR);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        return new Pair(getSolution().randomFacility(), getSolution().randomFacility());
    }
}
