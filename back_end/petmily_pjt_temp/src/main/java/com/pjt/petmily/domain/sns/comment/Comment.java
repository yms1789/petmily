package com.pjt.petmily.domain.sns.comment;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name="comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String commentContent;
    private Long commentTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEmail")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="BoardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment")
    private Comment comment;

    @OneToMany(mappedBy = "comment")
    private List<Comment> commentList = new ArrayList<>();

}
