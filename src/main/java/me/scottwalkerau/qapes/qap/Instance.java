package me.scottwalkerau.qapes.qap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Object to hold parsed data for a QAP instance
 * @author Scott Walker
 */
public class Instance {

    /** The file-name used */
    private final String fileName;
    /** The name of the instance */
    private final String insName;
    /** The distance matrix */
    private final SquareMatrix distance;
    /** The flow matrix */
    private final SquareMatrix flow;
    /** The size of the instance and flow matrices */
    private final int size;
    /** The best known value for a solution to this instance */
    private final long bkv;
    /** If both matrices are symmetric */
    private final boolean symmetric;

    /**
     * Constructor that reads in data from a specified file
     * @param name The file to be read in
     */
    public Instance(String name) throws FileNotFoundException {
        // Get file from resources
        InputStream file = ClassLoader.getSystemResourceAsStream(name);
        // If it doesn't exist in there, use instances/ folder in root directory
        if (file == null) {
            file = new FileInputStream(new File(name));
        }

        Scanner scanner = new Scanner(file);

        // Read in Size and Best Known Value
        size = scanner.nextInt();
        bkv = scanner.nextInt();

        // Read in flows
        flow = new SquareMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                flow.set(i, j, scanner.nextInt());
            }
        }

        // Read in distances
        distance = new SquareMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                distance.set(i, j, scanner.nextInt());
            }
        }

        // Set remaining attributes
        String[] split = name.split("/");
        fileName = split[split.length - 1];
        insName = fileName.split("\\.")[0];
        symmetric = distance.isSymmetric() && flow.isSymmetric();

        // TODO catch errors in instance file? Currently just hard errors.
        scanner.close();
    }

    // -- Getters --

    /**
     * Get the file name
     * @return fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Get the instance name
     * @return insName
     */
    public String getInsName() {
        return insName;
    }

    /**
     * Get the distance matrix
     * @return distance
     */
    public SquareMatrix getDistance() {
        return distance;
    }

    /**
     * Get the flow matrix
     * @return flow
     */
    public SquareMatrix getFlow() {
        return flow;
    }

    /**
     * Get the size of the matrices
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the Best Known Value
     * @return bkv
     */
    public long getBkv() {
        return bkv;
    }

    /**
     * Get if the matrices are symmetric
     * @return True if both matrices are symmetric
     */
    public boolean isSymmetric() {
        return symmetric;
    }
    
    // -- Override --

    /**
     * Turn the instance into a string
     * @return Formatted instance data
     */
    @Override
    public String toString() {
        String str = String.format("Size: %d", getSize());
        str += String.format("\nFlow:\n%s", getFlow());
        str += String.format("\nDistance:\n%s", getDistance());
        return str;
    }
}
