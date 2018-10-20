package me.scottwalkerau.qapes.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to hold multiple Results, with its main methods getting averages
 * @author Scott Walker
 */
public class ResultSet implements Comparable<ResultSet> {

    /** What algorithm did we use */
    private final AlgorithmTree algorithmTree;
    /** Internal results */
    private final List<Result> results;

    /**
     * Constructor
     * @param algorithmTree The algorithm used
     */
    public ResultSet(AlgorithmTree algorithmTree) {
        this.algorithmTree = algorithmTree;
        this.results = new ArrayList<>();
    }

    /**
     * Comparator to another ResultSet
     * @param other The other ResultSet
     * @return Negative if this set is better, positive if the other is better
     */
    public int compareTo(ResultSet other) {
        return getMeanFitness().compareTo(other.getMeanFitness());
    }

    /**
     * Add another result
     * @param result Result to add
     */
    public void addResult(Result result) {
        results.add(result);
    }

    /**
     * Get the mean fitness value
     * @return Mean fitness of every result
     */
    public Double getMeanFitness() {
        if (getSize() == 0)
            return null;
        return results.stream().mapToDouble(Result::getFitness).sum() / getSize();
    }

    /**
     * Get the mean gap of each solution object
     * @return Mean solution gap
     */
    public Double getMeanGap() {
        if (getSize() == 0)
            return null;
        return results.stream().mapToDouble(Result::getGap).sum() / getSize();
    }

    // -- Getters --

    /**
     * Get the algorithm used
     * @return AlgorithmTree
     */
    public AlgorithmTree getTree() {
        return algorithmTree;
    }

    /**
     * Get the size of the result set
     * @return results.size()
     */
    public int getSize() {
        return results.size();
    }

    /**
     * Turn the ResultSet into a printable string
     * @return This as a string
     */
    @Override
    public String toString() {
        return String.format("ResultSet{meanFitness=%.4f, meanGap=%.4f}", getMeanFitness(), getMeanGap());
    }
}
