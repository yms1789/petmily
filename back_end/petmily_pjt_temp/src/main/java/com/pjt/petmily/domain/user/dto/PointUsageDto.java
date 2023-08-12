package com.pjt.petmily.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class PointUsageDto {
    private String userEmail;
    private String pointContent;
    private Boolean pointType;
    private Integer pointCost;
}
