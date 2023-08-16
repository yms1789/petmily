package com.pjt.petmily.domain.pet.servicce;

import com.pjt.petmily.domain.pet.exception.PetException;
import com.pjt.petmily.domain.pet.dto.PetInfoEditDto;
import com.pjt.petmily.domain.pet.entity.Pet;
import com.pjt.petmily.domain.pet.repository.PetRepository;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class PetServiceImpl implements PetService{

    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final UserRepository userRepository;

    private final com.pjt.petmily.global.awss3.service.S3Uploader s3Uploader;

    @Autowired
    public PetServiceImpl(PetRepository petRepository, UserRepository userRepository, com.pjt.petmily.global.awss3.service.S3Uploader s3Uploader) {
        this.petRepository = petRepository;
        this.userRepository = userRepository;
        this.s3Uploader = s3Uploader;

    }


    // 반려동물 정보 저장
    @Override
    public void petInfoSave(PetInfoEditDto petInfoEditDto, MultipartFile file) throws Exception {
        User user = userRepository.findByUserEmail(petInfoEditDto.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + petInfoEditDto.getUserEmail()));

        Optional<String> petProfileImg = file == null? Optional.empty() : s3Uploader.uploadFile(file, "pet");

        Pet pet = Pet.builder()
                .user(user)
                .petName(petInfoEditDto.getPetName())
                .petGender(petInfoEditDto.getPetGender())
                .petInfo(petInfoEditDto.getPetInfo())
                .petBirth(petInfoEditDto.getPetBirth())
                .speciesName(petInfoEditDto.getSpeciesName())
                .build();

        if(petProfileImg.isPresent()) {
            pet.setPetImg(petProfileImg.get());
        }

        petRepository.save(pet);
    };


    // 반려동물 정보 수정
    @Override
    public void petInfoUpdate(Long petId, PetInfoEditDto petInfoEditDto, MultipartFile file) throws Exception {
        // 먼저 반려동물 ID로 기존 반려동물을 찾습니다.
        Pet pet = petRepository.findByPetId(petId)
                .orElseThrow(() -> new Exception("Pet not found with id: " + petId));

        // 사용자를 찾습니다. 이때, 사용자가 없으면 예외를 발생시킵니다.
        User user = userRepository.findByUserEmail(petInfoEditDto.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + petInfoEditDto.getUserEmail()));

        // 파일을 S3에 업로드하고, 그 URL을 가져옵니다.
        Optional<String> petProfileImgOptional = file == null? Optional.empty() : s3Uploader.uploadFile(file, "pet");

        // pet 엔티티의 필드를 업데이트합니다.
        pet.setUser(user);
        pet.setPetName(petInfoEditDto.getPetName());
        pet.setPetGender(petInfoEditDto.getPetGender());
        pet.setPetInfo(petInfoEditDto.getPetInfo());
        pet.setPetBirth(petInfoEditDto.getPetBirth());
        pet.setSpeciesName(petInfoEditDto.getSpeciesName());
        if(petProfileImgOptional.isPresent()){
            String petProfileImg = petProfileImgOptional.get();
            pet.setPetImg(petProfileImg);
        }
        // 업데이트된 pet 엔티티를 저장합니다.
        petRepository.save(pet);
    }


    // 반려동물 정보 삭제
    @Override
    public void petInfoDelete(Long petId) {
        Optional<Pet> petOptional = petRepository.findByPetId(petId);
        if (petOptional.isPresent()) {
            try {
                petRepository.delete(petOptional.get());
            } catch (Exception e) {
                throw new PetException.PetDeletionException("반려동물 정보 삭제 실패 " + petId);
            }
        } else {
            throw new PetException.PetNotFoundException("반려동물 정보가 없음" + petId);
        }
    }

}
