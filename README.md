
# Cosmo Cats Intergalactic Marketplace 

## Project Overview
Cosmo Cats Intergalactic Marketplace is a unique e-commerce platform for extraterrestrial goods—where cosmic products like anti-gravity yarn balls and space milk are traded!
This marketplace backend, built with Java and Spring Boot, follows a Domain-Driven Design (DDD) approach to create a clear, well-organized, and scalable structure.
This learning project covers API contract creation, data validation, CRUD operations, error handling, and environment profile management.

## Covered Tasks
### Part 1: API Contract & Error Handling
- **API Contract**: Define CRUD endpoints for `Product`, document with Swagger/OpenAPI.
- **Error Handling**: Implement global error handler following RFC 9457 with structured responses.

### Part 2: Domain-Driven Design (DDD)
- **Domain Modeling**: Design domain entities (`Product`, `Category`, `Order`).
- **DTOs & Mapping**: Create DTOs and map with MapStruct.

### Part 3: CRUD Operations
- Implement CRUD operations for `Product` with mock data responses.

### Part 4: Validation
- **Data Validation**: Use annotations like `@NotNull`, `@Size`, and create custom validators (e.g., `@CosmicWordCheck`).

### Part 5: Testing
- Write and perform integration and unit tests for all implemented features.
