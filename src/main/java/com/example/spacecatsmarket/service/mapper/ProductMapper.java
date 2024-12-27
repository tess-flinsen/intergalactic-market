package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDetailsDto;
import com.example.spacecatsmarket.dto.product.ProductEntry;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.dto.product.ProductDetailsListDto;
import java.util.List;
import java.util.UUID;

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

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category.id")
    @Mapping(target = "owner", source = "owner.id")
    ProductEntry toProductEntry(Product product);

    default ProductDetailsListDto toProductDetailsListDto(List<Product> products) {
        return ProductDetailsListDto.builder().products(toProductEntries(products)).build();
    }

    List<ProductEntry> toProductEntries(List<Product> product);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "owner", source = "owner")
    Product toProduct(ProductDetailsDto productDetailsDto);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    Product fromProductDetailsDto(ProductDetailsDto productDetailsDto);

    @Mapping(target = "id", expression = "java(handleId(product.getId()))")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "owner", source = "owner")
    ProductEntity toProductEntity(Product product);

    default UUID handleId(UUID id) {
        return id != null ? id : UUID.randomUUID();
    }

    List<Product> fromProductEntities(List<ProductEntity> productEntities);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "stockCount", source = "stockCount")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "owner", source = "owner")
    Product fromProductEntity(ProductEntity productEntity);
}