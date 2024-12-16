package com.example.spacecatsmarket.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.service.CosmoCatService;

import java.util.List;

@Slf4j
@Service
public class CosmoCatServiceImpl implements CosmoCatService {

    @Override
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public List<String> getCosmoCats() {
        log.info("Fetching list of Cosmo Cats...");
        return List.of(
            "Milky Way Mittens",
            "Galactic Purr",
            "Nebula Fluff",
            "Starlight Whiskers"
        );
    }
}
