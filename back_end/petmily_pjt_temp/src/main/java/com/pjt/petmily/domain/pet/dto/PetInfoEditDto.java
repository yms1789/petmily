package com.pjt.petmily.domain.pet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PetInfoEditDto {

    private String userEmail;

    private String petName;
    private String petGender;
    private String petInfo;
    private Long petBirth;
    private Long speciesId;

}
