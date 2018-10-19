package me.scottwalkerau.qapes.qap;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Object that stores the information of a given solution
 * @author Scott Walker
 */
public class Solution {

    /** The instance that this solution is for */
    private final Instance instance;
    /** The size of the solution */
    private final int size;

    /** The fitness value, null if it hasn't been re-calculated after changes */
    private Long fitness;
    /** The mapping of facilities (index) to locations (value) */
    private List<Integer> facilityLocations;
    /** If the last move improved the fitness */
    private boolean improved;
    /** Delta computation for next moves given the current state */
    private SquareMatrix deltas;

    /**
     * Default constructor
     * @param inst The instance this solution is for
     */
    public Solution(Instance inst) {
        instance = inst;
        size = instance.getSize();
        facilityLocations = Arrays.asList(new Integer[instance.getSize()]);
        initialise();
    }

    /**
     * Initialise the facilityLocations list with a random permutation
     */
    private void initialise() {
        fitness = null;

        // Sequential list of numbers 0 to size-1
        List<Integer> sequence = IntStream.range(0, size).boxed().collect(Collectors.toList());

        Random random = new Random();
        for (int fac = 0; fac < size; fac++) {
            Integer loc = sequence.remove(random.nextInt(sequence.size()));
            facilityLocations.set(fac, loc);
        }

        // Context specific attributes
        improved = true;
        deltas = new SquareMatrix(size);
        computeDeltas();
    }

    /**
     * Get the current fitness of the solution. Synchronised as multiple threads may call it at once,
     * and we only want to perform the computation once
     * @return Fitness as an int. Lower values are better
     */
    public synchronized long getFitness() {
        // We don't know the value, so calculate it
        if (fitness == null) {
            fitness = calculateFitness();
        }

        if (fitness < 0) {
            fitness = Long.MAX_VALUE;
        }

        return fitness;
    }

    public synchronized long calculateFitness() {
        int fitness = 0;
        for (int facA = 0; facA < size; facA++) {
            Integer locA = getLocation(facA);
            for (int facB = 0; facB < size; facB++) {
                Integer locB = getLocation(facB);

                int sum = instance.getFlow().get(facA, facB) * instance.getDistance().get(locA, locB);
                fitness += sum;
            }
        }
        return fitness;
    }

    /**
     * Get the gap of the fitness from the best known value. A gap of 1 means they're identical,
     * and a gap of 2 means it's 2x the BKV
     * @return Solution gap
     */
    public double getGap() {
        return getFitness() * 1.0 / getInstance().getBkv();
    }

    /**
     * Get the instance associated with this solution
     * @return Instance of the solution
     */
    public Instance getInstance() {
        return instance;
    }

    /**
     * Get the location a facility is mapped to
     * @param facility Facility we're after
     * @return Location of facility
     */
    public Integer getLocation(int facility) {
        return facilityLocations.get(facility);
    }

    /**
     * Get the number of facilities or locations
     * @return Size of the solution
     */
    public int getSize() {
        return size;
    }

    /**
     * Simply get a random facility
     * @return Random number in [0,size)
     */
    public int randomFacility() {
        return new Random().nextInt(size);
    }

    /**
     * Did the last move improve the fitness?
     * @return True if fitness is better
     */
    public boolean isImproved() {
        return improved;
    }

    /**
     * Get the delta for a given swap
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @return Change in the fitness. Negative if a better fitness.
     */
    public int getSwapDelta(int facA, int facB) {
        return deltas.get(facA, facB);
    }

    // -- Swap functions --

    /**
     * Perform the swap between two facilities
     * @param facA First facility to swap
     * @param facB Second facility to swap
     */
    public synchronized void swapFacilities(int facA, int facB) {
        if (facA == facB) {
            improved = false;
            return;
        }

        long initial = getFitness();
        Integer locA = getLocation(facA);
        Integer locB = getLocation(facB);
        facilityLocations.set(facA, locB);
        facilityLocations.set(facB, locA);

        // Set the improved flag
        fitness += getSwapDelta(facA, facB);
        improved = getFitness() < initial;

        // Recompute deltas
        recomputeDeltas(facA, facB);
    }

    /**
     * Compute all delta values from scratch
     */
    private synchronized void computeDeltas() {
        for (int facA = 0; facA < size; facA++) {
            for (int facB = facA + 1; facB < size; facB++) {
                int delta = computeSwapDelta(facA, facB);
                deltas.set(facA, facB, delta);
                deltas.set(facB, facA, delta);
            }
            deltas.set(facA, facA, 0);
        }
    }

    /**
     * Recompute delta values given two facility just swapped
     * @param swapC First facility swapped
     * @param swapD Second facility swapped
     */
    private synchronized void recomputeDeltas(int swapC, int swapD) {
        for (int facA = 0; facA < size - 1; facA++) {
            for (int facB = facA + 1; facB < size; facB++) {
                int delta;
                if (facA == swapC || facA == swapD || facB == swapC || facB == swapD) {
                    delta = computeSwapDelta(facA, facB);
                } else {
                    delta = computeSwapDeltaShort(facA, facB, swapC, swapD);
                    delta += deltas.get(facA, facB);
                }
                deltas.set(facA, facB, delta);
                deltas.set(facB, facA, delta);

                if (delta < (getFitness() * -1)) {
                    throw new IllegalStateException("Delta value for a swap cannot be less than fitness. " +
                            String.format("Delta: %d, Fitness: %d, facA: %d, facB: %d", delta, getFitness(), facA, facB));
                }
            }
        }
    }

    /**
     * Calculate the change in fitness if a certain swap was to occur
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @return The fitness change. Negative is better
     */
    private int computeSwapDelta(int facA, int facB) {
        if (instance.isSymmetric()) {
            return computeSwapDeltaSymmetric(facA, facB);
        } else {
            return computeSwapDeltaAsymmetric(facA, facB);
        }
    }

