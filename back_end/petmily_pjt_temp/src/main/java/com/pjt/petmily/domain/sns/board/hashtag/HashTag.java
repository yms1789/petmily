package com.pjt.petmily.domain.sns.board.hashtag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pjt.petmily.domain.sns.board.entity.Board;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@Table(name="hashtag")
@NoArgsConstructor
@AllArgsConstructor
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hashTagId;

    private String hashTagName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boardId")
    @JsonBackReference
    private Board board;
}
