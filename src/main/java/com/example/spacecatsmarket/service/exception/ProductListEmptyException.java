package com.example.spacecatsmarket.service.exception;

public class ProductListEmptyException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The products list is empty.";

    public ProductListEmptyException() {
        super(DEFAULT_MESSAGE);
    }
}