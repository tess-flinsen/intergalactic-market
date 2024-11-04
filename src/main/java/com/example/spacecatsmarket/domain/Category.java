package com.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Category {
    int id;
    String name;
}