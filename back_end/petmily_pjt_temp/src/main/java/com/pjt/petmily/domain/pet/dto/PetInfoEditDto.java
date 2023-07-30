package com.pjt.petmily.domain.pet.dto;

import com.pjt.petmily.domain.pet.Pet;
import com.pjt.petmily.domain.user.User;
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

//    public Pet toEntity(){
//        return Pet.builder()
//                .petName(petName)
//                .petGender(petGender)
//                .petInfo(petInfo)
//                .petBirth(petBirth)
//                .speciesId(speciesId)
//                .user(user)
//                .build();
//    }

}
