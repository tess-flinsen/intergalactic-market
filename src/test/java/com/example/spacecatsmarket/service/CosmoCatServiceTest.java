package com.example.spacecatsmarket.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import com.example.spacecatsmarket.config.FeatureToggleProperties;
import com.example.spacecatsmarket.featuretoggle.FeatureToggleExtension;
import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.spacecatsmarket.featuretoggle.annotation.EnabledFeatureToggle;
import com.example.spacecatsmarket.featuretoggle.aspect.FeatureToggleAspect;
import com.example.spacecatsmarket.featuretoggle.exception.FeatureToggleNotEnabledException;
import com.example.spacecatsmarket.featuretoggle.service.FeatureToggleService;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import com.example.spacecatsmarket.service.impl.CosmoCatServiceImpl;
import com.example.spacecatsmarket.service.impl.CustomerServiceImpl;
import com.example.spacecatsmarket.service.mapper.CustomerMapperImpl;

@SpringBootTest(
    classes = {
      CosmoCatServiceImpl.class,
      CustomerServiceImpl.class,
      FeatureToggleService.class,
      FeatureToggleAspect.class,
      CustomerMapperImpl.class
    })
@DisplayName("Cosmo Cat Service Tests")
@ExtendWith(FeatureToggleExtension.class)
@EnableAspectJAutoProxy
public class CosmoCatServiceTest {
    @MockBean private CustomerRepository customerRepository;
    @Autowired private CosmoCatService cosmoCatService;

    @MockBean private FeatureToggleProperties featureToggleProperties;

    private static List<String> catsNames = List.of("John Doe");

    @BeforeEach
    private void setUp() {
        Map<String, Boolean> map = new HashMap<>();
        for (FeatureToggles toggle : FeatureToggles.values()) {
            map.put(toggle.getFeatureName(), false);
        }
        featureToggleProperties.setToggles(map);
    }

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldThrowFeatureNotAvailableException() {
        assertThrows(FeatureToggleNotEnabledException.class, cosmoCatService::getCosmoCats);
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldReturnNames() {
        CustomerEntity testEntity = CustomerEntity.builder().id(1L).name(catsNames.get(0)).build();
        when(customerRepository.findAll()).thenReturn(List.of(testEntity));
        assertEquals(catsNames, cosmoCatService.getCosmoCats());
    }
}