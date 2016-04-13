package org.luminis.performance.elasticsearch;

public class Document extends Offer {
    private Product itemOffered;

    public Product getItemOffered() {
        return itemOffered;
    }

    public Document setItemOffered(Product itemOffered) {
        this.itemOffered = itemOffered;
        return this;
    }
}
