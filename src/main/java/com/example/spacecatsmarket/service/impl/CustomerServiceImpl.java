package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import com.example.spacecatsmarket.service.CustomerService;
import com.example.spacecatsmarket.service.exception.CustomerNotFoundException;
import com.example.spacecatsmarket.service.mapper.CustomerMapper;

import jakarta.persistence.PersistenceException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    final CustomerRepository repository;
    final CustomerMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Customer getCustomerDetailsById(Long id) {
        Optional<CustomerEntity> customerEntityOptional = null;
        try {
            customerEntityOptional = repository.findById(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        if (customerEntityOptional.isEmpty()) {
            throw new CustomerNotFoundException(id);
        }
        return mapper.fromCustomerEntity(customerEntityOptional.get());
    }

    @Override
    public Customer createCustomer(Customer customer) {
        try {
            return mapper.fromCustomerEntity(repository.save(mapper.toCustomerEntity(customer)));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void deleteCustomerById(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            repository.save(mapper.toCustomerEntity(customer));
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}
