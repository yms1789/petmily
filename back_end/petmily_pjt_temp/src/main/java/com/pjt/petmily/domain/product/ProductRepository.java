package com.pjt.petmily.domain.product;

import com.pjt.petmily.domain.curation.entity.Curation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
