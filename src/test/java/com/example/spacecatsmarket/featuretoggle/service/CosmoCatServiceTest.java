package com.example.spacecatsmarket.featuretoggle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.spacecatsmarket.config.FeatureToggleProperties;
import com.example.spacecatsmarket.featuretoggle.FeatureToggleExtension;
import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.DisabledFeatureToggle;
import com.example.spacecatsmarket.featuretoggle.annotation.EnabledFeatureToggle;
import com.example.spacecatsmarket.featuretoggle.aspect.FeatureToggleAspect;
import com.example.spacecatsmarket.featuretoggle.exception.FeatureToggleNotEnabledException;
import com.example.spacecatsmarket.service.CosmoCatService;
import com.example.spacecatsmarket.service.impl.CosmoCatServiceImpl;
import com.example.spacecatsmarket.service.impl.CustomerServiceImpl;

@SpringBootTest(
    classes = {
      CosmoCatServiceImpl.class,
      CustomerServiceImpl.class,
      FeatureToggleService.class,
      FeatureToggleAspect.class
    })
@ExtendWith(FeatureToggleExtension.class)
@EnableAspectJAutoProxy
public class CosmoCatServiceTest {
  @Autowired private CosmoCatService cosmoCatService;

  @MockBean private FeatureToggleProperties featureToggleProperties;

  private static List<String> catsNames = List.of(
      "Milky Way Mittens", 
      "Galactic Purr", 
      "Nebula Fluff", 
      "Starlight Whiskers"
  );
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
  void shouldThrowFeatureFeatureToggleNotEnabledException() {
    assertThrows(FeatureToggleNotEnabledException.class, cosmoCatService::getCosmoCats);
  }

  @Test
  @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
  void shouldReturnNames() {
    assertEquals(catsNames, cosmoCatService.getCosmoCats());
  }
}