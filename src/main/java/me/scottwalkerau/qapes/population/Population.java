package me.scottwalkerau.qapes.population;

import lombok.extern.log4j.Log4j2;
import me.scottwalkerau.qapes.algorithm.AlgorithmNode;
import me.scottwalkerau.qapes.algorithm.AlgorithmTree;
import me.scottwalkerau.qapes.algorithm.Converter;
import me.scottwalkerau.qapes.algorithm.ResultSet;
import me.scottwalkerau.qapes.block.ReturnType;
import me.scottwalkerau.qapes.qap.Instance;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Abstract population class, does not implement the population itself, but rather the methods
 * @author Scott Walker
 */
@Log4j2
public abstract class Population {

    /** The maximum number of generations to perform */
    private static final int MAX_GENERATIONS = 40;
    /** After how many generation of no improvement do we want to terminate */
    private static final int STALE_GEN = 10;

    /** The percentage chance of performing a mutation */
    private static final int MUTATE_CHANCE = 5;
    /** If not mutation occurred, go through again making at most one mutation given this chance */
    private static final int MUTATE_FORCE_CHANCE = 25;

    /** The instances to run against */
    private final List<Instance> instances;

    private transient AlgorithmTree best;
    private transient int generation;
    private transient int bestGeneration;
    private transient List<ResultSet> results;

    /**
     * Constructor
     * @param instances The instances to run against
     */
    public Population(List<Instance> instances) {
        this.instances = new ArrayList<>(instances);
    }

    /**
     * Create the initial population of algorithms
     * @param starters Optional list of loaded algorithms to start with
     */
    protected abstract void initialise(List<AlgorithmTree> starters);

    /**
     * Get the population as a list
     * @return List of all algorithms
     */
    protected abstract List<AlgorithmTree> getPopulation();

    /**
     * Perform a single generation. Is called by runGeneration()
     * @return The best tree from that generation
     * @throws InterruptedException A thread was interrupted
     */
    protected abstract AlgorithmTree performGeneration() throws InterruptedException;

    /**
     * Perform crossovers to generate new population members
     */
    protected abstract void crossoverAndMutate();

    /**
     * Start running the genetic programming process
     * @param starters Algorithms to start with
     * @throws InterruptedException A thread was interrupted
     */
    public final void run(ArrayList<AlgorithmTree> starters) throws InterruptedException {
        // Setup
        generation = 0;
        bestGeneration = 0;
        results = new ArrayList<>();
        initialise(new ArrayList<>(starters));

        // Run the genetic programming
        runGeneration();
        while (generation < MAX_GENERATIONS && !isStale()) {
            generation++;
            crossoverAndMutate();
            runGeneration();
        }

        // Log the best
        log.info(best.print().toString());
        log.info("Best generation " + bestGeneration + ": " + best.getResultSet());

        // Save the best algorithm
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmmss");
        Converter.save(formatter.format(new Date()), best);

        // Debug results
        for (int i = 0; i < results.size(); i++) {
            log.debug(String.format("Gen %2d: %s", i, results.get(i)));
        }
    }

    /**
     * Run a single generation
     * @throws InterruptedException A thread was interrupted
     */
    private void runGeneration() throws InterruptedException {
        log.info("Starting generation " + generation + " ...");
        AlgorithmTree result = performGeneration();
        results.add(result.getResultSet());
        log.info("Best from generation " + generation + ": " + result.getResultSet());
        if (updateBest(result)) {
            log.debug("New best algorithm");
            log.debug(result.print().toString());
        }
    }

    /**
     * Try and update the best overall algorithm
     * @param tree The tree to update with
     * @return True if there was an update
     */
    private boolean updateBest(AlgorithmTree tree) {
        if (best == null || tree.getResultSet().compareTo(best.getResultSet()) <= 0) {
            best = tree.duplicate();
            bestGeneration = generation;
            return true;
        }
        return false;
    }

    /**
     * Perform mutations across the entire population
     */
    private void mutate(AlgorithmTree algorithm) {
        boolean mutated = algorithm.mutate(MUTATE_CHANCE);
        // Force at least one mutation if hit the given chance
        if (!mutated && new Random().nextInt(100) < MUTATE_FORCE_CHANCE) {
            algorithm.generateChildren(algorithm.getRandomNode());
        }
    }

    /**
     * If the population is stale past some threshold STALE_GEN
     * @return True if stale
     */
    private boolean isStale() {
        return (generation - bestGeneration >= STALE_GEN);
    }

    /**
     * Generate a list of crossovers given two parents
     * @param parentA First parent
     * @param parentB Second parent
     * @param amount How many to generate
     * @return List of generated algorithms
     */
    protected List<AlgorithmTree> crossTwoParents(AlgorithmTree parentA, AlgorithmTree parentB, int amount) {
        List<AlgorithmTree> list = new ArrayList<>();

        while(list.size() < amount) {
            singleCross(parentA, parentB, list);
        }

        // Bring the population back down if it accidentally went over. (In cases where amount is odd)
        while (list.size() > amount) {
            list.remove(amount);
        }
        return list;
    }

    /**
     * Perform a single crossover of two parents (To generate two offspring)
     * @param parentA First parent
     * @param parentB Second parent
     * @param list List to append to
     */
    private void singleCross(AlgorithmTree parentA, AlgorithmTree parentB, List<AlgorithmTree> list) {
        List<ReturnType> values = new ArrayList<>();
        Collections.addAll(values, ReturnType.values());

        AlgorithmTree childA = parentA.duplicate();
        AlgorithmTree childB = parentB.duplicate();
        list.add(childA);
        list.add(childB);

        while (values.size() > 0) {
            ReturnType type = values.remove(new Random().nextInt(values.size()));

            AlgorithmNode nodeA = childA.getRandomNodeOfType(type);
            AlgorithmNode nodeB = childB.getRandomNodeOfType(type);
            if (nodeA != null && nodeB != null) {
                if (nodeA.isRoot())
                    childA.setRoot(nodeB);

                if (nodeB.isRoot())
                    childB.setRoot(nodeA);

                // We're good, make the swap and continue the outer loop
                nodeA.swapWith(nodeB);
                mutate(childA);
                mutate(childB);
                return;
            }
        }
        // This line is never reached, AlgorithmTrees will always have a void node to swap
    }

    // -- Getters --

    /**
     * Get a random instance from the list of instances
     * @return Randomly selected instance
     */
    protected Instance getInstanceToRun() {
        int index = new Random().nextInt(instances.size());
        return instances.get(index);
    }

    /**
     * Get the instances to run against
     * @return List of supplied instances
     */
    public final List<Instance> getInstances() {
        return instances;
    }
}
