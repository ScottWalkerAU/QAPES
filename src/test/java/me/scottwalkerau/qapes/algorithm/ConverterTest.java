package me.scottwalkerau.qapes.algorithm;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void load() {
        try {
            Converter.load("");
            fail();
        } catch (FileNotFoundException e) {}
    }
}