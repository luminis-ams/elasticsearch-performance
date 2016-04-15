package eu.luminis.performance.elasticsearch;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

public class App {

    private RandomProductGenerator randomProductGenerator = new RandomProductGenerator(10);
    private Elasticsearch elasticsearch = Elasticsearch.instance("es-performance");

    private static final String NESTED = "NESTED";
    private static final String PRODUCT = "PRODUCT";
    private static final String OFFER = "OFFER";

    private App() throws UnknownHostException {
    }

    public static void main(String[] args) throws IOException, ScriptException {

        new App()
                .fillIndexes()
                .createIndexes();
    }

    private App createIndexes() {
        elasticsearch
                .ifIndexExists(NESTED, () -> elasticsearch.createIndex(NESTED))
                .ifIndexExists(PRODUCT, () -> elasticsearch.createIndex(PRODUCT))
                .ifIndexExists(OFFER, () -> elasticsearch.createIndex(OFFER));

        return this;
    }

    private App fillIndexes() throws IOException {
        randomProductGenerator.addDataSource(new File(this.getClass().getResource("/noun.food").getPath()), 10);
        randomProductGenerator.addDataSource(new File(this.getClass().getResource("/noun.animal").getPath()), 10);

        System.out.println(randomProductGenerator.getProduct());

        return this;
    }
}
