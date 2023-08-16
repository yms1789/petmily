package com.pjt.petmily.domain.shop.contorller;


import com.pjt.petmily.domain.shop.dto.GetRandomDto;
import com.pjt.petmily.domain.shop.dto.ItemEquipmentDto;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.service.ItemService;
import com.pjt.petmily.domain.user.service.PointService;
import com.pjt.petmily.domain.user.service.PointServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final PointService pointService;
    private final ItemService itemService;

    @PostMapping("item/getRandom")
    @Operation(summary = "뽑기", description = "종류: badge,background,ring,All")
    public ResponseEntity<?> getRandom(@RequestBody GetRandomDto getRandomDto) throws InterruptedException {
        int cost = 10;
        if (getRandomDto.getRandomKind().equals("background")) {
            cost = 30;
        } else if ("badge".equals(getRandomDto.getRandomKind()) || "ring".equals(getRandomDto.getRandomKind())) {
            cost = 20;
        }
        try {
            pointService.updatePoint(false, cost, getRandomDto.getUserEmail(), "뽑기");
        } catch (PointServiceImpl.InsufficientPointsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("포인트 부족");
        }
        Object getItem = itemService.getRandom(getRandomDto.getRandomKind());
        Thread.sleep(3000);
        if ("꽝".equals(getItem)) {
            return ResponseEntity.ok().build();
        } else {
            Item item = (Item) getItem;
            itemService.saveInventory(getRandomDto.getUserEmail(), item.getItemId());
            return ResponseEntity.ok(getItem);
        }
    }

    @GetMapping("item/inventory")
    @Operation(summary = "유저 인벤토리 아이템조회")
    public ResponseEntity<Map<String, List<Item>>> inventory(@RequestParam String userEmail) {
        Map<String,List<Item>> userItem = itemService.getInventory(userEmail);
        return ResponseEntity.ok(userItem);
    }

    @PutMapping("item/equipment")
    @Operation(summary = "아이템 장착 및 해제",description ="이메일, 아이템id, 아이템타입:(ring,background,badge) 요쳥시 장착했는 아이템정보 응답/ 아이템id: null로 요청시 아이템 해제")
    public ResponseEntity<?> equipment(@RequestBody ItemEquipmentDto itemEquipmentDto) {
        Item equipmentItem = itemService.equipment(itemEquipmentDto);
        return ResponseEntity.ok(equipmentItem);

    }
}
