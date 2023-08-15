package com.pjt.petmily.domain.noti.controller;

import com.pjt.petmily.domain.noti.dto.NotiDto;
import com.pjt.petmily.domain.noti.entity.Noti;
import com.pjt.petmily.domain.noti.repository.NotiRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NotiController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotiRepository notiRepository;

    @GetMapping("/noti/{userEmail}")
    @Operation(summary = "알림 목록 반환", description = "최근 알림 목록 10개 반환")
    public ResponseEntity<List<NotiDto>> getNotification(@PathVariable String userEmail){
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 찾을 수 없음: " + userEmail));

        List<Noti> notiList = notiRepository.findByToUserOrderByIdDesc(user);

        List<NotiDto> notiDtoList = notiList.stream()
                .limit(10)
                .map(NotiDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notiDtoList);
    }

    @GetMapping("/noti/status/{userEmail}")
    @Operation(summary = "알림 확인")
    public ResponseEntity<?> statusNotification(@PathVariable String userEmail){
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 찾을 수 없음: " + userEmail));

        List<Noti> notiList = notiRepository.findByToUserAndIsCheckedFalseOrderByIdDesc(user);
        // 해당 알림들의 isChecked 상태를 true로 변경하고 저장
        notiList.forEach(noti -> {
            noti.setChecked(true);
            notiRepository.save(noti);
        });
        return ResponseEntity.ok("알림확인 완료");
    }

    @GetMapping("/noti/checked/{userEmail}")
    @Operation(summary = "알림 존재 여부 알림")
    public ResponseEntity<Boolean> checkNotification(@PathVariable String userEmail){
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 찾을 수 없음: " + userEmail));

        boolean hasUncheckedNotifications = notiRepository.existsByToUserAndIsCheckedFalse(user);

        return ResponseEntity.ok(hasUncheckedNotifications);
    }

}

