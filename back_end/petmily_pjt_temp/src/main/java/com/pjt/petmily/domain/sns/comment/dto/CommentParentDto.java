package com.pjt.petmily.domain.sns.comment.dto;

import com.pjt.petmily.domain.sns.comment.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentParentDto {

    private Long commentId;
    private String userEmail;
    private String userNickname;

    public static CommentParentDto fromCommentEntity(Comment comment){
        CommentParentDto commentParentDto = new CommentParentDto();
        commentParentDto.setCommentId(comment.getCommentId());
        commentParentDto.setUserEmail(comment.getUser().getUserEmail());
        commentParentDto.setUserNickname(comment.getUser().getUserNickname());

        return commentParentDto;
    }
}
