package com.pjt.petmily.domain.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemEquipmentDto {
   String userEmail;
   Long itemId;
   String itemType;
}
