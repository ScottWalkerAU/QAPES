package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;
import static me.scottwalkerau.qapes.block.ReturnType.VOID;

/**
 * An IF statement without an else. Executes the code of the condition returns true
 * @author Scott Walker
 */
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
