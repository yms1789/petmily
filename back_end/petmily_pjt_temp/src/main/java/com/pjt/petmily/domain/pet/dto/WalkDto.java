package com.pjt.petmily.domain.pet.dto;

import com.pjt.petmily.domain.pet.Walk;
import com.pjt.petmily.domain.pet.entity.Pet;
import lombok.Data;

import java.util.List;


@Data
public class WalkDto {
    private Pet pet;
    private List<Walk> walks;

    public WalkDto(Pet pet, List<Walk> walks) {
        this.pet = pet;
        this.walks = walks;
    }

}
