package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.user.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/walk")
public class WalkController {

    private final WalkService walkService;
    private final PointService pointService;

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
        walkService.saveWalkInfo(petId, LocalDateTime.parse(walkDate), walkDistance, walkSpend);
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
}
