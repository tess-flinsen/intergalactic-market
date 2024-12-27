package com.example.spacecatsmarket.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.spacecatsmarket.domain.Category;
import com.example.spacecatsmarket.dto.category.CategoryDto;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Category fromCategoryEntity(CategoryEntity categoryEntity);
}