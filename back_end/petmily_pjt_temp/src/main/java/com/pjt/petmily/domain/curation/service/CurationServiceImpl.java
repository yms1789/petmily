package com.pjt.petmily.domain.curation.service;

import com.pjt.petmily.domain.curation.dto.CurationBookmarkDto;
import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import com.pjt.petmily.domain.curation.entity.Curation;
import com.pjt.petmily.domain.curation.entity.Curationbookmark;
import com.pjt.petmily.domain.curation.repository.CurationRepository;
import com.pjt.petmily.domain.curation.repository.UserCurationRepository;
import com.pjt.petmily.domain.user.User;
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
//    String datePattern3 = "\\d+시간 전";
    Pattern pattern = Pattern.compile(datePattern);
    Pattern pattern2 = Pattern.compile(datePattern2);
//    Pattern pattern3 = Pattern.compile(datePattern3);

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
//                Elements newsElements = document.select(".news_area");
                Elements newsElements  = document.select(".news_wrap.api_ani_send");
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

                    String image = newsElement.select(".dsc_thumb img").attr("data-lazysrc");

                    String urlLink = newsElement.select(".news_tit").attr("abs:href");
                    String dateInfo = newsElement.select(".news_info").text();
                    Matcher dateInfo2 = pattern.matcher(dateInfo);
                    Matcher dateInfo3 = pattern2.matcher(dateInfo);
    //                Matcher dateInfo4 = pattern3.matcher(dateInfo);
                    String date = "";
                    DateTimeFormatter dateToString = DateTimeFormatter.ofPattern("yyyy.MM.dd.");

                    //test용
//                    int randomIndex = new Random().nextInt(categories.size());
                    if (dateInfo2.find()) {
                        date = dateInfo2.group(0);
                    } else if (dateInfo3.find()) {
                        String stringDate = dateInfo3.group(0);
                        Integer minusDate = Integer.parseInt(stringDate.replaceAll("[^0-9]", ""));
                        date = dateToString.format(LocalDate.now().minusDays(minusDate));
                    } else {
                        date = dateToString.format(LocalDate.now());
                    }
                    if (species != "강아지" || species !="고양이") {
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
            // true인 경우, cBookmarkCnt + 1
            int bookmarkCnt = curation.getCBookmarkCnt() + 1;
            curation.setCBookmarkCnt(bookmarkCnt);
        } else {
            // false인 경우, cBookmarkCnt - 1
            int bookmarkCnt = curation.getCBookmarkCnt() - 1;
            // 음수가 되지 않도록 최소값을 0으로 제한
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

//    @Override
//    // 해당유저 북마크 되어있는 데이터 전달
//    public List<Long> userBookmark(CurationBookmarkDto curationbookmarkDto) {
//        Long userId = emailToId(curationbookmarkDto.getUserEmail());
//        List<Curationbookmark> bookmarksdata = userCurationRepository.findByUser_UserId(userId);
//        System.out.println("북마크정보" + bookmarksdata);
//        return bookmarksdata.stream().map(Curationbookmark::getCuration).map(Curation::getCId).collect(Collectors.toList());
//    }


}
