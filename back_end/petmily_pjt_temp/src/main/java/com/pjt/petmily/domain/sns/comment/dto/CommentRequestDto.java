package com.pjt.petmily.domain.sns.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CommentRequestDto {

    private String userEmial;
    private String commentContent;
}
