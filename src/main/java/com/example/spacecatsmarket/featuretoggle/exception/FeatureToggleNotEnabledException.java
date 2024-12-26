package com.example.spacecatsmarket.featuretoggle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FeatureToggleNotEnabledException extends RuntimeException {
    public FeatureToggleNotEnabledException(String featureToggleName) {
        super("Feature " + featureToggleName + " is not enabled.");
    }
}