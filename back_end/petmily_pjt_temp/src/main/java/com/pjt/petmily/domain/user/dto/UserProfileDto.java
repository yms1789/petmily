package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.pet.dto.PetInfoDto;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserProfileDto {

    private Long userId;
    private String userEmail;
    private String userNickname;
    private String userProfileImg;
    private String userRing;
    private String userBadge;
    private String userBackground;
    private Integer followingCount;
    private Integer followerCount;
    private Integer boardCount;

    private List<PetInfoDto> userPets;


    public static UserProfileDto fromUserEntity(Optional<User> userOptional){

        UserProfileDto userProfileDto = new UserProfileDto();

        if(userOptional.isPresent()){
            User user = userOptional.get();

            userProfileDto.setUserId(user.getUserId());
            userProfileDto.setUserEmail(user.getUserEmail());
            userProfileDto.setUserNickname(user.getUserNickname());

            Long ringId = user.getUserRing();
            Long badgeId = user.getUserBadge();
            Long backgroundId = user.getUserBadge();

            Optional<Item> ringOptional = user.getInventoryList().stream()
                    .map(Inventory::getItem)
                    .filter(item -> item.getItemId().equals(ringId))
                    .findFirst();
            ringOptional.ifPresent(ring -> userProfileDto.setUserRing(ring.getItemColor()));
            
            Optional<Item> badgeOptional = user.getInventoryList().stream()
                    .map(Inventory::getItem)
                    .filter(item -> item.getItemId().equals(badgeId))
                    .findFirst();
            badgeOptional.ifPresent(badge -> userProfileDto.setUserBadge(badge.getItemImg()));

            Optional<Item> backgroundOptional = user.getInventoryList().stream()
                    .map(Inventory::getItem)
                    .filter(item -> item.getItemId().equals(backgroundId))
                    .findFirst();
            backgroundOptional.ifPresent(background -> userProfileDto.setUserBackground(background.getItemImg()));

            userProfileDto.setUserProfileImg(user.getUserProfileImg());

            userProfileDto.setFollowingCount(user.getFollowingList().size());
            userProfileDto.setFollowerCount(user.getFollowerList().size());

            userProfileDto.setBoardCount(user.getBoardList().size());

            List<PetInfoDto> userPets = user.getPets().stream()
                    .map(PetInfoDto::fromPetEntity)
                    .collect(Collectors.toList());
            userProfileDto.setUserPets(userPets);
        }
        return userProfileDto;
    }
}
