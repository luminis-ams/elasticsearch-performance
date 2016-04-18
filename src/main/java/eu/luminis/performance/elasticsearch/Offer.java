package eu.luminis.performance.elasticsearch;

import java.time.LocalDate;

public class Offer {
    private String id;

    private long price;
    private String priceCurrency;
    private LocalDate priceValidUntil;
    private ItemAvailability availability;
    private String url;
    private Product product;

    public String getId() {
        return id;
    }

    public Offer setId(String id) {
        this.id = id;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public Offer setPrice(long price) {
        this.price = price;
        return this;
    }

    public String getPriceCurrency() {
        return priceCurrency;
    }

    public Offer setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
        return this;
    }

    public LocalDate getPriceValidUntil() {
        return priceValidUntil;
    }

    public Offer setPriceValidUntil(LocalDate priceValidUntil) {
        this.priceValidUntil = priceValidUntil;
        return this;
    }

    public ItemAvailability getAvailability() {
        return availability;
    }

    public Offer setAvailability(ItemAvailability availability) {
        this.availability = availability;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Offer setUrl(String url) {
        this.url = url;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Offer setProduct(Product product) {
        this.product = product;
        return this;
    }
}
