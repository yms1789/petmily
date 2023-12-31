package com.pjt.petmily.domain.sns.heart.service;

import com.pjt.petmily.domain.sns.heart.dto.HeartRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface HeartService {

    void insert(HeartRequestDto heartRequestDTO) throws Exception;
    void delete(HeartRequestDto heartRequestDTO);

}
