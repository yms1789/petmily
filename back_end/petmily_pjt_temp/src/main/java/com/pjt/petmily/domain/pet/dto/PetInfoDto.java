package com.pjt.petmily.domain.pet.dto;

import com.pjt.petmily.domain.pet.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PetInfoDto {
    private Long petId;
    private String petName;
    private String petImg;
    private String petGender;
    private String petInfo;
    private String petBirth;
    private String speciesName;

    public PetInfoDto(Pet pet){
        this.petId = pet.getPetId();
        this.petName = pet.getPetName();
        this.petImg = pet.getPetImg();
        this.petGender = pet.getPetGender();
        this.petInfo = pet.getPetInfo();
        this.petBirth = pet.getPetBirth();
        this.speciesName = pet.getSpeciesName();
    }

}
