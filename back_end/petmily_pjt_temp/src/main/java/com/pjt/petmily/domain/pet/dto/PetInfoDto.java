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

    public static PetInfoDto fromPetEntity(Pet pet){
        PetInfoDto petInfoDto = new PetInfoDto();

        petInfoDto.setPetId(pet.getPetId());
        petInfoDto.setPetName(pet.getPetName());
        petInfoDto.setPetImg(pet.getPetImg());
        petInfoDto.setPetGender(pet.getPetGender());
        petInfoDto.setPetInfo(pet.getPetInfo());
        petInfoDto.setPetBirth(pet.getPetBirth());
        petInfoDto.setSpeciesName(pet.getSpeciesName());

        return petInfoDto;
    }

}
