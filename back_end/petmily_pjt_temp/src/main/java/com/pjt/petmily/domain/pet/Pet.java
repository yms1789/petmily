package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@Table(name="pet")
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEmail")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long petId;

    @NotNull
    private String petName;

    @Column(nullable=true)
    private String petGender;

    @Column(nullable=true)
    private String petInfo;

    @Column(nullable=true)
    private Long petBirth;

    @Column(nullable=true)
    private String petImg;

    @Column(nullable=true)
    private Long speciesId;

//    @Builder
//    public updatePet(String petName,
//                     String petGender,
//                     String petInfo,
//                     Long petBirth,
//                     String petImg,
//                     Long speciesId){
//        this.petName = petName;
//        this.petGender = petGender;
//        this.petInfo = petInfo;
//        this.petBirth = petBirth;
//        this.petImg = petImg;
//        this.speciesId = speciesId;
//
//        this.user.updateUserPet(this);
//    }

//    public updatePet(String petName, String petGender, String petInfo, Long petBirth, String petImg, Long speciesId){
//        this.petName = petName;
//        this.petGender = petGender;
//        this.petInfo = petInfo;
//        this.petBirth = petBirth;
//        this.petImg = petImg;
//        this.speciesId = speciesId;
//
//        this.user.updateUserPet(this);
    }
