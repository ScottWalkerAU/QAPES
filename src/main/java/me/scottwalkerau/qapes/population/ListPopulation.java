package me.scottwalkerau.qapes.population;

import me.scottwalkerau.qapes.algorithm.AlgorithmTree;
import me.scottwalkerau.qapes.algorithm.ResultSet;
import me.scottwalkerau.qapes.qap.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implements {@link Population} as a standard list
 * @author Scott Walker
 */
public class ListPopulation extends Population {

    /** Size of the population to use for each run */
    private static final int POPULATION_SIZE = 16;
    /** How many of the best members to keep and not regenerate. Set to 2 for original functionality */
    private static final int TRIMMED_SIZE = 6;

    private ArrayList<AlgorithmTree> population;
    private List<ResultSet> results;

    public ListPopulation(List<Instance> instances) {
        super(instances);
    }

    protected void initialise(List<AlgorithmTree> starters) {
        population = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            AlgorithmTree algorithm = starters.size() > 0 ? starters.remove(0) : new AlgorithmTree();
            population.add(algorithm);
        }
    }

    protected List<AlgorithmTree> getPopulation() {
        return population;
    }

    protected AlgorithmTree performGeneration() throws InterruptedException {
        for (AlgorithmTree algorithm : population) {
            algorithm.run(getInstanceToRun());
        }

        List<ResultSet> results = new ArrayList<>(population.size());
        for (AlgorithmTree algorithm : population) {
            results.add(algorithm.join());
        }

        Collections.sort(results);
        this.results = results;

        return results.get(0).getTree();
    }

    protected void crossoverAndMutate() {
        while (population.size() > TRIMMED_SIZE) {
            population.remove(TRIMMED_SIZE);
        }

        // First parent is the root, second parent is in the trimmed population, but not the first member
        int index = new Random().nextInt(population.size() - 1) + 1;
        AlgorithmTree parentA = results.get(0).getTree();
        AlgorithmTree parentB = results.get(index).getTree();

        // Add remaining members
        population.addAll(crossTwoParents(parentA, parentB, POPULATION_SIZE - population.size()));
    }
}
