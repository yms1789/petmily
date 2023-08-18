package com.pjt.petmily.domain.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
@Table(name = "item")
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(name = "item_type", nullable = false)
    private String itemType;

    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "item_img")
    private String itemImg;

    @Column(name = "item_color")
    private String itemColor;

    @Column(name = "item_Rarity")
    private String itemRarity;

    @OneToMany(mappedBy = "item")
    @Builder.Default
    @JsonIgnore
    private List<Inventory> inventoryList = new ArrayList<>();
}
