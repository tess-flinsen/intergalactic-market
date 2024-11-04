package com.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Customer {
    Long id;
    String name;
    String address;
    String phoneNumber;
    String email;
}