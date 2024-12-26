package com.example.spacecatsmarket.config;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.example.spacecatsmarket.service.mapper.CategoryMapper;
import com.example.spacecatsmarket.service.mapper.ProductMapper;


@TestConfiguration
public class MappersTestConfiguration {

    @Bean
    public ProductMapper productMapper() {
        return Mappers.getMapper(ProductMapper.class);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return Mappers.getMapper(CategoryMapper.class);
    }
}
