package com.pjt.petmily.domain.social;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;


@Service
public class GoogleLoginServiceImpl implements SocialLoginService{

    private static final String GOOGLE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    private final UserRepository userRepository;

    @Autowired
    public GoogleLoginServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Object> getGoogleUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<Map> response = restTemplate.exchange(GOOGLE_URL, HttpMethod.GET, entity, Map.class);
        Map<String, Object> userInfo = response.getBody();

        if (userInfo != null) {
            String email = (String) userInfo.get("email");
            if (email != null) {
                Optional<User> user = userRepository.findByUserEmail(email);
                if (user == null) {
                    user = Optional.of(new User());
//                    user.setEmail(email);
//                    userRepository.save(user);
                }
            }
        }

        return userInfo;
    }
}
