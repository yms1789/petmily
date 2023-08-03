package com.pjt.petmily.domain.product.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private String productName;
    private String productPrice;
    private String productUrl;
    private String productCategory;
    private String productImg;
    private String productSpecies;
}
