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
@Constraint(validatedBy = SpaceAddressValidator.class)
@Documented
public @interface ValidSpaceAddress {

    String SPACE_ADDRESS_SHOULD_BE_VALID = "\"Invalid Space Address: The provided address does not conform to the required format. Please ensure that it includes valid coordinates, sector, and planet information. Example: 'Sector 5, Planet Zeta, Quadrant 12'.\"\n";

    String message() default SPACE_ADDRESS_SHOULD_BE_VALID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}