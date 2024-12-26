package com.example.spacecatsmarket.web.exception;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder(toBuilder = true)
@Jacksonized
public class Violation {
    String field; // Field where the validation error occurred
    String message; // Explanation of the validation error
}

