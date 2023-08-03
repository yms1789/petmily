package com.pjt.petmily.domain.product;

import com.pjt.petmily.domain.product.dto.ProductSearchDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public interface ProductService {
    List<ProductSearchDto> productSearch(String keyword);


    void crawlAndSaveProduct(String species, String category) throws IOException;
}
