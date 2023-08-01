package com.pjt.petmily.domain.curation.dto;

import lombok.Builder;
import lombok.Data;
import org.jsoup.nodes.Document;

import java.time.LocalDateTime;


@Builder
@Data
public class NewsCurationDto {
    private String cTitle;
    private String cPetSpecies;
    private String cCategory;
    private String cContent;
    private String cImage;
    private String cUrl;
    private String cDate;

}
