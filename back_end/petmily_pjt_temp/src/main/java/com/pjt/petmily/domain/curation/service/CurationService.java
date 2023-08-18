package com.pjt.petmily.domain.curation.service;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface CurationService {

    void crawlAndSaveNews(String species, String category) throws IOException;

    Map<String, List<NewsCurationDto>> getNewsData(String species);

    void curationBookmark(String userEmail, Long cId);

    Long emailToId (String userEmail);

}
