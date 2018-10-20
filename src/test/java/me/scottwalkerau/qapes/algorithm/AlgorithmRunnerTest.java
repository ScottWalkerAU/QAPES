package me.scottwalkerau.qapes.algorithm;

import me.scottwalkerau.qapes.block.BlockType;
import me.scottwalkerau.qapes.qap.Instance;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class AlgorithmRunnerTest {

    @Test
    public void run() throws FileNotFoundException, InterruptedException {
        Instance instance = new Instance("instances/chr25a.ins");
        AlgorithmTree tree = new AlgorithmTree(new AlgorithmNode(null, BlockType.VOID.getBlock()));
        AlgorithmRunner runner = new AlgorithmRunner(instance, tree);

        runner.run();
        runner.join();

        assertNotNull(runner.getResult());
        assertFalse(runner.isAlive());
    }
}