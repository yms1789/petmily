package com.pjt.petmily.domain.user;

import jakarta.persistence.*;       //@Entity, @Table import
import jakarta.validation.constraints.NotNull;
import lombok.*;    //lombok method import



@Getter
@NoArgsConstructor
@Entity
@Builder
@Table(name="user")
@AllArgsConstructor
@NamedQuery(name = "User.findByUserEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    private String userEmail;

    @Column(nullable=true)
    private String userPw;

    //refresh token
    @Column(nullable=true)
    private String userToken;

    @Column(nullable=true)
    private String userNickname;

    @Column(nullable=true)
    private String userProfileImg;

    @Column(nullable=true)
    private String userLikePet;

    @Column(nullable=true)
    private Long userPoint;

    @Column(nullable=true)
    private Long userBadge;

    @Column(nullable=true)
    private Long userRing;

    @Column(nullable=true)
    private Long userBackground;

    @Column(nullable=true)
    private Long userLoginDate;

    @Column(nullable=true)
    private Boolean userIsSocial;


    // refreshtoken 저장
    public void updateUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserProfileImg(String userProfileImg) {
        this.userProfileImg = userProfileImg;
    }
    public void setUserNickname(String userNickname) {
        this.userNickname = User.this.userNickname;
    }
    public void setUserLikePet(String userLikePet) {
        this.userLikePet = User.this.userLikePet;
    }
    public void setUserIsSocial(Boolean userIsSocial) {
        this.userIsSocial = userIsSocial;
    }


    /*
    프로필이미지, 닉네임, 선호동물만 수정
     */
    public void updateUserInfo(String userNickname,
                               String userLikePet){
//        this.userProfileImg = userProfileImg;
        this.userNickname = userNickname;
        this.userLikePet = userLikePet;
    }

    public void updateUserImg(String userProfileImg){
        this.userProfileImg = userProfileImg;
    }
}
