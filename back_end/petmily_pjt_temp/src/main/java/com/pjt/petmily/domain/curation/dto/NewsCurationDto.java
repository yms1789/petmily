package com.pjt.petmily.domain.curation.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class NewsCurationDto {
    private Long cId;
    private String cTitle;
    private String cPetSpecies;
    private String cCategory;
    private String cContent;
    private String cImage;
    private String cUrl;
    private String cDate;
    private Integer cBookmarkCnt;

}
