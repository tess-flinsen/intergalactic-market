package com.example.spacecatsmarket.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "category")
public class CategoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_seq")
  @SequenceGenerator(
      name = "category_id_seq",
      sequenceName = "category_id_seq",
      allocationSize = 50)
  Long id;

  String name;

  @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
  List<ProductEntity> products;
}