package com.pjt.petmily.domain.curation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjt.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Entity
@Data
@Table(name="user_curation")
@NoArgsConstructor
@AllArgsConstructor
public class Curationbookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "c_id")
    @JsonIgnore
    private Curation curation;

    @Builder
    public Curationbookmark(User user, Curation curation) {
        this.user = user;
        this.curation = curation;
    }
}
