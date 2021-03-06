package me.scottwalkerau.qapes.algorithm;

import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockCatalogue;
import me.scottwalkerau.qapes.block.ReturnType;
import me.scottwalkerau.qapes.qap.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to store an entire algorithm in a tree structure.
 * The tree is a custom-built Abstract Data Type.
 * @author Scott Walker
 */
public class AlgorithmTree {

    /** How many times to run the algorithm */
    private static final int RUNS = 4;
    /** The depth at which we start to prefer terminal nodes */
    private static final int MAX_DEPTH = 5;

    /** Root node of the tree */
    private AlgorithmNode root;

    /** The result from the last run of the algorithm. This is needed for persistent fitness */
    private ResultSet resultSet;
    /** Runners in execution */
    private transient List<AlgorithmRunner> runners;

    /**
     * Constructor to randomly generate a tree
     */
    public AlgorithmTree() {
        root = new AlgorithmNode(null, BlockCatalogue.getRandomBlock(ReturnType.VOID));
        generateChildren(root);
    }

    /**
     * Clone constructor
     * @param original Algorithm to clone
     */
    private AlgorithmTree(AlgorithmTree original) {
        this.root = original.root.duplicate();
        this.resultSet = original.resultSet;
    }

    /**
     * Constructor used in loading an algorithm from file
     * @param root Root of the tree
     */
    public AlgorithmTree(AlgorithmNode root) {
        this.root = root;
    }

    /**
     * Call the clone constructor
     * @return New AlgorithmTree
     */
    public AlgorithmTree duplicate() {
        return new AlgorithmTree(this);
    }

    // TODO Private or move into the AlgorithmNode?
    public void generateChildren(AlgorithmNode current) {
        List<ReturnType> types = current.getBlock().getParamTypes();
        // For each of the children, set it to a random one of that type
        for (int i = 0; i < types.size(); i++) {
            ReturnType rt = types.get(i);
            Block block;
            if (current.getLevel() < MAX_DEPTH - 1) {
                block = BlockCatalogue.getRandomBlock(rt);
            } else {
                block = BlockCatalogue.getRandomBlockChildless(rt);
            }

            AlgorithmNode child = new AlgorithmNode(current, block);
            current.setChild(i, child);
            generateChildren(child);
        }
    }

    /**
     * Run the algorithm n times against the instance
     * @param instance Instance to use
     */
    public void run(Instance instance) {
        runners = new ArrayList<>();
        for (int run = 0; run < RUNS; run++) {
            AlgorithmRunner runner = new AlgorithmRunner(instance, this);
            runner.run();
            runners.add(runner);
        }
    }

    /**
     * Wait for the execution to finish and return the result set
     * @return Result set
     * @throws InterruptedException Thread was interrupted
     */
    public ResultSet join() throws InterruptedException {
        resultSet = new ResultSet(this);
        for (AlgorithmRunner runner : runners) {
            runner.join();
            resultSet.addResult(runner.getResult());
        }
        return resultSet;
    }

    /**
     * Cause mutations throughout the tree
     * @param chance Chance for a mutation out of 100
     * @return True if at least one mutation occurred
     */
    public boolean mutate(int chance) {
        return mutateRecursion(chance, root);
    }

    /**
     * Cause mutations throughout the tree
     * @param chance Chance for a mutation out of 100
     * @param node The current node to operate on
     * @return True if at least one mutation occurred
     */
    private boolean mutateRecursion(int chance, AlgorithmNode node) {
        if (new Random().nextInt(100) < chance) {
            generateChildren(node);
            return true;
        }

        boolean mutated = false;
        for (AlgorithmNode child : node.getChildren()) {
            if (mutateRecursion(chance, child))
                mutated = true;
        }
        return mutated;
    }

    /**
     * Get a random node that matches the given type
     * @param type Type to choose
     * @return Node of chosen type
     */
    public AlgorithmNode getRandomNodeOfType(ReturnType type) {
        List<AlgorithmNode> typeList = new ArrayList<>();

        for (AlgorithmNode current : getAllNodes()) {
            if (current.getBlock().getReturnType() == type)
                typeList.add(current);
        }

        return (typeList.size() == 0) ? null : typeList.get(new Random().nextInt(typeList.size()));
    }

    /**
     * Get a random node from the tree
     * @return A randomly selected node from the tree
     */
    public AlgorithmNode getRandomNode() {
        List<AlgorithmNode> all = getAllNodes();
        return all.get(new Random().nextInt(all.size()));
    }

    /**
     * Get all the nodes of the tree as a list
     * @return Every node
     */
    public List<AlgorithmNode> getAllNodes() {
        List<AlgorithmNode> list = new ArrayList<>();
        getAllNodesRecursion(list, root);
        return list;
    }

    /**
     * Helper function for getAllNodes
     * @param list List to append to
     * @param current Current node
     */
    private void getAllNodesRecursion(List<AlgorithmNode> list, AlgorithmNode current) {
        list.add(current);
        for (AlgorithmNode child : current.getChildren()) {
            getAllNodesRecursion(list, child);
        }
    }

    /**
     * Print the tree as a string over new lines
     */
    public StringBuilder print() {
        StringBuilder builder = new StringBuilder();
        new TreeMinimiser(this).run().getRoot().print(builder, "");
        return builder;
    }

    // -- Getters --

    public AlgorithmNode getRoot() {
        return root;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    // -- Setters --

    public void setRoot(AlgorithmNode root) {
        this.root = root;
    }
}
