package com.pjt.petmily.domain.curation.dto;

import com.pjt.petmily.domain.curation.entity.Curation;
import lombok.Data;


@Data
public class PetSpeciesDto {
    private Curation dog;
    private Curation cat;
    private Curation etc;

    public PetSpeciesDto() {
    }

    public PetSpeciesDto(Curation dog, Curation cat, Curation etc) {
        this.dog = dog;
        this.cat = cat;
        this.etc = etc;
    }
}
