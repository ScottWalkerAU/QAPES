package me.scottwalkerau.qapes;

import lombok.extern.log4j.Log4j2;
import me.scottwalkerau.qapes.algorithm.AlgorithmTree;
import me.scottwalkerau.qapes.algorithm.Converter;
import me.scottwalkerau.qapes.population.ListPopulation;
import me.scottwalkerau.qapes.population.Population;
import me.scottwalkerau.qapes.population.TreePopulation;
import me.scottwalkerau.qapes.qap.Instance;
import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Quadratic Assignment Problem, an Enhanced Solver
 * @author Scott Walker
 */
@Log4j2
public class QAPES {

    /**
     * Entry point for the QAPES program
     * @param args Instances that should be used to generate the heuristic
     */
    public static void main(String[] args) {
        CommandLine cmd = getCommandLine(args);
        run(cmd);
    }

    /**
     * Run with a set of instances
     * @param cmd CommandLine arguments
     */
    private static void run(CommandLine cmd) {
        long startTime = System.currentTimeMillis();
        log.debug("Program starting");

        // Get instances from input
        ArrayList<Instance> instances = new ArrayList<>();
        for (String ins : cmd.getOptionValue("instances").split(",")) {
            instances.add(getInstance(ins));
        }

        // Create population. Can be Tree or List.
        Population pop;
        switch (cmd.getOptionValue("population")) {
            case "list":
                log.debug("Using a List population");
                pop = new ListPopulation(instances);
                break;
            case "tree":
                log.debug("Using a Tree population");
                pop = new TreePopulation(instances);
                break;
            default:
                throw new IllegalArgumentException("Population type may only be list or tree");
        }

        // Start population. May have algorithms loaded in.
        try {

            ArrayList<AlgorithmTree> loaded = new ArrayList<>();
            if (cmd.hasOption("load")) {
                for (String load : cmd.getOptionValue("load").split(",")) {
                    log.debug("Loading " + load);
                    loaded.add(Converter.load(load));
                }
            }
            pop.run(loaded);

        } catch (InterruptedException e) {
            log.error(e);
        }

        // Finalise
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long seconds = elapsed % 60;
        long minutes = elapsed / 60;
        log.debug(String.format("Program finished, ran for %dm %ds", minutes, seconds));
    }

    /**
     * Get an instance object from a given name
     * @param name Name of the instance (Can include prefix and suffix)
     * @return Instance for the given name
     */
    private static Instance getInstance(String name) {
        final String prefix = "instances/";
        final String suffix = ".ins";

        if (!name.startsWith(prefix))
            name = prefix + name;

        if (!name.endsWith(suffix))
            name = name + suffix;

        try {
            return new Instance(name);
        } catch (FileNotFoundException e) {
            log.error(e);
            System.exit(1);
            return null;
        }
    }

    /**
     * Get the command line arguments into a file
     * @param args Command line input
     * @return CommandLine object, after validation occurred
     */
    private static CommandLine getCommandLine(String[] args) {

        Options options = new Options();

        // Which instances to use
        Option input = new Option("i", "instances", true, "Instance names separated by commas");
        input.setRequired(true);
        options.addOption(input);

        // What population type to run
        Option population = new Option("p", "population", true, "Population type. Accepts 'list' or 'tree'");
        population.setRequired(true);
        options.addOption(population);

        // Optional loadable algorithms
        Option loaded = new Option("l", "load", true, "Saved algorithms to load separated by spaces");
        loaded.setRequired(false);
        options.addOption(loaded);

        try {
            return new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            log.error(e.getMessage());
            new HelpFormatter().printHelp("QAPES", options);
            System.exit(1);
            return null;
        }
    }
}
