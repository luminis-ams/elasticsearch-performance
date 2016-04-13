package eu.luminis.performance.elasticsearch;

import java.net.URL;

public class Product {

    private String id;

    private String name;
    private URL url;
    private String description;
    private String brand;
    private AggregateRating aggregateRating;

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public URL getUrl() {
        return url;
    }

    public Product setUrl(URL url) {
        this.url = url;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Product setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public AggregateRating getAggregateRating() {
        return aggregateRating;
    }

    public Product setAggregateRating(AggregateRating aggregateRating) {
        this.aggregateRating = aggregateRating;
        return this;
    }
}
