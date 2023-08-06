package com.pjt.petmily.domain.product;

import com.pjt.petmily.domain.curation.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductSpecies(String speices);

    boolean existsByProductName(String name);
}
