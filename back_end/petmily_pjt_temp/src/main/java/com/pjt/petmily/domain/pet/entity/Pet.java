package com.pjt.petmily.domain.pet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjt.petmily.domain.pet.walk.Walk;
import com.pjt.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@Table(name="pet")
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEmail")
    @JsonIgnore
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
    private String petBirth;

    @Column(nullable=true)
    private String petImg;

    @Column(nullable=true)
    private String speciesName;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Walk> walks = new ArrayList<>();

    }
