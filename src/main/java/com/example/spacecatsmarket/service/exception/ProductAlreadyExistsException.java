package com.example.spacecatsmarket.service.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Product with name %s already exists";

    public ProductAlreadyExistsException(String productName) {
      super(String.format(DEFAULT_MESSAGE, productName));
    }
  }