    /**
     * Calculate the change in fitness if a certain swap was to occur, for a symmetric instance
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @return The fitness change. Negative is better
     */
    private int computeSwapDeltaSymmetric(int facA, int facB) {
        if (facA == facB)
            return 0;

        int sum = 0;
        SquareMatrix dist = instance.getDistance();
        SquareMatrix flow = instance.getFlow();

        int locA = getLocation(facA);
        int locB = getLocation(facB);
        for (int f = 0; f < size; f++) {
            if (f == facA || f == facB)
                continue; // No difference between facA and facB

            // Add facA*locB, facB*locA : Remove facA*locA, facB*locB
            int l = getLocation(f);
            sum += (flow.get(f, facA) - flow.get(f, facB)) * (dist.get(l, locB) - dist.get(l, locA));
        }

        sum *= 2;
        return sum;
    }

    /**
     * Calculate the change in fitness if a certain swap was to occur, for an asymmetric instance
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @return The fitness change. Negative is better
     */
    private int computeSwapDeltaAsymmetric(int facA, int facB) {
        if (facA == facB)
            return 0;

        int sum = 0;
        SquareMatrix dist = instance.getDistance();
        SquareMatrix flow = instance.getFlow();

        int locA = getLocation(facA);
        int locB = getLocation(facB);
        for (int f = 0; f < size; f++) {
            if (f == facA || f == facB)
                continue; // No difference between facA and facB

            int l = getLocation(f);
            sum += flow.get(f, facA) * (dist.get(l, locB) - dist.get(l, locA))
                    + flow.get(f, facB) * (dist.get(l, locA) - dist.get(l, locB))
                    + flow.get(facA, f) * (dist.get(locB, l) - dist.get(locA, l))
                    + flow.get(facB, f) * (dist.get(locA, l) - dist.get(locB, l));
        }

        // Each facility/location can have a distance and a flow between itself, so update that too
        sum += flow.get(facA, facA) * (dist.get(locB, locB) - dist.get(locA, locA))
                + flow.get(facA, facB) * (dist.get(locB, locA) - dist.get(locA, locB))
                + flow.get(facB, facA) * (dist.get(locA, locB) - dist.get(locB, locA))
                + flow.get(facB, facB) * (dist.get(locA, locA) - dist.get(locB, locB));

        return sum;
    }

    /**
     * Calculate the change in fitness if a certain swap was to occur
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @return The fitness change. Negative is better
     */
    private int computeSwapDeltaShort(int facA, int facB, int swapC, int swapD) {
        if (instance.isSymmetric()) {
            return computeSwapDeltaSymmetricShort(facA, facB, swapC, swapD);
        } else {
            return computeSwapDeltaAsymmetricShort(facA, facB, swapC, swapD);
        }
    }

    /**
     * Calculate the change in fitness if a certain swap was to occur, given C and D were just swapped
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @param swapC First facility just swapped
     * @param swapD Second facility just swapped
     * @return The fitness change. Negative is better
     */
    private int computeSwapDeltaSymmetricShort(int facA, int facB, int swapC, int swapD) {
        if (facA == facB)
            return 0;

        SquareMatrix dist = instance.getDistance();
        SquareMatrix flow = instance.getFlow();

        int locA = getLocation(facA);
        int locB = getLocation(facB);
        int locC = getLocation(swapC);
        int locD = getLocation(swapD);

        // Formula from Matt's code, but easier to read
        return 2 * (flow.get(facA, swapC) - flow.get(facA, swapD) + flow.get(facB, swapD) - flow.get(facB, swapC))
                 * (dist.get(locB, locC)  - dist.get(locB, locD)  + dist.get(locA, locD)  - dist.get(locA, locC));

    }

    /**
     * Calculate the change in fitness if a certain swap was to occur, given C and D were just swapped
     * @param facA First facility to swap
     * @param facB Second facility to swap
     * @param swapC First facility just swapped
     * @param swapD Second facility just swapped
     * @return The fitness change. Negative is better
     */
    private int computeSwapDeltaAsymmetricShort(int facA, int facB, int swapC, int swapD) {
        if (facA == facB)
            return 0;

        SquareMatrix dist = instance.getDistance();
        SquareMatrix flow = instance.getFlow();

        int locA = getLocation(facA);
        int locB = getLocation(facB);
        int locC = getLocation(swapC);
        int locD = getLocation(swapD);

        // Formula from Matt's code, btu easier to read
        int sum = (flow.get(facA, swapC) - flow.get(facA, swapD) + flow.get(facB, swapD) - flow.get(facB, swapC))
                * (dist.get(locB, locC)  - dist.get(locB, locD)  + dist.get(locA, locD)  - dist.get(locA, locC));
        sum += (flow.get(swapC, facA) - flow.get(swapD, facA) + flow.get(swapD, facB) - flow.get(swapC, facB))
             * (dist.get(locC, locB)  - dist.get(locD, locB)  + dist.get(locD, locA)  - dist.get(locC, locA));

        return sum;
    }

    // -- Override --

    /**
     * Turn the solution into a string
     * @return Solution
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < facilityLocations.size(); i++) {
            if (i > 0)
                out.append(',');
            out.append(facilityLocations.get(i));
        }
        return String.format("Size: %d, Fitness: %d, Mapping: [%s]", getSize(), getFitness(), out.toString());
    }

    /**
     * Method purely to allow testing. Thus it's package-private
     * @param facilityLocations Facility -> Location list
     */
    synchronized void setFacilityLocations(List<Integer> facilityLocations) {
        this.facilityLocations = facilityLocations;
        this.fitness = null;
        computeDeltas();
    }
}
