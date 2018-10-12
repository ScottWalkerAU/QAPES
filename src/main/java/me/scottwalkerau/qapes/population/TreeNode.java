package me.scottwalkerau.qapes.population;

import java.util.ArrayList;
import java.util.List;

/**
 * The tree structure to use for {@link TreePopulation}
 * @author Scott Walker
 */
public class TreeNode<T> {

    /** Parent node of this node */
    private TreeNode<T> parent;
    /** What the node contains */
    private T data;
    /** The children of this node */
    private final List<TreeNode<T>> children;

    /**
     * Constructor
     * @param branches How many children to have
     * @param depth How many more levels to spawn
     */
    public TreeNode(int branches, int depth) {
        // If we're at the last level, don't create any children
        if (depth == 0)
            branches = 0;

        this.parent = null;
        this.data = null;
        this.children = new ArrayList<>(branches);

        if (branches > 0) {
            for (int i = 0; i < branches; i++) {
                children.add(new TreeNode<>(this, branches, depth - 1));
            }
        }
    }

    /**
     * Constructor
     * @param parent The parent
     * @param branches How many children to have
     * @param depth How many more levels to spawn
     */
    private TreeNode(TreeNode<T> parent, int branches, int depth) {
        this(branches, depth);
        this.parent = parent;
    }

    /**
     * Swap the data between two nodes
     * @param other The other node to swap with
     */
    public void swapData(TreeNode<T> other) {
        T aux = getData();
        setData(other.getData());
        other.setData(aux);
    }

    /**
     * Get the data from the node
     * @return Data
     */
    public T getData() {
        return data;
    }

    /**
     * Set the data for the node
     * @param data Data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Get this nodes parent
     * @return Parent
     */
    public TreeNode<T> getParent() {
        return parent;
    }

    /**
     * Get the children of this node
     * @return Children
     */
    public List<TreeNode<T>> getChildren() {
        return children;
    }
}
