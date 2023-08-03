package com.pjt.petmily.domain.curation.controller;

import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import com.pjt.petmily.domain.curation.service.CurationService;
import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class CurationController {
    private final CurationService curationService;


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


    // 임시 데이터 크롤링
    @PutMapping("/curation/dogDataCrawling")
    public String dogDataCrawl() throws IOException {
        curationService.crawlAndSaveNews("강아지");
        return "강아지 뉴스 큐레이션 크롤링 완료";
    }

    @PutMapping("/curation/catDataCrawling")
    public String dataCrawl() throws IOException {
        curationService.crawlAndSaveNews("고양이");
        return "고양이 뉴스 큐레이션 크롤링 완료";
    }

    // 북마크 선택, 취소
    @PostMapping("/curation/bookmarks")
    public ResponseEntity<List> curationBookmark(@RequestParam String userEmail,
                                                 @RequestParam Long cId) {
        curationService.curationBookmark(userEmail,cId);
        List bookmarkdata = curationService.userBookmark(userEmail);
        return (ResponseEntity<List>) bookmarkdata;
    }




}


