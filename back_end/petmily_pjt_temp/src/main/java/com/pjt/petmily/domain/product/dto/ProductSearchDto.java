package com.pjt.petmily.domain.product.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSearchDto {
    private String productName;
    private String productPrice;
    private String productUrl;
    private String productCategory;
    private String productImg;

    public ProductSearchDto(String title, String lprice, String link, String category3, String image) {
        this.productName = title;
        this.productPrice = lprice;
        this.productUrl = link;
        this.productCategory = category3;
        this.productImg = image;
    }
}
