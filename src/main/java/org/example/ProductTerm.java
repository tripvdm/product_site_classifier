package org.example;

public enum ProductTerm {
    FAVORITE("favorite"),
    LIKE("like"),
    BUY("buy"),
    CART("cart"),
    BASKET("basket"),
    PRICE("price"),
    BOOK("book");

    private final String term;
    ProductTerm(String term) {
        this.term = term;
    }

    public String getTerm() {
        return term;
    }
}