package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.Customer;
import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomerDetails();

    Customer getCustomerDetailsById(Long customerId);
}