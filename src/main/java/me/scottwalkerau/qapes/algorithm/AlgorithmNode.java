package me.scottwalkerau.qapes.algorithm;

import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.qap.Solution;

/**
 * Internal node for an algorithm tree
 * @author Scott Walker
 */
public class AlgorithmNode {

    /** What level of the tree we're at */
    private int level;
    /** The parent of this node */
    private AlgorithmNode parent;
    /** The block we're storing */
    private Block block;
    /** All children blocks */
    private AlgorithmNode[] children;

    /**
     * Constructor
     * @param parent Parent for this node
     * @param block The block to store in the node
     */
    public AlgorithmNode(AlgorithmNode parent, Block block) {
        this.level = (parent == null) ? 0 : parent.getLevel() + 1;
        this.parent = parent;
        this.block = block;
        this.children = new AlgorithmNode[block.getParamTypes().size()];
    }

    private AlgorithmNode(AlgorithmNode original, AlgorithmNode parent) {
        this.level = original.level;
        this.parent = parent;
        this.block = original.block;
        this.children = new AlgorithmNode[original.children.length];
        for (int i = 0; i < children.length; i++) {
            children[i] = new AlgorithmNode(original.children[i], this);
        }
    }

    /**
     * Clone this node and all its children. Can only clone root nodes.
     * @return New root node
     */
    public AlgorithmNode duplicate() {
        if (!isRoot()) {
            throw new UnsupportedOperationException("Can only duplicate a root node");
        }
        return new AlgorithmNode(this, (AlgorithmNode) null);
    }

    /**
     * Call the evaluation function on the block stored in this node.
     * @param solution Solution in use
     * @return The return of the block
     */
    public Object evaluate(Solution solution) {
        return block.evaluate(solution, children);
    }

    /**
     * Recalculate the depth-levels from this node downwards
     */
    private void updateLevels() {
        level = isRoot() ? 0 : parent.getLevel() + 1;
        for (AlgorithmNode child : children) {
            child.updateLevels();
        }
    }

    /**
     * Used in crossover to swap places between trees
     * @param other The other node to swap with
     */
    public void swapWith(AlgorithmNode other) {
        int indexA = this.getIndexFromParent();
        int indexB = other.getIndexFromParent();

        AlgorithmNode parentA = this.getParent();
        AlgorithmNode parentB = other.getParent();

        // Now swap
        if (parentA != null) parentA.children[indexA] = other;
        if (parentB != null) parentB.children[indexB] = this;

        other.parent = parentA;
        this.parent = parentB;

        other.updateLevels();
        this.updateLevels();
    }

    /**
     * Overwrite this node with the given node
     * @param other Node to overwrite with
     */
    public void overwriteWith(AlgorithmNode other) {
        this.block = other.block;
        this.children = other.children;
        updateLevels();
    }

    /**
     * Overwrite the block in this node with the given type
     * @param type Block to overwrite with
     */
    public void overwriteWith(BlockType type) {
        if (!type.getBlock().isTerminal())
            throw new IllegalArgumentException("Can only overwrite with a Block if it's a terminal");

        this.block = type.getBlock();
        this.children = new AlgorithmNode[0];
        updateLevels();
    }

    /**
     * Get whether or not another AlgorithmNode contains the same block structure. Ignores where in the tree the node is,
     * this just checks from this location down to the leaf nodes.
     * @param other The other AlgorithmNode to test
     * @return True if both nodes contain the same structure, false if they're different
     */
    public boolean equivalentTo(AlgorithmNode other) {
        // The blocks aren't the same to begin with? Not a match.
        if (getBlockType() != other.getBlockType())
            return false;

        // Since they're both the same block, the children sizes are the same.
        for (int i = 0; i < getChildren().length; i++) {
            AlgorithmNode thisChild = getChildren()[i];
            AlgorithmNode otherChild = other.getChildren()[i];

            // Any of the children aren't the same? Not a match.
            if (!thisChild.equivalentTo(otherChild))
                return false;
        }

        // We're the same!
        return true;
    }

    /**
     * Set a particular child node
     * @param index Index of the child
     * @param child Node to set with
     */
    public void setChild(int index, AlgorithmNode child) {
        getChildren()[index] = child;
    }

    // -- Getters --

    public boolean isRoot() {
        return parent == null;
    }

    public int getLevel() {
        return level;
    }

    public AlgorithmNode getParent() {
        return parent;
    }

    public AlgorithmNode[] getChildren() {
        return children;
    }

    public AlgorithmNode getChild(int index) {
        return getChildren()[index];
    }

    public Block getBlock() {
        return block;
    }

    public BlockType getBlockType() {
        return block.getBlockType();
    }

    public int getIndexFromParent() {
        if (parent == null)
            return -1;

        AlgorithmNode[] children = parent.getChildren();
        for (int i = 0; i < children.length; i++) {
            if (children[i] == this)
                return i;
        }
        throw new RuntimeException("Parent should have this AlgorithmNode as a child");
    }

    // -- Printer --

    /**
     * Print the node and all its children into the given string builder
     * @param builder Where to append the text
     * @param prefix Prefix for the current line
     */
    public void print(StringBuilder builder, String prefix) {
        builder.append("\n").append(prefix).append(getBlockType());
        for (AlgorithmNode child : getChildren())
            child.print(builder, prefix + '|');
    }
}
