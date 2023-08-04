package com.pjt.petmily.domain.product;


import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import com.pjt.petmily.domain.curation.service.CurationService;
import com.pjt.petmily.domain.product.dto.ProductDto;
import com.pjt.petmily.domain.product.dto.ProductSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    // 상품 정보 DB에 담기
    @PostMapping("product/saveData")
    public String getProductData() throws IOException {
        productService.crawlAndSaveProduct("강아지","식품");
        productService.crawlAndSaveProduct("강아지","건강");
        productService.crawlAndSaveProduct("강아지","미용");
        productService.crawlAndSaveProduct("강아지","놀이");
        productService.crawlAndSaveProduct("고양이","식품");
        productService.crawlAndSaveProduct("고양이","건강");
        productService.crawlAndSaveProduct("고양이","미용");
        productService.crawlAndSaveProduct("고양이","놀이");
        return "newProductDataGet";
    }



    // 상품 정보 불러오기
    @GetMapping("product/getdata")
    public ResponseEntity<Map<String, List<ProductDto>>> ProductData(@RequestParam String species) {
        try {
            Map<String, List<ProductDto>> ProductDataMap = productService.getProductData(species);
            return ResponseEntity.ok(ProductDataMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
