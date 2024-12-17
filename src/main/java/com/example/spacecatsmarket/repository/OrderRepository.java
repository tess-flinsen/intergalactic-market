package com.example.spacecatsmarket.repository;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.spacecatsmarket.repository.entity.OrderEntity;

@Repository
public interface OrderRepository extends CrudRepository<OrderEntity, UUID> {}
