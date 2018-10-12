package me.scottwalkerau.qapes.qap;

/**
 * A fixed 2-dimensional matrix of size N*N for a type Double
 * @author Scott Walker
 */
public class SquareMatrix {

    /** Size of the matrix */
    private final int size;
    /** The square matrix */
    private final Integer[][] matrix;
    /** If the matrix is symmetric */
    private Boolean symmetric;

    /**
     * Constructor
     * @param size Size of the matrix
     */
    public SquareMatrix(int size) {
        this.size = size;
        matrix = new Integer[this.size][this.size];
        symmetric = null;
    }

    /**
     * Get whether the matrix is symmetric along axis y=x
     * @return True if the matrix is symmetric
     */
    public boolean isSymmetric() {
        // If the value has already been calculated
        if (symmetric != null) {
            return symmetric;
        }

        // Loop until an asymmetric pair is found
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (!get(i, j).equals(get(j, i))) {
                    symmetric = false;
                    return false;
                }
            }
        }

        // Every pair matches
        symmetric = true;
        return true;
    }

    /**
     * Gets a value for a specified index
     * @param i Row-coordinate
     * @param j Column-coordinate
     * @return Value stored at specified index
     */
    public Integer get(int i, int j) {
        return matrix[i][j];
    }

    /**
     * Set the value for a specified index
     * @param i     Row-coordinate
     * @param j     Column-coordinate
     * @param value Value to be set
     */
    public void set(int i, int j, Integer value) throws RuntimeException {
        matrix[i][j] = value;
    }

    /**
     * Get the size of the matrix
     * @return size
     */
    public int getSize() {
        return size;
    }

    // -- Override --

    /**
     * Turn the matrix into a string
     * @return A grid of the matrix
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                builder.append("\n");
            }

            for (int j = 0; j < size; j++) {
                builder.append(get(i, j));
                builder.append(" \t");
            }
        }
        return builder.toString();
    }
}
