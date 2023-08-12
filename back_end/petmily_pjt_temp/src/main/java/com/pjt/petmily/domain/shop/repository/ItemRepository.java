package com.pjt.petmily.domain.shop.repository;


import com.pjt.petmily.domain.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<List<Item>> findByItemRarity(String selectedRarity);


    List<Item> findByItemRarityAndItemType(String selectedRarity, String randomKind);

    Item findByItemId(Long itemId);
}
