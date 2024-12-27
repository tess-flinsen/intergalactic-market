package com.example.spacecatsmarket.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.service.CosmoCatService;
import com.example.spacecatsmarket.service.mapper.CustomerMapper;

import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CosmoCatServiceImpl implements CosmoCatService {

    CustomerRepository repository;
    CustomerMapper mapper;

    @Override
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    @Transactional(readOnly = true)
    public List<String> getCosmoCats() {
        try {
            return mapper.fromCustomerEntities(repository.findAll()).stream()
                .map(c -> c.getName())
                .toList();
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
}