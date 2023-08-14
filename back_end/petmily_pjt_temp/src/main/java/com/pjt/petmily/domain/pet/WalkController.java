package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.pet.dto.WalkDto;
import com.pjt.petmily.domain.pet.entity.Pet;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.domain.user.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/walk")
public class WalkController {

    private final WalkService walkService;
    private final PointService pointService;

    @Autowired
    private UserRepository userRepository;

    public WalkController(WalkService walkService, PointService pointService) {
        this.walkService = walkService;
        this.pointService = pointService;
    }

    // 산책정보 저장
    @PostMapping("/save")
    @Operation(summary = "산책정보 저장", description = "petId,날짜,거리,시간 요청")
    public ResponseEntity<String> saveWalkData(
            @RequestParam Long petId,
            @RequestParam String walkDate,
            @RequestParam Integer walkDistance,
            @RequestParam Integer walkSpend) {

        String userEmail = walkService.findUserByPet(petId).getUserEmail();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(walkDate);
        walkService.saveWalkInfo(petId, zonedDateTime, walkDistance, walkSpend);
        pointService.updatePoint(true,10, userEmail , "산책");
        return ResponseEntity.ok("정보저장");
    }

    // 산책정보 조회
    @GetMapping("/getPetWalkInfo")
    @Operation(summary = "산책정보 모두조회", description = "petId 요청시 해당 애완동물 모든 산책정보 응답")
    public ResponseEntity<List<Walk>> getWalksByPetId(@RequestParam Long petId) {
        List<Walk> walks = walkService.getAllWalksByPetId(petId);
        return ResponseEntity.ok(walks);
    }

    // 유저정보로 애완동물들 산책정보 조회
    @GetMapping("getUserPetWalkInfo")
    @Operation(summary = "유저펫 산책 정보 모두 조회", description = "userEamil 요청시 해당유저의 애완동무 모든 산책정보 응답")
    public ResponseEntity<?> getWalksByUserEmail(@RequestParam String userEmail) {
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);
        if (userOptional.isEmpty()) {
            // 사용자가 없는 경우 예외 처리 또는 오류 메시지 반환
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();
        List<WalkDto> walksList = new ArrayList<>();
        for (Pet pet : user.getPets()) {
            List<Walk> walks = pet.getWalks();
            WalkDto walkDto = new WalkDto(pet, walks);
            walksList.add(walkDto);
        }

        return ResponseEntity.ok(walksList);
    }

}
