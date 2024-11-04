package com.example.spacecatsmarket.dto.product;

import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class ProductDetailsListDto {
    List<ProductEntry> products;
}
