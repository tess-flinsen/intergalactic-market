package com.example.spacecatsmarket.dto.product;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ProductEntry {
    UUID id;
    String name;
    String description;
    double price;
    int stockCount;
    int categoryId;  
    Long ownerId; 
}