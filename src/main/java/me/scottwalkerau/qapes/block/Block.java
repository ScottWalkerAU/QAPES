package me.scottwalkerau.qapes.block;

import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.qap.Pair;
import me.scottwalkerau.qapes.qap.Solution;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The abstract class of the fundamental building block within QAPES
 * @author Scott Walker
 */
public abstract class Block {

    /** The return type of this block */
    private final ReturnType returnType;
    /** Types accepted as parameters to the evaluate method */
    private final List<ReturnType> paramTypes;
    /** The BlockType that references this Block */
    private BlockType blockType;

    /** The solution currently being evaluated */
    private Solution solution;
    /** The children currently being evaluated */
    private AlgorithmNode[] children;

    /**
     * Constructor
     * @param returnType What this block returns
     * @param paramTypes What this block takes as parameters
     */
    public Block(ReturnType returnType, ReturnType... paramTypes) {
        this.returnType = returnType;
        this.paramTypes = Collections.unmodifiableList(Arrays.asList(paramTypes));
        this.blockType = null;
    }

    /**
     * Run the algorithm from this point onwards, it will recursively call until it hits every leaf.
     * Intended to only be run on the root node.
     * @param solution The solution to use
     * @param children The children of the current block
     * @return Any type from {@link ReturnType}
     */
    public final Object evaluate(Solution solution, AlgorithmNode... children) {
        this.solution = solution;
        this.children = children;
        return evaluate();
    }

    // -- Abstract --

    /**
     * The inner evaluate call. Solution and children are already stored in the superclass
     * @return Any type from {@link ReturnType}
     */
    protected abstract Object evaluate();

    // -- Helpers --

    /**
     * Evaluate a boolean
     * @param child The index of the child
     * @return What the child evaluated to
     */
    protected final Boolean evalBoolean(int child) {
        return (Boolean) children[child].evaluate(solution);
    }

    /**
     * Evaluate an integer
     * @param child The index of the child
     * @return What the child evaluated to
     */
    protected final Long evalLong(int child) {
        return (Long) children[child].evaluate(solution);
    }

    /**
     * Evaluate a pair
     * @param child The index of the child
     * @return What the child evaluated to
     */
    protected final Pair evalPair(int child) {
        return (Pair) children[child].evaluate(solution);
    }

    /**
     * Evaluate void
     * @param child The index of the child
     */
    protected final void evalVoid(int child) {
        children[child].evaluate(solution);
    }

    // -- Getters --

    /**
     * Is the block a terminal
     * @return True if terminal
     */
    public final boolean isTerminal() {
        return paramTypes.isEmpty();
    }

    /**
     * Get the {@link ReturnType}
     * @return ReturnType
     */
    public final ReturnType getReturnType() {
        return returnType;
    }

    /**
     * Get the list of parameter types the block takes
     * @return List of {@link ReturnType}s
     */
    public final List<ReturnType> getParamTypes() {
        return paramTypes;
    }

    /**
     * Get the {@link BlockType}
     * @return BlockType
     */
    public final BlockType getBlockType() {
        return blockType;
    }

    /**
     * Get the {@link Solution} currently being evaluated against
     * @return Solution
     */
    public final Solution getSolution() {
        return solution;
    }

    // -- Setters --

    /**
     * Set the {@link BlockType} for this block. Can only be set once
     * @param blockType BlockType to set
     */
    public final void setBlockType(BlockType blockType) {
        if (this.blockType != null)
            throw new IllegalArgumentException("This block already has an associated BlockType");
        this.blockType = blockType;
    }
}
