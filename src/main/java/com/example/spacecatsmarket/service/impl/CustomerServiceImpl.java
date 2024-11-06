package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.service.CustomerService;
import com.example.spacecatsmarket.service.exception.CustomerNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final List<Customer> customerDetails = buildAllCustomerDetailsMock();

    @Override
    public List<Customer> getAllCustomerDetails() {
        return customerDetails;
    }

    @Override
    public Customer getCustomerDetailsById(Long customerId) {
        return Optional.of(customerDetails.stream()
            .filter(details -> details.getId().equals(customerId)).findFirst())
            .get()
            .orElseThrow(() -> {
                log.info("Customer with id {} not found in mock", customerId);
                return new CustomerNotFoundException(customerId);
            });
    }

    private List<Customer> buildAllCustomerDetailsMock() {
        return List.of(
            Customer.builder()
                .id(1L)
                .name("Alice Johnson")
                .address("123 Cosmic Lane, Catnip City")
                .phoneNumber("123-456-7890")
                .email("alice@example.com")
                .build(),
            Customer.builder()
                .id(2L)
                .name("Bob Smith")
                .address("456 Galactic Blvd, Star Town")
                .phoneNumber("987-654-3210")
                .email("bob@example.com")
                .build(),
            Customer.builder()
                .id(3L)
                .name("Charlie Brown")
                .address("789 Nebula Road, Space Village")
                .phoneNumber("555-123-4567")
                .email("charlie@example.com")
                .build());
    }
}
