package me.scottwalkerau.qapes.algorithm;

import me.scottwalkerau.qapes.qap.Solution;

/**
 * Class to hold the result of a single algorithm run
 * @author Scott Walker
 */
public class Result implements Comparable<Result> {

    private final AlgorithmTree tree;
    private final Solution solution;
    private final long initialFitness;
    private final Status status;
    private final long timeTaken;

    /**
     * Constructor
     * @param tree Tree
     * @param solution Solution
     * @param initialFitness Start fitness value
     * @param status End status of the execution
     * @param timeTaken Time elapsed
     */
    public Result(AlgorithmTree tree, Solution solution, long initialFitness, Status status, long timeTaken) {
        if (status == Status.RUNNING)
            throw new IllegalStateException("Cannot create results from a running thread");

        this.tree = tree;
        this.solution = solution;
        this.initialFitness = initialFitness;
        this.status = status;
        this.timeTaken = timeTaken;
    }

    /**
     * Arbitrary fitness function to determine which trees are better
     * @return Fitness value
     */
    public double getFitness() {
        double fitness = getSolution().getGap() + 1;

        double timeLog = Math.log(getTimeTaken());
        fitness *= timeLog;

        if (status.isTerminated())
            fitness *= 1.25;

        return fitness;
    }

    /**
     * Quick accessor for the gap
     * @return Gap of solution
     */
    public double getGap() {
        return solution.getGap();
    }

    @Override
    public int compareTo(Result o) {
        return Double.compare(getFitness(), o.getFitness());
    }

    // -- Getters --

    public AlgorithmTree getTree() {
        return tree;
    }

    public Solution getSolution() {
        return solution;
    }

    public long getInitialFitness() {
        return initialFitness;
    }

    public Status getStatus() {
        return status;
    }

    public long getTimeTaken() {
        return timeTaken;
    }
}
