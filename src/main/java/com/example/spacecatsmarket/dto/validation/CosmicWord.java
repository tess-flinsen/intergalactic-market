package com.example.spacecatsmarket.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE_USE, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface CosmicWord {
    String PRODUCT_NAME_SHOULD_BE_VALID = "\"Product name must contain at least one cosmic term such as 'stellar', 'galactic', 'cosmic', 'nebula', 'orbit', 'meteor', 'asteroid', 'comet', 'universe', 'solar', 'planetary', 'celestial', 'quasar', 'eclipse', 'lunar', 'equinox', 'zodiac', or 'interstellar'.\"\n";
    
    String message() default PRODUCT_NAME_SHOULD_BE_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
