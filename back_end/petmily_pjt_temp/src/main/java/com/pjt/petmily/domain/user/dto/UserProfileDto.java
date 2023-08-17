package com.pjt.petmily.domain.user.dto;

import com.pjt.petmily.domain.pet.dto.PetInfoDto;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.shop.repository.ItemRepository;
import com.pjt.petmily.domain.shop.repository.UserItemRepository;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
/*
 * 게시글 수, 팔로잉, 팔로워 수, 펫정보
 * 기본 유저 정보
 * 내가쓴 게시글/좋아요/ 큐레이션 (api빼서)
 */

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

//            Long ringId = user.getUserRing();
//            if (ringId == null) {
//                userProfileDto.setUserRing(null);
//            } else {
//                String ringOptional = itemRepository.findByItemId(ringId).getItemColor();
//                userProfileDto.setUserRing(ringOptional);
//            }
//
//            Long badgeId = user.getUserBadge();
//            if(badgeId == null) {
//                userProfileDto.setUserBadge(null);
//            } else {
//                String badgeOptional = itemRepository.findByItemId(badgeId).getItemImg();
//                userProfileDto.setUserBadge(badgeOptional);
//            }
//
//            Long background = user.getUserBadge();
//            if(background == null) {
//                userProfileDto.setUserBackground(null);
//            } else {
//                String backgroundOptional = itemRepository.findByItemId(background).getItemImg();
//                userProfileDto.setUserBackground(backgroundOptional);
//            }

            Optional<Item> ringOptional = user.getInventoryList().stream()
                    .map(Inventory::getItem)
                    .filter(item -> "ring".equalsIgnoreCase(item.getItemType()))
                    .findFirst();
            ringOptional.ifPresent(ring -> userProfileDto.setUserRing(ring.getItemColor()));


            Optional<Item> badgeOptional = user.getInventoryList().stream()
                    .map(Inventory::getItem)
                    .filter(item -> "badge".equalsIgnoreCase(item.getItemType()))
                    .findFirst();
            badgeOptional.ifPresent(badge -> userProfileDto.setUserBadge(badge.getItemImg()));

            Optional<Item> backgroundOptional = user.getInventoryList().stream()
                    .map(Inventory::getItem)
                    .filter(item -> "background".equalsIgnoreCase(item.getItemType()))
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
