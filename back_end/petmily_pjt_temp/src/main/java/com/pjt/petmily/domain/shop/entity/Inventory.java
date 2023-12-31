package com.pjt.petmily.domain.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pjt.petmily.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Entity
@Table(name = "user_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    @JsonIgnore
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
