package com.pjt.petmily.domain.pet.dto;

import com.pjt.petmily.domain.pet.Pet;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public class PetInfoSaveDto {

    private String petName;
    private String petGender;
    private String petInfo;
    private Long petBirth;
    private Long speciesId;

    private String petImg;

    public Pet toEntity(){
        return Pet.builder().
                petName(petName).
                petGender(petGender).
                petInfo(petInfo).
                petBirth(petBirth).
                speciesId(speciesId).
                petImg(petImg).
                build();
    }
}
