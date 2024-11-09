package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDetailsDto;
import com.example.spacecatsmarket.dto.product.ProductEntry;
import com.example.spacecatsmarket.dto.product.ProductDetailsListDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface ProductMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "owner", source = "owner")
    ProductDetailsDto toProductDetailsDto(Product product);

    default ProductDetailsListDto toProductDetailsListDto(List<Product> products) {
        return ProductDetailsListDto.builder().products(toProductEntries(products)).build();
    }

    List<ProductEntry> toProductEntries(List<Product> product);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "owner", source = "owner")
    ProductEntry toProductEntry(Product product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "owner", source = "owner")
    Product toProduct(ProductDetailsDto productDetailsDto);
}