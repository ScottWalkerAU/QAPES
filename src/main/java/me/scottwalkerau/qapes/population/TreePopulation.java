package me.scottwalkerau.qapes.population;

import me.scottwalkerau.qapes.algorithm.AlgorithmTree;
import me.scottwalkerau.qapes.algorithm.ResultSet;
import me.scottwalkerau.qapes.qap.Instance;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements {@link Population} as a tree structure of depth 2 and a configurable amount of children
 * @author Scott Walker
 */
public class TreePopulation extends Population {

    // Population size = 1 + children + children^2 = 1 + n(n+1)
    private static final int CHILDREN = 3;
    private static final int DEPTH = 2; // Not mutable

    private TreeNode<AlgorithmTree> root;

    public TreePopulation(ArrayList<Instance> instances) {
        super(instances);
    }

    protected void initialise(List<AlgorithmTree> starters) {
        root = new TreeNode<>(CHILDREN, DEPTH);
        initialiseRecursion(root, starters);
    }

    private void initialiseRecursion(TreeNode<AlgorithmTree> node, List<AlgorithmTree> starters) {
        AlgorithmTree algorithm = starters.size() > 0 ? starters.remove(0) : new AlgorithmTree();
        node.setData(algorithm);
        for (TreeNode<AlgorithmTree> child : node.getChildren()) {
            initialiseRecursion(child, starters);
        }
    }

    protected List<AlgorithmTree> getPopulation() {
        List<AlgorithmTree> list = new ArrayList<>();
        getPopulationRecursion(root, list);
        return list;
    }

    private void getPopulationRecursion(TreeNode<AlgorithmTree> node, List<AlgorithmTree> list) {
        list.add(node.getData());
        for (TreeNode<AlgorithmTree> child : node.getChildren()) {
            getPopulationRecursion(child, list);
        }
    }

    protected AlgorithmTree performGeneration() throws InterruptedException {
        for (AlgorithmTree algorithm : getPopulation()) {
            algorithm.run(getInstanceToRun());
        }

        for (AlgorithmTree algorithm : getPopulation()) {
            algorithm.join();
        }

        for (TreeNode<AlgorithmTree> parent : root.getChildren()) {
            promoteChild(parent);
        }

        promoteChild(root);

        return root.getData();
    }

    private void promoteChild(TreeNode<AlgorithmTree> parent) {
        ResultSet best = parent.getData().getResultSet();
        Integer replace = null;
        for (int child = 0; child < CHILDREN; child++) {
            ResultSet childResult = parent.getChildren().get(child).getData().getResultSet();
            if (childResult.compareTo(best) < 0) {
                best = childResult;
                replace = child;
            }
        }

        if (replace != null) {
            parent.swapData(parent.getChildren().get(replace));
        }
    }

    protected void crossoverAndMutate() {
        for (TreeNode<AlgorithmTree> parent : root.getChildren()) {
            List<AlgorithmTree> offspring = crossTwoParents(root.getData(), parent.getData(), CHILDREN);
            for (TreeNode<AlgorithmTree> child : parent.getChildren()) {
                child.setData(offspring.remove(0));
            }
        }
    }
}
