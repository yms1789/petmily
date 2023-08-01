package com.pjt.petmily.domain.curation;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// 패턴매칭
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    private final CurationRepository curationRepository;

    String datePattern = "\\d{4}\\.\\d{2}\\.\\d{2}\\.";
    Pattern pattern = Pattern.compile(datePattern);

    @Override
    public void crawlAndSaveNews() throws IOException {
        String keyword = "반려동물 강아지";
        String baseUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=";
        int maxPages = 10;

        for (int page = 1; page <= maxPages; page++) {
            String url = baseUrl + keyword + "&start=" + ((page - 1) * 10);
            Document document = Jsoup.connect(url).get();
            Elements newsElements = document.select(".news_area");

            if (newsElements.isEmpty()) {
                break; // 뉴스가 없으면 크롤링 종료
            }

            for (Element newsElement : newsElements) {
                String title = newsElement.select(".news_tit").text();
                // 같은 뉴스일경우 스킵
                if (curationRepository.existsBycTitle(title)) {
                    System.out.println("Skipping duplicate record: " + title);
                    continue; // Skip saving the current Curation object
                }
                String content = newsElement.select(".news_dsc").text();

                Elements image1 = document.select(".news_wrap.api_ani_send");
                Elements image2 = image1.select(".dsc_thumb");
                String image = image2.select("img").attr("abs:src");
//                System.out.println("image1: " + image1);
//                System.out.println("image2: " + image2);
//                System.out.println("image: " + image);

                String urlLink = newsElement.select(".news_tit").attr("abs:href");
                String dateInfo = newsElement.select(".news_info").text();
                Matcher dateInfo2 = pattern.matcher(dateInfo);
                String date = "";
                if (dateInfo2.find()) {
                    date = dateInfo2.group(0);
                }
                Curation curation = Curation.builder()
                        .cTitle(title)
                        .cContent(content)
                        .cImage(image)
                        .cUrl(urlLink)
                        .cDate(date) // 뉴스 날짜로 변경
                        .cPetSpecies("강아지")
                        .build();

                curationRepository.save(curation);
            }
        }
    }

    @Override
    public List<NewsCurationDto> getNewsData() {
        // 뉴스 데이터를 데이터베이스에서 가져옵니다.
        List<Curation> curations = curationRepository.findAll();

        // Curation 엔티티를 NewsCurationDto 객체로 변환합니다.
        return curations.stream()
                .map(curation -> NewsCurationDto.builder()
                        .cTitle(curation.getCTitle())
                        .cCategory("이슈")
                        .cContent(curation.getCContent())
                        .cImage(curation.getCImage())
                        .cDate(curation.getCDate())
                        .cUrl(curation.getCUrl())
                        .cPetSpecies(curation.getCPetSpecies())
                        .build())
                .collect(Collectors.toList());
    }
}
