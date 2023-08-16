package com.pjt.petmily.domain.sns.heart.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HeartRequestDto {

    private String userEmail;
    private Long boardId;

    public HeartRequestDto(String userEmail, Long boardId){
        this.userEmail = userEmail;
        this.boardId = boardId;
    }

}
