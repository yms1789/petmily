package com.pjt.petmily.domain.product;


import com.pjt.petmily.domain.curation.service.CurationService;
import com.pjt.petmily.domain.product.dto.ProductSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @GetMapping("product/search/{keyword}")
    @Operation(summary = "상품 검색", description = "상품 검색")
    public List<ProductSearchDto> productSearch(@PathVariable String keyword) {
        List searchData = productService.productSearch(keyword);
        return searchData;
    }
}
