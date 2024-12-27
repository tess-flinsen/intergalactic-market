package com.example.spacecatsmarket.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.repository.projection.ProductDetailsProjection;

@Repository
public interface ProductRepository extends ListCrudRepository<ProductEntity, UUID> {
    @Query("SELECT p.name AS name, p.description AS description, p.price AS price " +
            "FROM ProductEntity p " +
            "WHERE p.price >= :minPrice AND p.price <= :maxPrice " +
            "ORDER BY p.name ASC")
    List<ProductDetailsProjection> findProductByPriceRange(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice);
            
    @Query("SELECT p FROM ProductEntity p WHERE p.name=:name AND p.category.name=:categoryName")
    Optional<ProductEntity> findProductByNameAndCategoryName(@Param("name") String name, @Param("categoryName") String categoryName);
}