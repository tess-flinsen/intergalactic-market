package com.example.spacecatsmarket.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class SpaceAddressValidator implements ConstraintValidator<ValidSpaceAddress, String> {

    private static final String SPACE_ADDRESS_PATTERN = "^Sector \\d+, Planet [A-Za-z]+, Quadrant \\d+$";

    private static final Pattern pattern = Pattern.compile(SPACE_ADDRESS_PATTERN);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        return pattern.matcher(value).matches();
    }
}