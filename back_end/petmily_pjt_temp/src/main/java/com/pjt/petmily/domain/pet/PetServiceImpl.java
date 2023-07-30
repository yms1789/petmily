package com.pjt.petmily.domain.pet;

import com.pjt.petmily.domain.pet.dto.PetInfoEditDto;
import com.pjt.petmily.domain.user.User;
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

        String petProfileImg = file == null? null : s3Uploader.uploadFile(file, "pet");

        Pet pet = Pet.builder()
                .user(user)
                .petName(petInfoEditDto.getPetName())
                .petGender(petInfoEditDto.getPetGender())
                .petInfo(petInfoEditDto.getPetInfo())
                .petBirth(petInfoEditDto.getPetBirth())
                .speciesId(petInfoEditDto.getSpeciesId())
                .petImg(petProfileImg)
                .build();


        petRepository.save(pet);
    };

    // 반려동물 정보 수정
    @Override
    public void petInfoUpdate(Long petId, PetInfoEditDto petInfoEditDto){

    };

    // 반려동물 정보 삭제
    @Override
    public void petInfoDelete(Long petId) {
        Optional<Pet> petOptional = petRepository.findById(petId);
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
