package com.pjt.petmily.domain.pet.servicce;

import com.pjt.petmily.domain.pet.dto.PetInfoEditDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface PetService {

    // 반려동물 정보 저장
    void petInfoSave(PetInfoEditDto petInfoEditDto, MultipartFile file) throws Exception;

    // 반려동물 정보 수정
    void petInfoUpdate(Long petId, PetInfoEditDto petInfoEditDto, MultipartFile file) throws Exception;

    // 반려동물 정보 삭제
    void petInfoDelete(Long petId);
}
