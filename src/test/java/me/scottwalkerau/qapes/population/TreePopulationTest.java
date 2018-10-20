package me.scottwalkerau.qapes.population;

import me.scottwalkerau.qapes.qap.Instance;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TreePopulationTest {

    @Test
    public void testTreePopulation() throws FileNotFoundException, InterruptedException {
        Instance instance = new Instance("instances/chr25a.ins");
        TreePopulation pop = new TreePopulation(Arrays.asList(instance));
        assertEquals(1, pop.getInstances().size());

        pop.initialise(new ArrayList<>());
        assertNotNull(pop.getPopulation());
    }
}