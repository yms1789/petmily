package com.pjt.petmily.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.petmily.domain.product.dto.ProductDto;
import com.pjt.petmily.domain.product.dto.ProductSearchDto;
import com.pjt.petmily.domain.product.entity.Product;
import com.pjt.petmily.domain.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    String clientId = "FoDAdUrsT16Nt13CAgz5";
    String clientSecret = "4_naXjLmJ3";


    public List<ProductSearchDto> productSearch(String keyword) {
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/shop.json")
                .queryParam("query", "반려동물" + keyword)
                .queryParam("display", 100)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(req, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();

            ObjectMapper mapper = new ObjectMapper();
            List<ProductSearchDto> productList = new ArrayList<>();

            try {
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode itemsNode = rootNode.path("items");

                for (JsonNode itemNode : itemsNode) {
                    String title = itemNode.path("title").asText();
                    String lprice = itemNode.path("lprice").asText();
                    String link = itemNode.path("link").asText();
                    String category3 = itemNode.path("category3").asText();
                    String image = itemNode.path("image").asText();
                    ProductSearchDto product = new ProductSearchDto(title, lprice, link, category3, image);
                    productList.add(product);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON 파싱 오류: " + e.getMessage());
            }
            return productList;
        } else {
            throw new RuntimeException("API 호출이 실패하였습니다. 상태 코드: " + response.getStatusCodeValue());
        }
    }

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    ProductRepository productRepository;
    // 최저가 상품 DB저장
    @Async
    @Override
    public void crawlAndSaveProduct(String species, String category) throws IOException {
        String keyword = species + " " + category;
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/shop.json")
                .queryParam("query", keyword)
                .queryParam("display", 100)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(req, String.class);

        String categorySymobol = (category.equals("놀이")) ? "기타" : category;
        String speciesSymbol = (!species.equals("강아지") && !species.equals("고양이")) ? "기타동물" : species;

        if (response.getStatusCode() == HttpStatus.OK) {
            String responseBody = response.getBody();

            ObjectMapper mapper = new ObjectMapper();

            try {
                JsonNode rootNode = mapper.readTree(responseBody);
                JsonNode itemsNode = rootNode.path("items");

                for (JsonNode itemNode : itemsNode) {
                    String title = itemNode.path("title").asText();
                    if (productRepository.existsByProductName(title)) {
                        continue;
                    }
                    String lprice = itemNode.path("lprice").asText();
                    String link = itemNode.path("link").asText();
                    String image = itemNode.path("image").asText();

                   Product product = Product.builder()
                           .productName(title)
                           .productPrice(lprice)
                           .productUrl(link)
                           .productCategory(categorySymobol)
                           .productImg(image)
                           .productSpecies(speciesSymbol)
                           .build();
                   productRepository.save(product);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON 파싱 오류: " + e.getMessage());
            }

        } else {
            throw new RuntimeException("API 호출이 실패하였습니다. 상태 코드: " + response.getStatusCodeValue());
        }
    }

    // 상품정보 가져오기
    @Override
    public Map<String, List<ProductDto>> getProductData(String species) {
        List<Product> products;
        if (!"강아지".equals(species) && !"고양이".equals(species)) {
            species = "기타동물";
        }
        products = productRepository.findByProductSpecies(species);
        Map<String, List<ProductDto>> resultMap = products.stream()
                .map(product -> ProductDto.builder()
                        .productName(product.getProductName())
                        .productPrice(product.getProductPrice())
                        .productSpecies(product.getProductSpecies())
                        .productCategory(product.getProductCategory())
                        .productUrl(product.getProductUrl())
                        .productImg(product.getProductImg())
                        .build()
                )
                .collect(Collectors.groupingBy(ProductDto::getProductCategory));
        return resultMap;
    }


    @Transactional
    public void deleteAllData() {
        productRepository.deleteAll();
    }

}
