package com.pjt.petmily.global.jwt.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

//    @Value("${jwt.secret}")
    private static final String jwtSecret = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

//    @Value("${jwt.access.expiration}")
//    private Long accessTokenExpirationPeriod;
//
//    @Value("${jwt.refresh.expiration}")
//    private Long refreshTokenExpirationPeriod;
//
//    @Value("${jwt.access.header}")
//    private String accessHeader;
//
//    @Value("${jwt.refresh.header}")
//    private String refreshHeader;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)' 형식
     */

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer";

    private static final UserRepository userRepository = null;



    /**
     * AccessToken 생성 메소드
     */
    public static String createAccessToken(String email) {
        Date now = new Date();
//        Date expiration = new Date(now.getTime() + Duration.ofHours(1).toMillis());
        //test용 1분
        Date expiration = new Date(now.getTime() + Duration.ofMinutes(1).toMillis());
        return Jwts.builder()     //JWT토큰을 생성하는 빌더 생성
                .claim("userEmail", email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }


    public static String createRefreshToken(String email){
        Date now = new Date();
        Date expiration = new Date(now.getTime() + Duration.ofDays(30).toMillis());
        return Jwts.builder()
                .claim("userEmail", email)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public static boolean validateToken(String accessToken) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(accessToken);
            return true; // 토큰이 올바르고 유효한 경우
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었습니다.");
            return false; // 만료된 토큰인 경우
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다.");
            return false; // 올바르지 않은 토큰인 경우
        }
    }

    // Access Token에서 userEmail 추출
    public static String extractUserEmailFromAccessToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims.get("userEmail", String.class);
        } catch (Exception e) {
            return null; // 올바르지 않은 토큰인 경우
        }
    }

    // access토큰 유효성검사
    public static boolean isUserValid(String userEmail) {
        return userRepository.findByUserEmail(userEmail) != null;
    }

    // 이메일로 DB의 refresh 가져오기
    public static String refreshtokenCheck(String userEmail) {
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String userToken = user.getUserToken(); // 이 부분을 추가해 가져오려는 userToken 필드에 맞게 수정해주세요.
            return userToken;
        } else {
            return null; // 사용자가 존재하지 않을 경우에는 null 반환 또는 다른 처리를 하실 수 있습니다.
        }
    }




//
//    /**
//     * AccessToken 헤더에 실어서 보내기
//     */
//    public void sendAccessToken(HttpServletResponse response, String accessToken) {
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        response.setHeader(accessHeader, accessToken);
//        log.info("재발급된 Access Token : {}", accessToken);
//    }
//
//    /**
//     * AccessToken + RefreshToken 헤더에 실어서 보내기
//     */
//    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
//        response.setStatus(HttpServletResponse.SC_OK);
//
//        setAccessTokenHeader(response, accessToken);
//        setRefreshTokenHeader(response, refreshToken);
//        log.info("Access Token, Refresh Token 헤더 설정 완료");
//    }
//
//    /**
//     * 헤더에서 RefreshToken 추출
//     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
//     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
//     */
//    public Optional<String> extractRefreshToken(HttpServletRequest request) {
//        return Optional.ofNullable(request.getHeader(refreshHeader))
//                .filter(refreshToken -> refreshToken.startsWith(BEARER))
//                .map(refreshToken -> refreshToken.replace(BEARER, ""));
//    }
//
//    /**
//     * 헤더에서 AccessToken 추출
//     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
//     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
//     */
//    public Optional<String> extractAccessToken(HttpServletRequest request) {
//        return Optional.ofNullable(request.getHeader(accessHeader))
//                .filter(refreshToken -> refreshToken.startsWith(BEARER))
//                .map(refreshToken -> refreshToken.replace(BEARER, ""));
//    }
//
//    /**
//     * AccessToken에서 Email 추출
//     * 추출 전에 JWT.require()로 검증기 생성
//     * verify로 AceessToken 검증 후
//     * 유효하다면 getClaim()으로 이메일 추출
//     * 유효하지 않다면 빈 Optional 객체 반환
//     */
//    public Optional<String> extractEmail(String accessToken) {
//        try {
//            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
//            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
//                    .build() // 반환된 빌더로 JWT verifier 생성
//                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
//                    .getClaim(EMAIL_CLAIM) // claim(Emial) 가져오기
//                    .asString());
//        } catch (Exception e) {
//            log.error("액세스 토큰이 유효하지 않습니다.");
//            return Optional.empty();
//        }
//    }
//
//    /**
//     * AccessToken 헤더 설정
//     */
//    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
//        response.setHeader(accessHeader, accessToken);
//    }
//
//    /**
//     * RefreshToken 헤더 설정
//     */
//    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
//        response.setHeader(refreshHeader, refreshToken);
//    }
//
//    /**
//     * RefreshToken DB 저장(업데이트)
//     */
//    public void updateRefreshToken(String email, String refreshToken) {
//        userRepository.findByUserEmail(email)
//                .ifPresentOrElse(
//                        user -> user.updateUserToken(refreshToken),
//                        () -> new Exception("일치하는 회원이 없습니다.")
//                );
//    }
//
//    public boolean isTokenValid(String token) {
//        try {
//            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
//            return true;
//        } catch (Exception e) {
//            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
//            return false;
//        }
//    }
}
