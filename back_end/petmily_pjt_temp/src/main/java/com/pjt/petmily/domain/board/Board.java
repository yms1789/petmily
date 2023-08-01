//package com.pjt.petmily.domain.board;
//
//import com.pjt.petmily.domain.user.User;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Getter
//@Entity
//@NoArgsConstructor
//@Table(name = 'board')
//public class Board {
//    // 게시글 ID, 작성자, 작성내용, 작성시간
//    @Id
//    private Long BoardId;
//
//    private String BoardContent;
//    private Long BoardUploadTime;
//
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userEmail")
//    private User user;
//}
