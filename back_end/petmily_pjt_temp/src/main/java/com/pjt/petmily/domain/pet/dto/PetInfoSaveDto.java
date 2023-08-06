package com.pjt.petmily.domain.pet.dto;

import com.pjt.petmily.domain.pet.Pet;

public class PetInfoSaveDto {

    private String petName;
    private String petGender;
    private String petInfo;
    private String petBirth;
    private String speciesName;

    private String petImg;

    public Pet toEntity(){
        return Pet.builder().
                petName(petName).
                petGender(petGender).
                petInfo(petInfo).
                petBirth(petBirth).
                speciesName(speciesName).
                petImg(petImg).
                build();
    }
}
