package com.pjt.petmily.domain.curation;

import com.pjt.petmily.domain.curation.dto.NewsCurationDto;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface CurationService {
    void crawlAndSaveNews() throws IOException;
    List<NewsCurationDto> getNewsData();

}
