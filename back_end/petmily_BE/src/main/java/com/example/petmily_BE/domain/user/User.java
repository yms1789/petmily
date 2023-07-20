package com.example.petmily_BE.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Builder
@Table(name="Users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @NotNull
    @Column(unique = true)
    private String userEmail;

    private String userPw;

    @NotNull
    private String userToken;

    @NotNull
    private String userNickname;
    private String userRegion;
    private String userProfileImg;
    private String userLikePet;
    private long userPoint;
    private long userBadge;
    private long userRing;
    private long userBackground;
    private long userLoginDate;
    private boolean userIsSocial;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    public String getEmail(){
        return userEmail;
    }


    public String getPassword(){
        return userPw;
    }

    public boolean isAccountNonExpired(){
        return true;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.userPw = passwordEncoder.encode(this.userPw);
    }


    // refreshtoke 저장
    public void updateRefreshToken(String updateRefreshToken) {
        this.userToken = updateRefreshToken;
    }

//    @Builder
//    public User(long userId,
//                @NotNull String userEmail,
//                String userPw,
//                @NotNull String userToken,
//                @NotNull String userNickname,
//                String userRegion,
//                String userProfileImg,
//                String userLikePet,
//                long userPoint,
//                long userBadge,
//                long userRing,
//                long userLoginDate,
//                boolean userIsSocial
//                ){
//        this.userId = userId;
//        this.userEmail = userEmail;
//        this.userPw = userPw;
//        this.userToken = userToken;
//        this.userNickname = userNickname;
//        this.userRegion = userRegion;
//        this.userProfileImg = userProfileImg;
//        this.userLikePet = userLikePet;
//        this.userPoint = userPoint;
//        this.userBadge = userBadge;
//        this.userRing = userRing;
//        this.userLoginDate = userLoginDate;
//        this.userIsSocial = userIsSocial;
//    }
}
