package com.pjt.petmily.domain.sns.comment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentWebRequestDto {
        private String userEmail;
        private Long boardId;
        private String commentContent;
        private Long parentId;
}
