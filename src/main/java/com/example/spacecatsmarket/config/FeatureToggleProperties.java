package com.example.spacecatsmarket.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.feature")
public class FeatureToggleProperties {

    private Map<String, Boolean> toggles;

    public boolean check(String featureToggle) {
        return toggles.getOrDefault(featureToggle, false);
    }
}