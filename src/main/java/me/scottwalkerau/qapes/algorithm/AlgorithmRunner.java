package me.scottwalkerau.qapes.algorithm;

import lombok.extern.log4j.Log4j2;
import me.scottwalkerau.qapes.qap.Instance;
import me.scottwalkerau.qapes.qap.Solution;

/**
 * The runnable class for an {@link AlgorithmTree}
 * @author Scott Walker
 */
@Log4j2
class AlgorithmRunner extends Thread {

    /** How long until we should force kill a thread in milliseconds */
    private static final int EXPIRE_TIME = 12000;
    /** How long until a thread is considered stale in milliseconds */
    private static final int STALE_TIME = 6000;
    /** How long to sleep for each time we go to check the status of the thread in milliseconds */
    private static final int SLEEP = 250;

    /** Instance to run against */
    private final Instance instance;
    /** Algorithm to evaluate */
    private final AlgorithmTree tree;

    // The following transient variables are not persistent
    private transient Result result;
    private transient Solution solution;
    private transient long startFitness;
    private transient long bestFitness;
    private transient long startTime;
    private transient long bestTime;
    private transient Status status;

    /**
     * Constructor
     * @param instance Instance to run against
     * @param tree Algorithm to use
     */
    AlgorithmRunner(Instance instance, AlgorithmTree tree) {
        this.instance = instance;
        this.tree = tree;
    }

    /**
     * Execute the algorithm
     */
    public void run() {
        // Setup and start
        initialise();
        Thread thread = new Thread(() -> tree.getRoot().evaluate(solution));
        thread.start();

        try {
            // Monitor the thread
            while (thread.isAlive() && !isExpired() && !isStale()) {
                Thread.sleep(SLEEP);
                long fitness = solution.getFitness();
                if (fitness < bestFitness) {
                    bestFitness = fitness;
                    bestTime = getTime();
                }
            }
        } catch (InterruptedException e) {
            log.error(e);
        }

        if (thread.isAlive()) {
            thread.stop();
            status = isStale() ? Status.STALE : Status.EXPIRED;
        } else {
            status = Status.COMPLETED;
        }

        // Set the result
        result = new Result(tree, solution, startFitness, status, getTimeTaken());
    }

    /**
     * Set up the initial state for the running of an algorithm
     */
    private void initialise() {
        solution = new Solution(instance);
        startTime = getTime();
        bestTime = startTime;
        startFitness = solution.getFitness();
        bestFitness = startFitness;
        status = Status.RUNNING;
    }

    /**
     * Get the result of this execution
     * @return Result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Get the current time in milliseconds
     * @return Time
     */
    private long getTime() {
        return System.currentTimeMillis();
    }

    /**
     * How long has elapsed
     * @return Time elapsed
     */
    private long getTimeTaken() {
        return getTime() - startTime;
    }

    /**
     * Has the time gone over the expire threshold
     * @return True if expired
     */
    private boolean isExpired() {
        return getTimeTaken() > EXPIRE_TIME;
    }

    /**
     * Has the time since the last best algorithm was found gone over stale threshold
     * @return True if stale
     */
    private boolean isStale() {
        return getTime() - bestTime > STALE_TIME;
    }
}
