package com.pjt.petmily.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "inventory")
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(name = "owned_item_id", nullable = false)
    private Long itemType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;


}
