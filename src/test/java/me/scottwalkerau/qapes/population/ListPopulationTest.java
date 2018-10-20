package me.scottwalkerau.qapes.population;

import me.scottwalkerau.qapes.qap.Instance;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ListPopulationTest {

    @Test
    public void testListPopulation() throws FileNotFoundException {
        Instance instance = new Instance("instances/chr25a.ins");
        ListPopulation pop = new ListPopulation(Arrays.asList(instance));
        assertEquals(1, pop.getInstances().size());

        pop.initialise(new ArrayList<>());
        assertNotNull(pop.getPopulation());
    }
}