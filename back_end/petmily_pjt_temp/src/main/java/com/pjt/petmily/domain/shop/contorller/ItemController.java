package com.pjt.petmily.domain.shop.contorller;


import com.pjt.petmily.domain.shop.dto.GetRandomDto;
import com.pjt.petmily.domain.shop.dto.ItemEquipmentDto;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.service.ItemService;
import com.pjt.petmily.domain.user.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final PointService pointService;
    private final ItemService itemService;

    @PostMapping("item/getRandom")
    @Operation(summary = "뽑기", description = "종류: badge,background,ring,all")
    public ResponseEntity<?> getRandom(@RequestBody GetRandomDto getRandomDto) {
        int cost = 10;
        if (getRandomDto.getRandomKind().equals("background")) {
            cost = 30;
        } else if ("badge".equals(getRandomDto.getRandomKind()) || "ring".equals(getRandomDto.getRandomKind())) {
            cost = 20;
        }
        // 포인트업데이트
        pointService.updatePoint(false, cost, getRandomDto.getUserEmail(), "뽑기");
        // 뽑기로직
        Object getItem = itemService.getRandom(getRandomDto.getRandomKind());
        // 유저 인벤토리에 저장

        // 결과 응답
        if ("꽝".equals(getItem)) {
            return ResponseEntity.ok().build(); // 빈 응답 객체 반환
        } else {
            Item item = (Item) getItem;
            itemService.saveInventory(getRandomDto.getUserEmail(), item.getItemId());
            return ResponseEntity.ok(getItem);
        }
    }

    @GetMapping("item/inventory")
    @Operation(summary = "유저 인벤토리 아이템조회")
    public ResponseEntity<List<Item>> inventory(@RequestParam String userEmail) {
        List<Item> userItem = itemService.getInventory(userEmail);
        return ResponseEntity.ok(userItem);
    }

    // 아이템 장착 및 해제
    @PutMapping("item/equipment")
    @Operation(summary = "아이템 장착 및 해제")
    public ResponseEntity<?> equipment(@RequestBody ItemEquipmentDto itemEquipmentDto) {
        if (itemEquipmentDto.getItemId().equals(null)) {
//            itemService.equipmentCancle(itemEquipmentDto);
            return ResponseEntity.ok("장착해제");
        } else {
            Item equipmentItem = itemService.equipment(itemEquipmentDto);
            return ResponseEntity.ok(equipmentItem);
        }

    }
}
