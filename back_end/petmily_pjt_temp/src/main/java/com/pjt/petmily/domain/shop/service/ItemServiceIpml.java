package com.pjt.petmily.domain.shop.service;

import com.pjt.petmily.domain.shop.dto.ItemEquipmentDto;
import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.repository.ItemRepository;
import com.pjt.petmily.domain.shop.repository.UserItemRepository;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemServiceIpml implements ItemService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final UserItemRepository userItemRepository;

    @Autowired // Add this constructor
    public ItemServiceIpml(UserRepository userRepository, ItemRepository itemRepository, UserItemRepository userItemRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.userItemRepository = userItemRepository;
    }


    // 뽑기 로직
    @Override
    public Object getRandom(String randomKind) {
        List<String> data;
        List<Integer> probabilities;
        if (randomKind.equals("All")) {
            data = Arrays.asList("A", "S", "꽝");
            probabilities = Arrays.asList(25, 5, 70);
        } else {
            data = Arrays.asList("A", "S");
            probabilities = Arrays.asList(81, 19);
        }
        List<Integer> cumulativeProbabilities = calculateCumulativeProbabilities(probabilities);
        Random random = new Random();
        int randomValue = random.nextInt(100) + 1;
        String selectedRarity = pickRandomData(data, cumulativeProbabilities, randomValue);

        if ("꽝".equals(selectedRarity)) {
            return "꽝";
        }
        List<Item> items;
        if (randomKind.equals("All")) {
            items = itemRepository.findByItemRarity(selectedRarity)
                    .orElseThrow(() -> new RuntimeException("해당 등급의 아이템이 없습니다 " + selectedRarity));
        } else {
            items = itemRepository.findByItemRarityAndItemType(selectedRarity, randomKind);
        }
        int randomIndex = random.nextInt(items.size());
        return items.get(randomIndex);
    }

    // 확률 누적 리스트
    private List<Integer> calculateCumulativeProbabilities(List<Integer> probabilities) {
        List<Integer> cumulativeProbabilities = new ArrayList<>();
        int sum = 0;
        for (int probability : probabilities) {
            sum += probability;
            cumulativeProbabilities.add(sum);
        }
        return cumulativeProbabilities;
    }

    // 숫자에 따른 데이터값 반환
    private static String pickRandomData(List<String> data, List<Integer> cumulativeProbabilities, int randomValue) {
        for (int i = 0; i < cumulativeProbabilities.size(); i++) {
            if (randomValue <= cumulativeProbabilities.get(i)) {
                return data.get(i);
            }
        }
        return null;
    }

    // 뽑은아이템 유저 인벤토리에저장
    @Override
    public void saveInventory(String userEmail, Long itemId) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("큐레이션을 찾을 수 없습니다."));
        Inventory existingItem = userItemRepository.findByUserAndItem(user, item);
        if (existingItem == null) {
            Inventory inventory = Inventory.builder()
                    .user(user)
                    .item(item)
                    .build();
            userItemRepository.save(inventory);
        }
    }


    @Override
    public Map<String, List<Item>> getInventory(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        List<Inventory> userItems = userItemRepository.findByUser(user);

        Map<String, List<Item>> itemsByItemType = new HashMap<>();

        for (Inventory userItem : userItems) {
            Item item = userItem.getItem();
            String itemType = item.getItemType();

            itemsByItemType.computeIfAbsent(itemType, k -> new ArrayList<>()).add(item);
        }

        return itemsByItemType;
    }




    //아이템 장착
    @Override
    public Item equipment(ItemEquipmentDto itemEquipmentDto) {
        String userEmail = itemEquipmentDto.getUserEmail();
        Long itemId = itemEquipmentDto.getItemId();
        String selectedItemType = itemEquipmentDto.getItemType();

        // 해당 유저를 영속 상태로 가져오기
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Item selectedItem = itemRepository.findByItemId(itemId);

        if (selectedItemType.equals("ring")) {
            user.setUserRing(itemId);
        } else if (selectedItemType.equals("badge")) {
            user.setUserBadge(itemId);
        } else if (selectedItemType.equals("background")) {
            user.setUserBackground(itemId);
        }

        // 수정된 유저 정보를 DB에 저장
        userRepository.save(user);

        return selectedItem;
    }

}
