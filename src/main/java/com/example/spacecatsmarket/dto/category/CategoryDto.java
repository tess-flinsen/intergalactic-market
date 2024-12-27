package com.example.spacecatsmarket.dto.category;

import com.example.spacecatsmarket.dto.validation.CosmicWord;
import com.example.spacecatsmarket.dto.validation.ExtendedValidation;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@GroupSequence({CategoryDto.class, ExtendedValidation.class})
public class CategoryDto {
    int id;

    @Size(min = 5, message = "Category name could not be shorter than 5 characters")
    @Size(max = 30, message = "Category name could not be longer than 30 characters")
    @CosmicWord(groups = ExtendedValidation.class)
    String name;
}