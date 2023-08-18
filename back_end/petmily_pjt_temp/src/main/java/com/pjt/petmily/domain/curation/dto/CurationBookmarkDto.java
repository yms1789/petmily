package com.pjt.petmily.domain.curation.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CurationBookmarkDto {
    private String userEmail;
    private Long cId;
}
