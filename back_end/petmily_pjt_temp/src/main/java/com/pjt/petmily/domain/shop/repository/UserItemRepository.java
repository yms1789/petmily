package com.pjt.petmily.domain.shop.repository;

import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserItemRepository extends JpaRepository<Inventory, Long> {
    Inventory findByUserAndItem(User user, Item item);

    List<Inventory> findByUser(User user);
}
