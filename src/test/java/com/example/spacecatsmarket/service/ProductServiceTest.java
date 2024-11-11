package com.example.spacecatsmarket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.spacecatsmarket.domain.Category;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.service.exception.PermissionDeniedException;
import com.example.spacecatsmarket.service.exception.CustomerNotFoundException;
import com.example.spacecatsmarket.service.exception.ProductNotFoundException;
import com.example.spacecatsmarket.service.exception.ProductListEmptyException;
import com.example.spacecatsmarket.service.impl.CustomerServiceImpl;
import com.example.spacecatsmarket.service.impl.ProductServiceImpl;

@SpringBootTest(classes = { ProductServiceImpl.class, CustomerServiceImpl.class })
@DisplayName("Product Service Tests")
@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    private static List<Product> products;
    private static Integer expectedProductsSize = 5;
    private static String newProductName = "Space Test product";
    private static String updatedProductName = "Updated Space product";
    private static UUID newProductId;
    private static Long newProductOwnerId = 1L;
    private static Long otherProductOwnerId = 2L;
    private static Category testCategory = Category.builder()
            .id(3)
            .name("Cosmic Test")
            .build();

    static Stream<Product> provideProducts() {
        return products.stream();
    }

    @Test
    @Order(1)
    void shouldReturnAllProducts() {
        products = productService.getAllProducts();
        assertNotNull(products);
        assertEquals(expectedProductsSize, products.size());
    }

    @ParameterizedTest
    @MethodSource("provideProducts")
    @Order(2)
    void shouldReturnProductByID(Product expectedProduct) {
        Product actualProduct = productService.getProductById(expectedProduct.getId());
        assertEquals(expectedProduct, actualProduct, "Product should match");
    }

    @Test
    @Order(3)
    void shouldThrowExceptionForNonExistentProductID() {
        UUID nonExistentId = UUID.randomUUID();
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(nonExistentId),
                "Expected ProductNotFoundException for a non-existent product ID");
    }

    @Test
    @Order(4)
    void shouldCreateProduct() {
        Product newProduct = Product.builder()
                .name(newProductName)
                .description("Test description for a space product")
                .price(99.99)
                .stockCount(10)
                .category(testCategory)
                .owner(customerService.getCustomerDetailsById(newProductOwnerId))
                .build();

        newProductId = productService.createProduct(newProduct, newProductOwnerId);
        Product createdProduct = productService.getProductById(newProductId);

        assertNotNull(createdProduct, "Created product should not be null");
        assertEquals(newProduct.getName(), createdProduct.getName(), "Product name should match");
        assertEquals("Test description for a space product", createdProduct.getDescription(), "Product description should match");
        assertEquals(99.99, createdProduct.getPrice(), "Product price should match");
        assertEquals(10, createdProduct.getStockCount(), "Product stock count should match");
        assertEquals(newProductOwnerId, createdProduct.getOwner().getId(), "Product owner ID should match");

        expectedProductsSize++;
        assertEquals(expectedProductsSize, productService.getAllProducts().size(), "Product list size should be updated after creation");
    }

    @Test
    @Order(5)
    void shouldThrowCustomerNotFound() {
        Product newProduct = Product.builder()
                .name(newProductName)
                .description("Test description for a space product")
                .price(99.99)
                .stockCount(10)
                .category(testCategory)
                .build();

        assertThrows(CustomerNotFoundException.class,
                () -> productService.createProduct(newProduct, -1L),
                "Creating a product with an invalid customer ID should throw CustomerNotFoundException");
    }

    @Test
    @Order(6)
    void shouldUpdateProduct() {
        Product newProduct = Product.builder()
                .name(newProductName)
                .description("Initial description")
                .price(99.99)
                .stockCount(20)
                .category(testCategory)  
                .owner(customerService.getCustomerDetailsById(newProductOwnerId))
                .build();
        
        newProductId = productService.createProduct(newProduct, newProductOwnerId);
        
        Product productToUpdate = productService.getProductById(newProductId);
        Product updatedProduct = productToUpdate.toBuilder()
                .name(updatedProductName)  
                .price(120.00)  
                .build();
        
        productService.updateProduct(updatedProduct, newProductOwnerId);
        Product updatedProductFromService = productService.getProductById(newProductId);
        
        assertEquals(updatedProductName, updatedProductFromService.getName(), "Product name should match with updated version");
        assertEquals(120.00, updatedProductFromService.getPrice(), "Price should match with updated version");
        assertEquals(newProductOwnerId, updatedProductFromService.getOwner().getId(),"Product owner id should remain the same");
    }

    @Test
    @Order(7)
    void shouldDeleteProduct() {
        Product productToDelete = Product.builder()
            .name("Product to Delete")
            .description("Description for deletion test")
            .price(59.99)
            .stockCount(15)
            .category(testCategory)
            .owner(customerService.getCustomerDetailsById(newProductOwnerId))
            .build();
        
        UUID productIdToDelete = productService.createProduct(productToDelete, newProductOwnerId);
        
        assertThrows(PermissionDeniedException.class,
                () -> productService.deleteProductById(productIdToDelete, otherProductOwnerId));

        assertThrows(ProductNotFoundException.class,
                () -> productService.deleteProductById(UUID.randomUUID(), otherProductOwnerId));

        productService.deleteProductById(productIdToDelete, newProductOwnerId);
        assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(productIdToDelete));
    }

    @Test
    @Order(8)
    void shouldThrowProductsNotFound() {
        products = productService.getAllProducts();
        List<Product> productsCopy = new ArrayList<>(products);
        for (Product p : productsCopy) {
            productService.deleteProductById(p.getId(), p.getOwner().getId());
        }
        assertThrows(ProductListEmptyException.class,
                () -> productService.getAllProducts());
    }
}