package com.pjt.petmily.global.FCM;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FirebaseCloudMessageService firebaseCloudMessageService;
    private final UserRepository userRepository;

    @PostMapping("/fcm/token/save")
    public ResponseEntity pushMessage(@RequestBody RequestDTO requestDTO) throws IOException {
        User user = userRepository.findByUserEmail(requestDTO.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자가 존재하지 않습니다"));

        user.setFcmToken(requestDTO.getFcmToken());
        userRepository.save(user);
        return ResponseEntity.ok("FCM TOKEN 저장 성공");
    }
}