package com.example.spacecatsmarket.service.exception;

public class CustomerNotFoundException extends RuntimeException {
    private static final String CUSTOMER_NOT_FOUND_MESSAGE = "Customer with id %s not found";

    public CustomerNotFoundException(Long customerId) {
        super(String.format(CUSTOMER_NOT_FOUND_MESSAGE, customerId));
    }
}