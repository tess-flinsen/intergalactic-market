package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.AbstractIt;
import com.example.spacecatsmarket.domain.Category;
import com.example.spacecatsmarket.domain.Customer;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDetailsDto;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.CustomerRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import com.example.spacecatsmarket.repository.entity.CustomerEntity;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.service.mapper.CategoryMapper;
import com.example.spacecatsmarket.service.mapper.CustomerMapper;
import com.example.spacecatsmarket.service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@DisplayName("Product Controller IT")
public class ProductControllerIT extends AbstractIt {
    @SpyBean private ProductService productService;

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private CustomerRepository customerRepository;

    @Autowired private MockMvc mockMvc;
    
    @Autowired private ProductMapper productMapper;
    @Autowired private CategoryMapper categoryMapper;
    @Autowired private CustomerMapper customerMapper;

    @BeforeEach
    void setUp() {
      reset(productService);
      productRepository.deleteAll();
    }

    private CategoryEntity categoryEntity;
    private CustomerEntity customerEntity;
    private ProductEntity productEntity;
    private Category category;
    private Customer customer;
    private Product product;

    @BeforeAll
    void init() {
        categoryEntity = CategoryEntity.builder().name("Meteor Test").build();
        customerEntity =
            CustomerEntity.builder()
                .name("Sirius Black")
                .address("Sector 5, Planet Zeta, Quadrant 12")
                .phoneNumber("999-353-1781")
                .email("sirius.blakc@gmail.com")
                .build();
        productEntity =
            ProductEntity.builder()
                .id(UUID.randomUUID())
                .name("Asteroid decor")
                .description("Interesting decor with a twist.")
                .price(155.12)
                .stockCount(5)
                .category(categoryEntity)
                .owner(customerEntity)
                .build();
    }

    @Test
    void shouldCreateValidProduct() throws Exception {
        seedDbWithoutProduct();
        mapDomainFromEntity();
        ProductDetailsDto productDto = productMapper.toProductDetailsDto(product);
        mockMvc
            .perform(
                post("/v1/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(productDto))
                    .header("customerId", customer.getId().toString()))
            .andDo(print())
            .andExpect(status().isCreated()) 
            .andExpect(header().exists("Location")) 
            .andExpect(jsonPath("$.name").value(productDto.getName())) 
            .andExpect(jsonPath("$.description").value(productDto.getDescription()))
            .andExpect(jsonPath("$.price").value(productDto.getPrice()))
            .andExpect(jsonPath("$.category.name").value(productDto.getCategory().getName())); 
  }

    @Test
    void shouldReturnProduct() throws Exception {
        seedDb();
        mapDomainFromEntity();
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
    void shouldThrowProductNotFound() throws Exception {
        seedDbWithoutProduct();
        mapDomainFromEntity();
        String path = "/v1/products/" + product.getId().toString();
        mockMvc.perform(get(path))
            .andDo(print())
            .andExpect(status().isNotFound()) 
            .andExpect(jsonPath("$.title").value("Product Not Found")) 
            .andExpect(jsonPath("$.detail").value("Product with id " + product.getId().toString() + " not found")) 
            .andExpect(jsonPath("$.instance").value(path))
            .andExpect(jsonPath("$.timestamp").exists());
  }

    @Test
    void shouldThrowProductListEmpty() throws Exception {
        seedDbWithoutProduct();
        mapDomainFromEntity();
        String path = "/v1/products";
        mockMvc.perform(get(path))
            .andDo(print())
            .andExpect(status().isNotFound()) 
            .andExpect(jsonPath("$.title").value("No Products Available")) 
            .andExpect(jsonPath("$.detail").value("The products list is empty.")) 
            .andExpect(jsonPath("$.instance").value(path))
            .andExpect(jsonPath("$.timestamp").exists()); 
    }

    @Test
    void shouldThrowPermissionDenied() throws Exception {
      seedDb();
      mapDomainFromEntity();
      String path = "/v1/products/" + product.getId().toString();
      mockMvc.perform(delete(path).header("customerId", -1L))
          .andDo(print())
          .andExpect(status().isForbidden()) 
          .andExpect(jsonPath("$.title").value("Permission Denied")) 
          .andExpect(jsonPath("$.instance").value(path));
  }

    private void seedDb() {
      categoryEntity = categoryRepository.save(categoryEntity);
      customerEntity = customerRepository.save(customerEntity);

      productEntity.setOwner(customerEntity);
      productEntity.setCategory(categoryEntity);
      productRepository.save(productEntity);
    }

    private void seedDbWithoutProduct() {
        categoryEntity = categoryRepository.save(categoryEntity);
        customerEntity = customerRepository.save(customerEntity);
    }

    private void mapDomainFromEntity() {
        category = categoryMapper.fromCategoryEntity(categoryEntity);
        customer = customerMapper.fromCustomerEntity(customerEntity);
        product = productMapper.fromProductEntity(productEntity);
    }
}