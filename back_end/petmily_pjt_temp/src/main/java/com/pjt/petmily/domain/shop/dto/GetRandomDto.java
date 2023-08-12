package com.pjt.petmily.domain.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRandomDto {
    String userEmail;
    String randomKind;
}
