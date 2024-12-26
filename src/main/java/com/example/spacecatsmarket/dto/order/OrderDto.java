package com.example.spacecatsmarket.dto.order;

import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.product.ProductDetailsListDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.UUID;

@Value
@Builder
@Jacksonized
public class OrderDto {

    UUID id;

    @NotBlank(message = "Order status could not be blank")
    @Size(min = 3, max = 20, message = "Order status must be between 3 and 20 characters")
    String status;

    @NotBlank(message = "Customer could not be blank")
    @Valid 
    CustomerDetailsDto customer;

    @NotBlank(message = "Product list could not be blank")
    @Valid 
    ProductDetailsListDto products;
}