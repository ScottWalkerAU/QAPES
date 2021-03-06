package me.scottwalkerau.qapes.block.blocks.control;

import me.scottwalkerau.qapes.block.Block;

import static me.scottwalkerau.qapes.block.ReturnType.BOOLEAN;
import static me.scottwalkerau.qapes.block.ReturnType.VOID;

/**
 * A WHILE loop given the condition returns true
 * @author Scott Walker
 */
public class WhileBlock extends Block {

    public WhileBlock() {
        super(VOID, BOOLEAN, VOID);
    }

    /**
     * {@inheritDoc}
     */
    public Object evaluate() {
        while (evalBoolean(0)) {
            evalVoid(1);
        }
        return null;
    }
}
