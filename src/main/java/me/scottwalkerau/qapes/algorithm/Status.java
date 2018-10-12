package me.scottwalkerau.qapes.algorithm;

/**
 * Enum to hold the status of a running algorithm
 * @author Scott Walker
 */
public enum Status {
    /** Algorithm is still running */
    RUNNING(false),
    /** Algorithm successfully finished */
    COMPLETED(false),
    /** Algorithm reached the total time allowed */
    EXPIRED(true),
    /** Solution stopped improving */
    STALE(true);

    /** If the enum represents a terminated state */
    private final boolean terminated;

    /**
     * Constructor
     * @param terminated Terminated state
     */
    Status(boolean terminated) {
        this.terminated = terminated;
    }

    /**
     * Get if the enum represents a terminated state
     * @return True if terminated
     */
    public boolean isTerminated() {
        return terminated;
    }
}
