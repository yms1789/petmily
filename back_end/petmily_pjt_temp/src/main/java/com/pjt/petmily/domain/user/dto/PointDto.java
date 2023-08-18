package com.pjt.petmily.domain.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PointDto {
    private String pointContent;
    private Boolean pointType;
    private Integer pointCost;
    private LocalDateTime pointUsageDate;
}
