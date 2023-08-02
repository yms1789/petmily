package com.pjt.petmily.domain.curation;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



import java.awt.print.Pageable;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

// 패턴매칭
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    private final CurationRepository curationRepository;

    String datePattern = "\\d{4}\\.\\d{2}\\.\\d{2}\\.";
    String datePattern2 = "\\d+일 전";
//    String datePattern3 = "\\d+시간 전";
    Pattern pattern = Pattern.compile(datePattern);
    Pattern pattern2 = Pattern.compile(datePattern2);
//    Pattern pattern3 = Pattern.compile(datePattern3);

    @Override
    public void crawlAndSaveNews(String species) throws IOException {
        String keyword = "반려동물" + species;
        String baseUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=";
        int maxPages = 10;

        // 카테고리 test용
        List<String> categories = List.of("건강", "미용", "식품", "여행");


        for (int page = 1; page <= maxPages; page++) {
            String url = baseUrl + keyword + "&start=" + ((page - 1) * 10);

            try {

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
                    continue;
                }
                String content = newsElement.select(".news_dsc").text();

                Elements image1 = document.select(".news_wrap.api_ani_send");
                Elements image2 = image1.select(".dsc_thumb");
                String image = image2.select("img").attr("data-lazysrc");


                String urlLink = newsElement.select(".news_tit").attr("abs:href");
                String dateInfo = newsElement.select(".news_info").text();
                Matcher dateInfo2 = pattern.matcher(dateInfo);
                Matcher dateInfo3 = pattern2.matcher(dateInfo);
//                Matcher dateInfo4 = pattern3.matcher(dateInfo);
                String date = "";
                DateTimeFormatter dateToString = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

                //test용
                int randomIndex = new Random().nextInt(categories.size());
                if (dateInfo2.find()) {
                    date = dateInfo2.group(0);
                } else if (dateInfo3.find()) {
                    String stringDate = dateInfo3.group(0);
                    Integer minusDate = Integer.parseInt(stringDate.replaceAll("[^0-9]", ""));
                    date = dateToString.format(LocalDate.now().minusDays(minusDate));
                } else {
                    date = dateToString.format(LocalDate.now());
                }
                Curation curation = Curation.builder()
                        .cTitle(title)
                        .cContent(content)
                        .cImage(image)
                        .cUrl(urlLink)
                        .cDate(date) // 뉴스 날짜로 변경
                        .cCategory(categories.get(randomIndex))
                        .cPetSpecies(species)
                        .cBookmarkCnt(0)
                        .build();

                curationRepository.save(curation);
            }
            } catch (HttpStatusException e) {
                // 오류가 발생한 경우, 로그 출력 등의 예외 처리를 수행
                System.err.println("Error fetching URL: " + e.getUrl());
                // 오류 발생 페이지를 스킵하고 다음 페이지로 진행하도록 continue 사용
                continue;
            } catch (IOException e) {
                // 그 외의 IO 오류 처리
                e.printStackTrace();
            }

        }

    }

//    @Override
//    public List<NewsCurationDto> getNewsData(String spices) {
//        // 뉴스 데이터를 데이터베이스에서 가져옵니다.
////        List<Curation> curations = curationRepository.findAll();
//
//        List<Curation> curations;
//        if ("All".equalsIgnoreCase(spices)) {
//            // 'All'인 경우 모든 데이터를 가져옵니다.
//            curations = curationRepository.findAll();
//        } else if ("Dog".equalsIgnoreCase(spices)) {
//            curations = curationRepository.findByCPetSpecies(spices);
//        } else {
//            // 그 외의 경우 빈 데이터를 반환하거나 에러 처리를 수행
//            // 예를 들어, 잘못된 spices 값이 들어온 경우에 대한 처리를 추가할 수 있습니다.
//            curations = curationRepository.findByCPetSpecies("기타동물");
//        }
//
//
//        // Curation 엔티티를 NewsCurationDto 객체로 변환합니다.
//        return curations.stream()
//                .map(curation -> NewsCurationDto.builder()
//                        .cTitle(curation.getCTitle())
//                        .cCategory(curation.getCCategory())
//                        .cContent(curation.getCContent())
//                        .cImage(curation.getCImage())
//                        .cDate(curation.getCDate())
//                        .cUrl(curation.getCUrl())
//                        .cPetSpecies(curation.getCPetSpecies())
//
//                        .build())
//                .collect(Collectors.toList());
//    }
    @Override
    public Map<String, List<NewsCurationDto>> getNewsData(String species) {
        // 뉴스 데이터를 데이터베이스에서 가져옵니다.
        List<Curation> curations;
        if ("All".equalsIgnoreCase(species)) {
            // 'All'인 경우 모든 데이터를 가져옴
            curations = curationRepository.findAll();
        } else {
            // species 값에 따라 강아지, 고양이, 기타동물에 해당하는 데이터를 가져옴
        curations = curationRepository.findBycPetSpecies(species);
        }
//        System.out.println("가져온값" + curations);
        // Curation 엔티티를 NewsCurationDto 객체로 변환한 후, cPetSpecies별로 Map에 그룹화합니다.
        Map<String, List<NewsCurationDto>> resultMap = curations.stream()
                .map(curation -> NewsCurationDto.builder()
                        .cTitle(curation.getCTitle())
                        .cCategory(curation.getCCategory())
                        .cContent(curation.getCContent())
                        .cImage(curation.getCImage())
                        .cDate(curation.getCDate())
                        .cUrl(curation.getCUrl())
                        .cPetSpecies(curation.getCPetSpecies())
                        .build()
                )
                .collect(Collectors.groupingBy(NewsCurationDto::getCPetSpecies));

        return resultMap;
    }


}
