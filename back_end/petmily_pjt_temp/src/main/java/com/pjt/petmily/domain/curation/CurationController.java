package com.pjt.petmily.domain.curation;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import com.pjt.petmily.domain.curation.dto.PetSpeciesDto;
import com.pjt.petmily.domain.user.dto.LoginResponseDto;
import com.pjt.petmily.domain.user.dto.ResponseDto;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
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

//    @GetMapping("/curation/getNewsData")
//    public ResponseEntity<List<NewsCurationDto>> getNewsData(@RequestParam String spices) {
//        try {
//            List<NewsCurationDto> newsData = curationService.getNewsData(spices);
//            return ResponseEntity.status(HttpStatus.OK).body(newsData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//
//    }
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




//    @PostMapping("/login")
//    public ResponseDto<LoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
//        ResponseDto<LoginResponseDto> result = userService.loginUser(userLoginDto);
//        return result


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


}


