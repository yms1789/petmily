package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.pet.entity.Pet;
import com.pjt.petmily.domain.pet.repository.PetRepository;
import com.pjt.petmily.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalkServiceImpl implements WalkService{

    private final WalkRepository walkRepository;
    private final PetRepository petRepository;

    public WalkServiceImpl(WalkRepository walkRepository, PetRepository petRepository) {
        this.walkRepository = walkRepository;
        this.petRepository = petRepository;
    }

    // 산책정보 저장
    @Override
    public void saveWalkInfo(Long petId, LocalDateTime walkDate, Integer walkDistance, Integer walkSpend) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("PetID를 찾을 수 없음: " + petId));
        Walk walk = new Walk();
        walk.setWalkDate(walkDate);
        walk.setWalkDistance(walkDistance);
        walk.setWalkSpend(walkSpend);
        walk.setPet(pet);

        walkRepository.save(walk);
    }


    // 산책정보 조회
    @Override
    public List<Walk> getAllWalksByPetId(Long petId) {
        return walkRepository.findAllByPet_PetId(petId);
    }

    // 펫id로 유저(주인)정보 가져오기
    @Override
    public User findUserByPet(Long petId) {
        Pet pet = petRepository.findByPetId(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found with petId: " + petId));
        return pet.getUser();
    }

}
