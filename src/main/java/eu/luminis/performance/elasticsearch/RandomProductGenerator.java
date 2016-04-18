package eu.luminis.performance.elasticsearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomProductGenerator {

    private static final Logger logger = LoggerFactory.getLogger(RandomProductGenerator.class);

    private final Random random;
    private final List<List<WordNetFileReader.Row>> wordRowsList = new ArrayList<>();

    public RandomProductGenerator(long seed) {
        this.random = new Random(seed);
    }

    public RandomProductGenerator addDataSource(File dataSource, int startingLine) throws IOException {
        logger.info("Loading datasource {} into memory", dataSource.getName());
        wordRowsList.add(WordNetFileReader.getRows(dataSource, startingLine));
        return this;
    }

    public List<Nested> getProducts(int count){
        logger.info("Generating {} randomized products", count);
        List<Nested> products = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            products.add(getProduct());
        }

        return products;
    }

    public Nested getProduct(){
        if(wordRowsList.isEmpty()) throw new IllegalStateException("Add some dataSources first please");

        int firstRandom = random.nextInt(2500);
        int secondRandom = random.nextInt(2500);

        Nested product = (Nested) new Nested()
                .setName(getWordCombination(firstRandom, secondRandom))
                .setDescription(getSentence(firstRandom, secondRandom));

        return addOffers(product);
    }

    private Nested addOffers(Nested product) {
        int offerCount = random.nextInt(50);
        List<Offer> offers = new ArrayList<>(offerCount);

        for(int i = 0; i < offerCount; i++) {
            offers.add(new Offer()
                    .setPrice(random.nextInt(500))
                    .setProduct(product));
        }

        return product.setOffers(offers);
    }

    private String getWordCombination(int firstRandom, int secondRandom) {
        return String.format("%s %s", wordRowsList.get(0).get(firstRandom).getWord(), wordRowsList.get(1).get(secondRandom).getWord());
    }

    private String getSentence(int firstRandom, int secondRandom) {
        return String.format("%s %s", wordRowsList.get(0).get(firstRandom).getSentence(), wordRowsList.get(1).get(secondRandom).getSentence());
    }
}