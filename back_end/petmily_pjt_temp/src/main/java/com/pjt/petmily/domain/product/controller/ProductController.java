package com.pjt.petmily.domain.product.controller;

import com.pjt.petmily.domain.product.service.ProductService;
import com.pjt.petmily.domain.product.dto.ProductDto;
import com.pjt.petmily.domain.product.dto.ProductSearchDto;
import io.swagger.v3.oas.annotations.Operation;
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

    @PostMapping("product/saveData")
    @Operation(summary = "[TEST용] 상품데이터정보 수동 받아오기", description = "추천상품정보 수동으로 가져오기")
    public String getProductData() throws IOException {
        productService.crawlAndSaveProduct("강아지","사료");
        productService.crawlAndSaveProduct("강아지","건강");
        productService.crawlAndSaveProduct("강아지","미용");
        productService.crawlAndSaveProduct("강아지","놀이");
        productService.crawlAndSaveProduct("고양이","사료");
        productService.crawlAndSaveProduct("고양이","건강");
        productService.crawlAndSaveProduct("고양이","미용");
        productService.crawlAndSaveProduct("고양이","놀이");
        productService.crawlAndSaveProduct("햄스터","식품");
        productService.crawlAndSaveProduct("햄스터","건강");
        productService.crawlAndSaveProduct("햄스터","미용");
        productService.crawlAndSaveProduct("햄스터","놀이");
        productService.crawlAndSaveProduct("고슴도치","식품");
        productService.crawlAndSaveProduct("고슴도치","건강");
        productService.crawlAndSaveProduct("고슴도치","미용");
        productService.crawlAndSaveProduct("고슴도치","놀이");
        productService.crawlAndSaveProduct("조류","식품");
        productService.crawlAndSaveProduct("조류","건강");
        productService.crawlAndSaveProduct("조류","미용");
        productService.crawlAndSaveProduct("조류","놀이");
        return "newProductDataGet";
    }


    @GetMapping("product/getdata")
    @Operation(summary = "동물종에 따른 상품 정보 불러오기", description = "species:강아지,고양이,기타동물/ 동물 종류별로 묶어서 반환")
    public ResponseEntity<Map<String, List<ProductDto>>> ProductData(@RequestParam String species) {
        try {
            Map<String, List<ProductDto>> ProductDataMap = productService.getProductData(species);
            return ResponseEntity.ok(ProductDataMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("product/reset")
    @Operation(summary = "상품정보 초기화")
    public void ProductReset() {
        productService.deleteAllProducts();
    }
}
