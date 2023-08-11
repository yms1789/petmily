package com.pjt.petmily.domain.curation.controller;

import com.pjt.petmily.domain.curation.dto.CurationBookmarkDto;
import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import com.pjt.petmily.domain.curation.repository.UserCurationRepository;
import com.pjt.petmily.domain.curation.service.CurationService;
import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import io.swagger.v3.oas.annotations.Operation;
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
import java.util.stream.Collectors;


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
    @Operation(summary = "강아지, 고양이 데이터 크롤링")
    public String DataCrawl() throws IOException, InterruptedException {
        setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        curationService.crawlAndSaveNews("강아지", "건강");
        curationService.crawlAndSaveNews("강아지", "미용");
        curationService.crawlAndSaveNews("강아지", "식품");
        curationService.crawlAndSaveNews("강아지", "입양");
        curationService.crawlAndSaveNews("고양이", "건강");
        curationService.crawlAndSaveNews("고양이", "미용");
        curationService.crawlAndSaveNews("고양이", "식품");
        curationService.crawlAndSaveNews("고양이", "입양");
        return "뉴스 큐레이션 크롤링 완료";
    }

    @PutMapping("/curation/DataCrawling2")
    @Operation(summary = "기타 동물 데이터 크롤링")
    public String DataCrawl2() throws IOException, InterruptedException {
        setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        curationService.crawlAndSaveNews("햄스터", "건강");
        curationService.crawlAndSaveNews("햄스터", "미용");
        curationService.crawlAndSaveNews("햄스터", "식품");
        curationService.crawlAndSaveNews("햄스터", "입양");
        curationService.crawlAndSaveNews("고슴도치", "건강");
        curationService.crawlAndSaveNews("고슴도치", "미용");
        curationService.crawlAndSaveNews("고슴도치", "식품");
        curationService.crawlAndSaveNews("고슴도치", "입양");
        curationService.crawlAndSaveNews("조류", "건강");
        curationService.crawlAndSaveNews("조류", "미용");
        curationService.crawlAndSaveNews("조류", "식품");
        curationService.crawlAndSaveNews("조류", "입양");
        return "뉴스 큐레이션 크롤링 완료";
    }


    private final UserCurationRepository userCurationRepository;
    // 북마크 선택, 취소
    @PostMapping("/curation/bookmarks")
    @Operation(summary = "북마크 설정 및 취소", description = "입력값:ex){\n" +
            "    \"userEmail\": \"example@example.com\",\n" +
            "    \"cId\": 1\n" +
            "}, return값:해당유저의 현재 북마크 된 큐레이션id 리스트로 반환")
    public List<Long> curationBookmark(@RequestBody CurationBookmarkDto curationBookmarkDto) {
        //test
        Long userid = curationService.emailToId(curationBookmarkDto.getUserEmail());
        // 북마크 선택, 취소
        curationService.curationBookmark(curationBookmarkDto.getUserEmail(), curationBookmarkDto.getCId());
        // userbookmark 보여주기
        List<Curationbookmark> curationbookmarks = userCurationRepository.findByUser_UserId(userid);
        List<Long> cidList = curationbookmarks.stream()
                .map(c -> c.getCuration().getCId())
                .collect(Collectors.toList());
        return cidList;
    }
}


