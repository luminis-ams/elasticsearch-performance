package eu.luminis.performance.elasticsearch;

import java.net.URL;
import java.util.List;

public class Nested extends Product {
    private List<Offer> offers;

    public List<Offer> getOffers() {
        return offers;
    }

    public Nested setOffers(List<Offer> offers) {
        this.offers = offers;
        return this;
    }

    @Override
    public Product setId(String id) {
        return super.setId(id);
    }

    @Override
    public Product setName(String name) {
        return super.setName(name);
    }

    @Override
    public Product setUrl(URL url) {
        return super.setUrl(url);
    }

    @Override
    public Product setDescription(String description) {
        return super.setDescription(description);
    }

    @Override
    public Product setBrand(String brand) {
        return super.setBrand(brand);
    }

    @Override
    public Product setAggregateRating(AggregateRating aggregateRating) {
        return super.setAggregateRating(aggregateRating);
    }

    @Override
    public String toString() {
        return "Nested{" +
                "offers=" + offers +
                "} " + super.toString();
    }
}
