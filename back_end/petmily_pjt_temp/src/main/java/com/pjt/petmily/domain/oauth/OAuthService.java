package com.pjt.petmily.domain.oauth;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import com.nimbusds.jose.shaded.gson.JsonParser;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.repository.ItemRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.LoginResponseDto;
import com.pjt.petmily.domain.user.dto.ResponseDto;
import com.pjt.petmily.domain.user.dto.UserLoginInfoDto;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ItemRepository itemRepository;

    public OAuthService(UserRepository userRepository, JwtService jwtService, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public String getKakaoAccessToken(String code) {
        System.out.println(code);

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");

            sb.append("&client_id=66c5ba77d82e4dbed66a1f8fc91f00bd"); //본인이 발급받은 keywew
            sb.append("&redirect_uri=http://i9d209.p.ssafy.io/oauth/callback/kakao"); // 본인이 설정한 주소


            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public ResponseDto<LoginResponseDto> getUserInfo(String access_Token) {
        // 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
//        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonObject element = JsonParser.parseString(result).getAsJsonObject();

            JsonObject kakao_account = element.getAsJsonObject("kakao_account");

            String userEmail = kakao_account.getAsJsonObject().get("email").getAsString();

            System.out.println("kakao_email : " + kakao_account);

            User user = saveUserInfo(userEmail);

            String accessToken = JwtService.createAccessToken(userEmail);

            Item ERing = itemRepository.findByItemId(user.getUserRing());
            Item EBackground = itemRepository.findByItemId(user.getUserBackground());
            Item EBadge = itemRepository.findByItemId(user.getUserBadge());

            UserLoginInfoDto userLoginInfoDto = new UserLoginInfoDto();
            userLoginInfoDto.setUserEmail(user.getUserEmail());
            userLoginInfoDto.setUserToken(user.getUserToken());
            userLoginInfoDto.setUserNickname(user.getUserNickname());
            userLoginInfoDto.setUserProfileImg(user.getUserProfileImg());
            userLoginInfoDto.setUserLikePet(user.getUserLikePet());
            userLoginInfoDto.setUserBadge(EBadge);
            userLoginInfoDto.setUserRing(ERing);
            userLoginInfoDto.setUserBackground(EBackground);
            userLoginInfoDto.setUserLoginDate(user.getUserLoginDate());
            userLoginInfoDto.setUserIsSocial(user.getUserIsSocial());
            userLoginInfoDto.setUserAttendance(user.getUserAttendance());

            loginResponseDto = new LoginResponseDto(accessToken, userLoginInfoDto);

//            userInfo.put("email", userEmail);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseDto.setSucess("소셜로그인",loginResponseDto);
    }

    private User saveUserInfo(String userEmail) {
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);
        // 이메일이 존재하지않을때
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setUserEmail(userEmail);
            user.setUserIsSocial(true);
            user.setUserPoint(0L);

            String userToken = JwtService.createRefreshToken(userEmail);
//            String accessToken = JwtService.createAccessToken(userEmail);
            System.out.println("jwt :" + userToken);
            user.updateUserToken(userToken);

            userRepository.save(user);
            return user;
            // 이메일이 존재할때
        } else {
            User existingUser = userOptional.get();

            // 이미 존재하는 사용자라면 토큰을 새로 발급
            String userToken = JwtService.createRefreshToken(userEmail);
            System.out.println("jwt :" + userToken);
            existingUser.updateUserToken(userToken);

            userRepository.save(existingUser);
            return existingUser;
        }
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(google, google, naver)에서 가져온 유저 정보를 담고있음
        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름(ex. google, naver, google)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들
        return null;
    }
}

