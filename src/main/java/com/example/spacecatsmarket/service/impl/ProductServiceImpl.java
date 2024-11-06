package com.example.spacecatsmarket.service.impl;

import com.example.spacecatsmarket.domain.Category;
import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.service.CustomerService;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.service.exception.PermissionDeniedException;
import com.example.spacecatsmarket.service.exception.ProductListEmptyException;
import com.example.spacecatsmarket.service.exception.ProductNotFoundException;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> products = buildMockProducts();
    private final CustomerService customerService;

    public ProductServiceImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public List<Product> getAllProducts() {
        if (products.isEmpty()){
            throw new ProductListEmptyException();
        }
        return new ArrayList<>(products);
    }
    
    @Override
    public Product getProductById(UUID id) {
        return products.stream()
            .filter(product -> product.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> {
                return new ProductNotFoundException(id);
            });
    }

    @Override
    public UUID createProduct(Product product, Long requesterId) {
        Customer owner = customerService.getCustomerDetailsById(requesterId);
        Product newProduct = product.toBuilder()
            .id(UUID.randomUUID())
            .owner(owner)
            .build();
        products.add(newProduct);
        log.info("Product created with ID: {}.", newProduct.getId());
        return newProduct.getId();
    }
    
    @Override
    public void updateProduct(Product product, Long requesterId) {
        Product existingProduct = getProductById(product.getId());
        Long ownerId = existingProduct.getOwner().getId();
        if (!ownerId.equals(requesterId)) {
            throw new PermissionDeniedException(requesterId, "update", product.getId());
        }
    
        Product updatedProduct = existingProduct.toBuilder()
                .name(product.getName() != null ? product.getName() : existingProduct.getName())
                .description(product.getDescription() != null ? product.getDescription() : existingProduct.getDescription())
                .price(product.getPrice() != -1.0 ? product.getPrice() : existingProduct.getPrice())  // -1.0 is an "unset" value flag
                .stockCount(product.getStockCount() != -1 ? product.getStockCount() : existingProduct.getStockCount())  // -1 is an "unset" value flag
                .category(product.getCategory() != null ?  product.getCategory() : existingProduct.getCategory())
                .build();
        products.set(products.indexOf(existingProduct), updatedProduct);
        log.info("Product with ID: {} was updated.", product.getId());   
    }

    @Override
    public void deleteProductById(UUID productId, Long requesterId) {
        Product product = getProductById(productId);
        Customer owner = product.getOwner();
        if (!owner.getId().equals(requesterId)){
            throw new PermissionDeniedException(requesterId, "delete", productId);
        }
        products.remove(product);
        log.info("Product with ID: {} was deleted.", productId);
    }

    private List<Product> buildMockProducts() {
        Category food = Category.builder()
            .id(1)
            .name("Planetary Foods")
            .build();
        Category toys = Category.builder()
            .id(2)
            .name("Cosmic Toys")
            .build();

        Customer customer1 = customerService.getCustomerDetailsById(1L);
        Customer customer2 = customerService.getCustomerDetailsById(2L);
        Customer customer3 = customerService.getCustomerDetailsById(3L);
        log.info("Mock products are being created");  
        
        return List.of(
            Product.builder()
                .id(UUID.randomUUID())
                .name("Galactic Milk")
                .description("Fresh milk from the Andromeda galaxy")
                .price(25.99)
                .stockCount(50)
                .category(food)
                .owner(customer1)
                .build(),
            Product.builder()
                .id(UUID.randomUUID())
                .name("Meteor Yarn Ball")
                .description("Perfect for space cats to play with")
                .price(15.99)
                .stockCount(100)
                .category(toys)
                .owner(customer2)
                .build(),
            Product.builder()
                .id(UUID.randomUUID())
                .name("Intergalactic Cat Treats")
                .description("Tasty treats for space-faring cats")
                .price(12.50)
                .stockCount(200)
                .category(food)
                .owner(customer3)
                .build(),
            Product.builder()
                .id(UUID.randomUUID())
                .name("Meteor Munchies")
                .description("Crunchy meteor-shaped snacks loved by cats")
                .price(9.99)
                .stockCount(150)
                .category(food)
                .owner(customer1)
                .build(),
            Product.builder()
                .id(UUID.randomUUID())
                .name("Zodiac Wand")
                .description("Magic wand toy that keeps cats entertained for lightyears")
                .price(59.99)
                .stockCount(75)
                .category(toys)
                .owner(customer2)
                .build()
        ); 
    }
}
