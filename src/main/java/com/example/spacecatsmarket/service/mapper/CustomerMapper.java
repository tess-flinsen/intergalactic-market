package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsEntry;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsListDto;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface CustomerMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    CustomerDetailsDto toCustomerDetailsDto(Customer customer);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    CustomerDetailsEntry toCustomerDetailsEntry(Customer customer);

    default CustomerDetailsListDto toCustomerDetailsListDto(List<Customer> customers) {
        return CustomerDetailsListDto.builder().customerDetailsEntries(toCustomerDetailsEntries(customers)).build();
    }

    List<CustomerDetailsEntry> toCustomerDetailsEntries(List<Customer> customers);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    Customer fromCustomerDetailsEntry(CustomerDetailsEntry customerDetailsEntry);

    List<Customer> fromCustomerEntities(List<CustomerEntity> customers);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "customer.name")
    @Mapping(target = "address", source = "customer.address")
    @Mapping(target = "phoneNumber", source = "customer.phoneNumber")
    @Mapping(target = "email", source = "customer.email")
    CustomerEntity toCustomerEntity(Customer customer);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    Customer fromCustomerEntity(CustomerEntity customerEntity);
}