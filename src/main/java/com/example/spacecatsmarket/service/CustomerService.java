package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.Customer;

public interface CustomerService {
    Customer getCustomerDetailsById(Long id);
    Customer createCustomer(Customer customer);
    void deleteCustomerById(Long id);
    void updateCustomer(Customer customer);
}