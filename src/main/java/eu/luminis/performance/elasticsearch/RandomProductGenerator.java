package eu.luminis.performance.elasticsearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomProductGenerator {

    private final Random random;
    private final List<List<WordNetFileReader.Row>> wordRowsList = new ArrayList<>();

    public RandomProductGenerator(long seed) {
        this.random = new Random(seed);
    }

    public RandomProductGenerator addDataSource(File dataSource, int startingLine) throws IOException {
        wordRowsList.add(WordNetFileReader.getRows(dataSource, startingLine));
        return this;
    }

    public Nested getProduct(){
        if(wordRowsList.isEmpty()) throw new IllegalStateException("Add some dataSources first please");

        int firstRandom = random.nextInt(2500);
        int secondRandom = random.nextInt(2500);

        return (Nested) new Nested()
                .setName(getWordCombination(firstRandom, secondRandom))
                .setDescription(getSentence(firstRandom, secondRandom));
    }

    private String getWordCombination(int firstRandom, int secondRandom) {
        return String.format("%s %s", wordRowsList.get(0).get(firstRandom).getWord(), wordRowsList.get(1).get(secondRandom).getWord());
    }

    private String getSentence(int firstRandom, int secondRandom) {
        return String.format("%s %s", wordRowsList.get(0).get(firstRandom).getSentence(), wordRowsList.get(1).get(secondRandom).getSentence());
    }
}