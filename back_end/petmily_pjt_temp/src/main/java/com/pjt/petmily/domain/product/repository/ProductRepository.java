package com.pjt.petmily.domain.product.repository;

import com.pjt.petmily.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductSpecies(String speices);

    boolean existsByProductName(String name);
}
