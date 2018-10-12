package me.scottwalkerau.qapes.qap;

/**
 * Object to store two members together.
 * @author Scott Walker
 */
public class Pair {

    /** First member */
    private final int a;
    /** Second member */
    private final int b;

    /**
     * Create a pair of two members
     * @param a First
     * @param b Second
     */
    public Pair(int a, int b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Get the first member
     * @return A
     */
    public int getA() {
        return a;
    }

    /**
     * Get the second member
     * @return B
     */
    public int getB() {
        return b;
    }

    /**
     * Get the pair as a string
     * @return Pair string
     */
    @Override
    public String toString() {
        return String.format("[%s, %s]", a, b);
    }
}
