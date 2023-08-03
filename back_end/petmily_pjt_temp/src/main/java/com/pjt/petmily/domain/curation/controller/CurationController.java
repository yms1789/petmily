package com.pjt.petmily.domain.curation.controller;

import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import com.pjt.petmily.domain.curation.service.CurationService;
import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


@EnableAsync
@RestController
@RequiredArgsConstructor
public class CurationController {
    private final CurationService curationService;

    // 큐레이션 정보가져오기
    @GetMapping("/curation/getNewsData")
    public ResponseEntity<Map<String, List<NewsCurationDto>>> getNewsData(@RequestParam String species) {
        try {
            Map<String, List<NewsCurationDto>> newsDataMap = curationService.getNewsData(species);
            return ResponseEntity.ok(newsDataMap);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public void setUserAgent(String userAgent) {
        URLConnection connection = null;
        try {
            connection = new URL("https://search.naver.com").openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        connection.setRequestProperty("User-Agent", userAgent);
    }

    // 임시 데이터 크롤링
    @PutMapping("/curation/DataCrawling")
    public String DataCrawl() throws IOException, InterruptedException {
        setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        curationService.crawlAndSaveNews("강아지", "건강");
        curationService.crawlAndSaveNews("강아지", "미용");
        curationService.crawlAndSaveNews("강아지", "식품");
        curationService.crawlAndSaveNews("강아지", "여행");
        curationService.crawlAndSaveNews("고양이", "건강");
        curationService.crawlAndSaveNews("고양이", "미용");
        curationService.crawlAndSaveNews("고양이", "식품");
        curationService.crawlAndSaveNews("고양이", "여행");
        return "뉴스 큐레이션 크롤링 완료";
    }

//    @PutMapping("/curation/catDataCrawling")
//    public String dataCrawl() throws IOException {
//        curationService.crawlAndSaveNews("고양이");
//        return "고양이 뉴스 큐레이션 크롤링 완료";
//    }

    // 북마크 선택, 취소
    @PostMapping("/curation/bookmarks")
    public ResponseEntity<List> curationBookmark(@RequestParam String userEmail,
                                                 @RequestParam Long cId) {
        curationService.curationBookmark(userEmail,cId);
        List bookmarkdata = curationService.userBookmark(userEmail);
        return (ResponseEntity<List>) bookmarkdata;
    }




}


