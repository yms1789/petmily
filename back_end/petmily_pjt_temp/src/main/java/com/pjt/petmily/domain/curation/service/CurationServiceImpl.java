package com.pjt.petmily.domain.curation.service;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import com.pjt.petmily.domain.curation.entity.Curation;
import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import com.pjt.petmily.domain.curation.repository.CurationRepository;
import com.pjt.petmily.domain.curation.repository.UserCurationRepository;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// 패턴매칭
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CurationServiceImpl implements CurationService {

    private final UserRepository userRepository;

    private final CurationRepository curationRepository;

    @Autowired
    private UserCurationRepository userCurationRepository;


    String datePattern = "\\d{4}\\.\\d{2}\\.\\d{2}\\.";
    String datePattern2 = "\\d+일 전";
    Pattern pattern = Pattern.compile(datePattern);
    Pattern pattern2 = Pattern.compile(datePattern2);

    @Async
    @Override
    public void crawlAndSaveNews(String species, String category) throws IOException {
        String keyword = "반려동물" + species + category;
        String baseUrl = "https://search.naver.com/search.naver?where=news&sm=tab_jum&query=";
        int maxPages = 5;



        for (int page = 1; page <= maxPages; page++) {
            String url = baseUrl + keyword + "&start=" + ((page - 1) * 10);

            try {

                Document document = Jsoup.connect(url).get();
                Elements newsElements  = document.select(".news_wrap.api_ani_send");
                if (newsElements.isEmpty()) {
                    break;
                }

                for (Element newsElement : newsElements) {
                    String title = newsElement.select(".news_tit").text();
                    String urlLink = newsElement.select(".news_tit").attr("abs:href");
                    if (curationRepository.existsBycTitle(title) || curationRepository.existsBycUrl(urlLink)) {
                        System.out.println("Skipping duplicate record: " + title);
                        continue;
                    }
                    String content = newsElement.select(".news_dsc").text();

                    String image = newsElement.select(".dsc_thumb img").attr("data-lazysrc");

                    String dateInfo = newsElement.select(".news_info").text();
                    Matcher dateInfo2 = pattern.matcher(dateInfo);
                    Matcher dateInfo3 = pattern2.matcher(dateInfo);
                    String date = "";
                    DateTimeFormatter dateToString = DateTimeFormatter.ofPattern("yyyy.MM.dd.");


                    if (dateInfo2.find()) {
                        date = dateInfo2.group(0);
                    } else if (dateInfo3.find()) {
                        String stringDate = dateInfo3.group(0);
                        Integer minusDate = Integer.parseInt(stringDate.replaceAll("[^0-9]", ""));
                        date = dateToString.format(LocalDate.now().minusDays(minusDate));
                    } else {
                        date = dateToString.format(LocalDate.now());
                    }
                    if (!"강아지".equals(species) && !"고양이".equals(species)) {
                        species = "기타동물";
                    }

                    Curation curation = Curation.builder()
                            .cTitle(title)
                            .cContent(content)
                            .cImage(image)
                            .cUrl(urlLink)
                            .cDate(date)
                            .cCategory(category)
                            .cPetSpecies(species)
                            .cBookmarkCnt(0)
                            .build();

                    curationRepository.save(curation);
                }
            } catch (HttpStatusException e) {
                System.err.println("Error fetching URL: " + e.getUrl());
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public Map<String, List<NewsCurationDto>> getNewsData(String species) {
        List<Curation> curations;
        if ("All".equalsIgnoreCase(species)) {
            curations = curationRepository.findAll();
        } else {
        curations = curationRepository.findBycPetSpecies(species);
        }
        curations.sort(Comparator.comparing(Curation::getCDate).reversed());
        Map<String, List<NewsCurationDto>> resultMap = curations.stream()
                .map(curation -> NewsCurationDto.builder()
                        .cId(curation.getCId())
                        .cTitle(curation.getCTitle())
                        .cCategory(curation.getCCategory())
                        .cContent(curation.getCContent())
                        .cImage(curation.getCImage())
                        .cDate(curation.getCDate())
                        .cUrl(curation.getCUrl())
                        .cPetSpecies(curation.getCPetSpecies())
                        .cBookmarkCnt(curation.getCBookmarkCnt())
                        .build()
                )
                .collect(Collectors.groupingBy(NewsCurationDto::getCPetSpecies));


        return resultMap;
    }


    // 북마크 카운트
    public void bookmarkCnt(Long cId, boolean tf) {
        Curation curation = curationRepository.findById(cId)
                .orElseThrow(() -> new RuntimeException("큐레이션을 찾을 수 없습니다."));
        if (tf) {
            int bookmarkCnt = curation.getCBookmarkCnt() + 1;
            curation.setCBookmarkCnt(bookmarkCnt);
        } else {
            int bookmarkCnt = curation.getCBookmarkCnt() - 1;
            bookmarkCnt = Math.max(bookmarkCnt, 0);
            curation.setCBookmarkCnt(bookmarkCnt);
        }
        curationRepository.save(curation);
    }

    // 북마크 추가 제거
    public void curationBookmark(String userEmail, Long cId) {
        Long userId = emailToId(userEmail);
        User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Curation curation = curationRepository.findById(cId)
                    .orElseThrow(() -> new RuntimeException("큐레이션을 찾을 수 없습니다."));

        Curationbookmark existingBookmark = userCurationRepository.findByUserAndCuration(user,curation);
        if (existingBookmark != null){
            userCurationRepository.delete(existingBookmark);
            bookmarkCnt(curation.getCId(), false);
        } else {
            Curationbookmark curationbookmark = Curationbookmark.builder()
                    .user(user)
                    .curation(curation)
                    .build();
            userCurationRepository.save(curationbookmark);
            bookmarkCnt(curation.getCId(), true);
        }

    }

    // useremail -> userid
    @Override
    public Long emailToId (String userEmail) {
        User userdata = userRepository.findByUserEmail(userEmail).get();
        Long userid = userdata.getUserId();
        return userid;
    }



}
