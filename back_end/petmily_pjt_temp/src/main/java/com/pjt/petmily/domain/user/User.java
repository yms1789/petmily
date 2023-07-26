package com.pjt.petmily.domain.user;

import jakarta.persistence.*;       //@Entity, @Table import
import jakarta.validation.constraints.NotNull;
import lombok.*;    //lombok method import
import org.springframework.security.crypto.password.PasswordEncoder;


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
    @Column(name = "u_id")
    private Long userId;

    @NotNull
    private String userEmail;


    private String userPw;

    //refresh token
    private String userToken;

    private String userNickname;
    private String userRegion;
    private String userProfileImg;
    private String userLikePet;
    private Long userPoint;
    private Long userBadge;
    private Long userRing;
    private Long userBackground;
    private Long userLoginDate;
    private Boolean userIsSocial;

    // 유저 권한 설정 메소드
    @Enumerated(EnumType.STRING)
    private Role role;

    // 비밀번호 암호화 메소드
//    public void passwordEncode(PasswordEncoder passwordEncoder){
//
//        this.userPw = passwordEncoder.encode(this.userPw);
//    }

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
