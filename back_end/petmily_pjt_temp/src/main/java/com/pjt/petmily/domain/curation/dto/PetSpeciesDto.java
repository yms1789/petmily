package com.pjt.petmily.domain.curation.dto;

import com.pjt.petmily.domain.curation.Curation;
import lombok.Builder;
import lombok.Data;

import java.util.List;


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
