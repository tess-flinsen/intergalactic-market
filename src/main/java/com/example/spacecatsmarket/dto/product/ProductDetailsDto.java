package com.example.spacecatsmarket.dto.product;

import com.example.spacecatsmarket.dto.validation.ExtendedValidation;
import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.validation.CosmicWord;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@GroupSequence({ProductDetailsDto.class, ExtendedValidation.class})
public class ProductDetailsDto {

    @NotBlank(message = "Product name could not be blank")
    @Size(min = 2, message = "Product name could not be shorter than 2 characters")
    @Size(max = 30, message = "Product name could not be longer than 30 characters")
    @CosmicWord(groups = ExtendedValidation.class)
    String name;

    @Size(min = 10, message = "Product description could not be shorter than 10 characters")
    @Size(max = 300, message = "Product description could not be longer than 300 characters")
    String description;

    @NotBlank(message = "Price could not be blank")
    @DecimalMin(value = "0.01", message = "Price could not be less than 0.01")
    @DecimalMax(value = "100000", message = "Price could not exceed 100,000")
    double price;

    @Min(value = 0, message = "Stock count could not be less than 0")
    @Max(value = 10000, message = "Stock count could not exceed 10,000")
    int stockCount;

    @NotBlank(message = "Category could not be blank")
    @Valid 
    CategoryDto category;

    @NotBlank(message = "Owner could not be blank")
    @Valid 
    CustomerDetailsDto owner;
}
