package com.pjt.petmily.domain.sns.board.hashtag;

import com.pjt.petmily.domain.sns.board.Board;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;
}
