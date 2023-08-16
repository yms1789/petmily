package com.pjt.petmily.domain.product.service;

import com.pjt.petmily.domain.product.dto.ProductDto;
import com.pjt.petmily.domain.product.dto.ProductSearchDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public interface ProductService {
    List<ProductSearchDto> productSearch(String keyword);
    void crawlAndSaveProduct(String species, String category) throws IOException;
    Map<String, List<ProductDto>> getProductData(String species);
    void deleteAllProducts();
}
