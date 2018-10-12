package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;
import static me.scottwalkerau.qapes.block.ReturnType.VOID;

public class IfBlock extends Block {

    public IfBlock() {
        super(VOID, BOOLEAN, VOID);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        boolean condition = evalBoolean(0);
        if (condition) {
            evalVoid(1);
        }
        return null;
    }
}
