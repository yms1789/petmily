package com.pjt.petmily.domain.curation.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name="curation")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Curation {

    @OneToMany(mappedBy = "curation")
    @JsonIgnoreProperties("curation")
    private List<Curationbookmark> curationBookmarks = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cId;

    @NotNull
    private String cTitle;

    @Column(nullable = true)
    private String cPetSpecies;

    @Column(nullable=true)
    private String cContent;

    @Column(nullable=true)
    private String cImage;

    @Column(nullable=true)
    private String cUrl;

    @Column(nullable = true)
    private String cDate;

    @Column
    private String cCategory;

    @Column
    private Integer cBookmarkCnt;



}
