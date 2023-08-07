package com.pjt.petmily.domain.sns.board;

import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.comment.Comment;
import com.pjt.petmily.domain.sns.board.hashtag.HashTag;
import com.pjt.petmily.domain.sns.heart.Heart;
import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board")
public class Board {
    // 게시글 ID, 작성자, 작성내용, 작성시간
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String boardContent;

    @Column(nullable = false, updatable = false)
    private LocalDateTime boardUploadTime;

    private int heartCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEmail")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Photo> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Heart> heartList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    @Builder.Default
    private List<HashTag> hashTagList = new ArrayList<>();

}
