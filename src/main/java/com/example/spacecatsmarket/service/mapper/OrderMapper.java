package com.example.spacecatsmarket.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.spacecatsmarket.domain.Order;
import com.example.spacecatsmarket.dto.order.OrderDto;


@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface OrderMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "products", source = "products")
    OrderDto toOrderDto(Order order);
}
