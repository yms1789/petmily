package com.pjt.petmily.domain.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Getter
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotNull
    private String productName;

    @Column(nullable = true)
    private String productPrice;

    @Column(nullable=true)
    private String productUrl;

    @Column(nullable=true)
    private String productCategory;

    @Column(nullable=true)
    private String productImg;

    @Column(nullable=true)
    private String productSpecies;



}
