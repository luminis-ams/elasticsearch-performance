package eu.luminis.performance.elasticsearch;

import org.apache.log4j.BasicConfigurator;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class App {

    private RandomProductGenerator randomProductGenerator = new RandomProductGenerator(24);
    private Elasticsearch elasticsearch = Elasticsearch.instance("es-performance");

    private static final String NESTED = "nested";
    private static final String PRODUCT = "product";
    private static final String OFFER = "offer";

    private App() throws UnknownHostException {
    }

    public static void main(String[] args) throws IOException, ScriptException, ExecutionException, InterruptedException {

        BasicConfigurator.configure();

        new App()
                .preLoadRandomData()
                .createIndexes()
                .fillIndexes();
    }

    private App createIndexes() throws ExecutionException, InterruptedException {

        elasticsearch.deleteIndex(NESTED);
        elasticsearch.deleteIndex(PRODUCT);
        elasticsearch.deleteIndex(OFFER);

        elasticsearch
                .ifIndexExists(NESTED, () -> elasticsearch.createIndex(NESTED))
                .ifIndexExists(PRODUCT, () -> elasticsearch.createIndex(PRODUCT))
                .ifIndexExists(OFFER, () -> elasticsearch.createIndex(OFFER));

        return this;
    }

    private App preLoadRandomData() throws IOException {
        randomProductGenerator.addDataSource(new File(this.getClass().getResource("/noun.food").getPath()), 10);
        randomProductGenerator.addDataSource(new File(this.getClass().getResource("/noun.animal").getPath()), 10);
        return this;
    }

    private App fillIndexes() {

        List<Nested> nestedProducts = randomProductGenerator.getProducts(1000);
        elasticsearch.bulkIndex(NESTED, nestedProducts);

        List<Product> products = nestedProducts.stream().map(nested -> (Product) nested).collect(Collectors.toList());
        elasticsearch.bulkIndex(PRODUCT, products);

        List<Offer> offers = nestedProducts.stream().flatMap(nested -> nested.getOffers().stream()).collect(Collectors.toList());
        elasticsearch.bulkIndex(OFFER, offers);

        return this;
    }
}
