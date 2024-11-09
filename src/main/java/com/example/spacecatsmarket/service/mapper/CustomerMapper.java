package com.example.spacecatsmarket.service.mapper;

import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsDto;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsEntry;
import com.example.spacecatsmarket.dto.customer.CustomerDetailsListDto;
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

    default CustomerDetailsListDto toCustomerDetailsListDto(List<Customer> customers) {
        return CustomerDetailsListDto.builder().customerDetailsEntries(toCustomerDetailsEntries(customers)).build();
    }

    List<CustomerDetailsEntry> toCustomerDetailsEntries(List<Customer> customers);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    CustomerDetailsEntry toCustomerDetailsEntry(Customer customer);
}