package com.example.spacecatsmarket.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CosmicWordValidator implements ConstraintValidator<CosmicWord, String> {

    private static final List<String> COSMIC_WORDS = Arrays.asList(
        "galactic", "cosmic", "solar", "star",
        "meteor", "asteroid", "comet", "universe", 
        "planetary", "eclipse", "dimension", "void",
        "lunar", "zodiac", "space", "intergalactic"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; 
        }
       return COSMIC_WORDS.stream().anyMatch(value.toLowerCase()::contains);
    }
}