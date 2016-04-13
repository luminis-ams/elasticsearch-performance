package eu.luminis.performance.elasticsearch;

import java.util.Random;

public class RandomProductGenerator {

    private final Random random;
    private final WordNetFileReader wordNetFileReader;

    public RandomProductGenerator(long seed, WordNetFileReader wordNetFileReader) {
        this.random = new Random(seed);
        this.wordNetFileReader = wordNetFileReader;
    }

    public Nested getProduct(){
        return new Nested();
    }
}