package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;
import static me.scottwalkerau.qapes.block.ReturnType.VOID;

/**
 * An IF-ELSE statement, only executes one of the blocks.
 * @author Scott Walker
 */
public class IfElseBlock extends Block {

    public IfElseBlock() {
        super(VOID, BOOLEAN, VOID, VOID);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        boolean condition = evalBoolean(0);
        if (condition) {
            evalVoid(1);
        } else {
            evalVoid(2);
        }
        return null;
    }
}
