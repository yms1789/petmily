package com.pjt.petmily.domain.shop.service;

import com.pjt.petmily.domain.shop.dto.ItemEquipmentDto;
import com.pjt.petmily.domain.shop.entity.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ItemService {
    Object getRandom(String randomKind);

    void saveInventory(String userEmail, Long itemId);

    Map<String, List<Item>> getInventory(String userEmail);

    Item equipment(ItemEquipmentDto itemEquipmentDto);

}
