package com.pjt.petmily.domain.curation;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;



@RestController
@RequiredArgsConstructor
public class CurationController {
    private final CurationService curationService;

    @GetMapping("/curation/getNewsData")
    public ResponseEntity<List<NewsCurationDto>> getNewsData() {
        try {
//            curationService.crawlAndSaveNews();
            List<NewsCurationDto> newsData = curationService.getNewsData();
            return ResponseEntity.status(HttpStatus.OK).body(newsData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    // 임시 데이터 크롤링
    @PutMapping("/curation/dataCrawling")
    public String dataCrawl() throws IOException {
        curationService.crawlAndSaveNews();
        return "크롤링 완료";
    }
}


