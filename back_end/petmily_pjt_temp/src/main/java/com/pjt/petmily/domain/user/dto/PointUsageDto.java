package com.pjt.petmily.domain.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PointUsageDto {
    private String userEmail;
    private String pointContent;
    private Boolean pointType;
    private Integer pointCost;
    private String adminPassword;
}
