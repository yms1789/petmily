package com.pjt.petmily.domain.sns.board;

import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.bookmark.Bookmark;
import com.pjt.petmily.domain.sns.comment.Comment;
import com.pjt.petmily.domain.sns.hashtag.HashTag;
import com.pjt.petmily.domain.sns.heart.Heart;
import com.pjt.petmily.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
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
    private Long boardUploadTime;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userEmail")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Photo> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Heart> heartList = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<HashTag> hashTagList = new ArrayList<>();
}
