package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public interface WalkService {

    void saveWalkInfo(Long petId, ZonedDateTime walkDate, Integer walkDistance, Integer walkSpend);

    List<Walk> getAllWalksByPetId(Long petId);

    User findUserByPet(Long petId);

    List<Walk> getWalksForUserPets(String userEmail);
}
