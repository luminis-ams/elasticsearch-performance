package org.luminis.performance.elasticsearch;

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
}
