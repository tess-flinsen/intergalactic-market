package com.example.spacecatsmarket.web;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.example.spacecatsmarket.config.MappersTestConfiguration;
import com.example.spacecatsmarket.domain.Category;
import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.service.exception.ProductListEmptyException;
import com.example.spacecatsmarket.service.exception.ProductNotFoundException;
import com.example.spacecatsmarket.web.exception.GlobalExceptionHandler;

import org.junit.jupiter.api.Test;


@WebMvcTest(ProductController.class)
@Import({MappersTestConfiguration.class, GlobalExceptionHandler.class})
@DisplayName("Product Controller IT")
public class ProductControllerTest {
    @MockBean private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Autowired private MockMvc mockMvc;

    private static Customer customer = Customer.builder()
                                        .id(4L)
                                        .name("Sirius Black")
                                        .address("117 Hound Street, Andromeda Galaxy")
                                        .phoneNumber("999-353-1781")
                                        .email("sirius.blakc@gmail.com")
                                        .build();

    private static Category testCategory = Category.builder()
            .id(4)
            .name("Meteor Test")
            .build();

    private static Product product = Product.builder()
                                .id(UUID.randomUUID())
                                .name("Asteroid decor")
                                .description("Interesting decor with a twist.")
                                .price(155.12)
                                .stockCount(5)
                                .category(testCategory)
                                .owner(customer)
                                .build();

   
    @Test
    public void shouldReturnProduct() throws Exception {
        when(productService.getProductById(product.getId())).thenReturn(product);
        mockMvc
            .perform(get("/v1/products/{productId}", product.getId()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.price").value(product.getPrice()))
            .andExpect(jsonPath("$.category.name").value(product.getCategory().getName()));
    }

    @Test
    public void shouldThrowProductNotFound() throws Exception {
        UUID nonExistentProductId = UUID.randomUUID();
        String path = "/v1/products/" + nonExistentProductId;

        when(productService.getProductById(nonExistentProductId))
            .thenThrow(new ProductNotFoundException(nonExistentProductId));

        mockMvc
            .perform(get(path))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("Product Not Found"))
            .andExpect(jsonPath("$.detail").value("Product with id " + nonExistentProductId + " not found"))
            .andExpect(jsonPath("$.instance").value(path))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void shouldThrowProductListEmpty() throws Exception {
        when(productService.getAllProducts()).thenThrow(new ProductListEmptyException());

        String path = "/v1/products";

        mockMvc
            .perform(get(path))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.title").value("No Products Available"))
            .andExpect(jsonPath("$.detail").value("The products list is empty."))
            .andExpect(jsonPath("$.instance").value(path))
            .andExpect(jsonPath("$.timestamp").exists());
    }
}