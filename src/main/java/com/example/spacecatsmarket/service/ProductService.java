package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.domain.Product;
import java.util.List;
import java.util.UUID;


public interface ProductService {
    UUID createProduct(Product product, Long requesterId);
    List<Product> getAllProducts();
    Product getProductById(UUID id);
    void updateProduct(Product product, Long requesterId);
    void deleteProductById(UUID productId, Long requesterId);
}