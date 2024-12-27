package com.example.spacecatsmarket.web;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.dto.product.ProductDetailsDto;
import com.example.spacecatsmarket.dto.product.ProductDetailsListDto;
import com.example.spacecatsmarket.service.ProductService;
import com.example.spacecatsmarket.service.mapper.ProductMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@Validated
@RequestMapping("v1/products")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    
    @Operation(summary = "Get all products", description = "Retrieve a list of all available products.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of all products retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsListDto.class))),
        @ApiResponse(responseCode = "404", description = "No available products in the marketplace",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<ProductDetailsListDto> getAllProducts() {
      return ResponseEntity.ok(productMapper.toProductDetailsListDto(productService.getAllProducts()));
    }


    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product found", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsDto.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailsDto> getProductById(@PathVariable("productId") UUID id) {
        Product product = productService.getProductById(id);
        ProductDetailsDto productDetailsDto = productMapper.toProductDetailsDto(product);
        return ResponseEntity.ok(productDetailsDto);
    }
    
    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product with specified details.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Product created successfully",
                    headers = @Header(name = "Location", description = "URL to newly created product",
                                    schema = @Schema(type = "string", example = "/v1/products/550e8400-e29b-41d4-a716-446655440000")),
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", 
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "409", description = "Product already exists", 
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ResponseEntity<ProductDetailsDto> createProduct(
        @RequestBody @Valid ProductDetailsDto productDetailsDto, @RequestHeader("customerId") Long customerId) {
        UUID productId = productService.createProduct(productMapper.toProduct(productDetailsDto), customerId);
        ProductDetailsDto createdProductDto = productMapper.toProductDetailsDto(
            productService.getProductById(productId)
        );
        URI location = URI.create(String.format("/v1/products/%s", productId));
        return ResponseEntity.created(location).body(createdProductDto);
    }


    @PutMapping("/{productId}")
    @Operation(summary = "Update an existing product", description = "Update the details of a product by its ID.")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200", description = "Product updated successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDetailsDto.class))
        ),
        @ApiResponse(
            responseCode = "400", description = "Invalid input data",
            content = @Content( mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        ),
        @ApiResponse(
            responseCode = "404", description = "Product not found", 
            content = @Content( mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))
        )
    })
    public ResponseEntity<?> updateProduct(
        @PathVariable("productId") UUID id,
        @RequestBody ProductDetailsDto productDetailsDto,
        @RequestHeader Long customerId) {
        Product product = productMapper.toProduct(productDetailsDto);
        product.toBuilder().id(id).build();
        productService.updateProduct(product, customerId);
        ProductDetailsDto updatedProductDto = productMapper.toProductDetailsDto(product);
        return ResponseEntity.ok(updatedProductDto);
    }

    @Operation(
        summary = "Delete an existing product",
        description = "Delete a product by its unique ID. Only accessible to the product owner."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
        @ApiResponse(responseCode = "403", description = "Deletion forbidden due to insufficient permissions", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class))),
        @ApiResponse(responseCode = "404", description = "Product not found", 
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(
        @PathVariable("productId") UUID id, @RequestHeader("customerId") Long customerId) {
        productService.deleteProductById(id, customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
