package com.pjt.petmily.domain.product;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Entity
@Getter
@Table(name="curation")
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
    private Integer productPrice;

    @Column(nullable=true)
    private String productUrl;

    @Column(nullable=true)
    private String productCategory;

    @Column(nullable=true)
    private String productImg;



}
