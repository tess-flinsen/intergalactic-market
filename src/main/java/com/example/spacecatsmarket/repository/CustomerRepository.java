package com.example.spacecatsmarket.repository;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.spacecatsmarket.repository.entity.CustomerEntity;

@Repository
public interface CustomerRepository extends ListCrudRepository<CustomerEntity, Long> {}