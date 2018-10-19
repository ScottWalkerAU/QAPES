package me.scottwalkerau.qapes.algorithm;

import lombok.extern.log4j.Log4j2;
import me.scottwalkerau.qapes.block.Block;
import me.scottwalkerau.qapes.block.BlockType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class to load and save algorithm trees to files
 * @author Scott Walker
 */
@Log4j2
public class Converter {

    /** Folder to save in */
    private static final String FOLDER = "saved/";
    /** Extension to use */
    private static final String EXTENSION = ".alg";

    /**
     * Save the given algorithm tree to the file specified
     * @param file Name of the file (Without the extension)
     * @param tree Tree to save
     */
    public static void save(String file, AlgorithmTree tree) {
        List<String> output = new ArrayList<>();
        tree = new TreeMinimiser(tree).run();

        saveRecursion(output, tree.getRoot(), "");

        try {
            // Make sure folders exist
            new File(FOLDER).mkdirs();

            // Write to file
            Path path = Paths.get(FOLDER + file + EXTENSION);
            Files.write(path, output);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Recursive function to save the algorithm
     * @param output List of output lines
     * @param node Current node
     * @param prefix The line prefix
     */
    private static void saveRecursion(List<String> output, AlgorithmNode node, String prefix) {
        output.add(prefix + node.getBlockType().name());
        for (AlgorithmNode child : node.getChildren()) {
            saveRecursion(output, child, prefix + " ");
        }
    }

    /**
     * Load an algorithm from file
     * @param file Name of the file without the extension
     * @return Algorithm loaded in
     */
    public static AlgorithmTree load(String file) {
        try {
            Scanner scanner = new Scanner(new File(FOLDER + file + EXTENSION));
            AlgorithmNode root = loadRecursion(scanner, null);
            return new AlgorithmTree(root);
        } catch (FileNotFoundException | NullPointerException e) {
            log.error("Couldn't load " + file + ", an error occurred:", e);
            System.exit(1);
            return null;
        }
    }

    /**
     * Recursive function to load the algorithm
     * @param scanner Scanner
     * @param parent Parent node
     * @return Loaded node
     */
    private static AlgorithmNode loadRecursion(Scanner scanner, AlgorithmNode parent) {
        String type = scanner.nextLine().trim();
        Block block = BlockType.valueOf(type).getBlock();

        // Get the current node and load all its children recursively
        AlgorithmNode node = new AlgorithmNode(parent, block);
        for (int child = 0; child < block.getParamTypes().size(); child++) {
            node.setChild(child, loadRecursion(scanner, node));
        }

        return node;
    }

}
