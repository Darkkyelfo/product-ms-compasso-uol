package com.uol.compasso.productms.exception;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(Long id) {
        super(String.format("The Product %s NOT EXISTS", id));
    }
}
