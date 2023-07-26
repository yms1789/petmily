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
    private String userRegion;

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

    // 유저 권한 설정 메소드
    @Enumerated(EnumType.STRING)
    private Role role;

    // refreshtoken 저장
    public void updateUserToken(String userToken) {
        this.userToken = userToken;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserIsSocial(Boolean userIsSocial) {
        this.userIsSocial = userIsSocial;
    }

}
