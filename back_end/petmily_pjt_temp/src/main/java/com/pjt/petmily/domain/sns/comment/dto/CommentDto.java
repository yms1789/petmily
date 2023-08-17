package com.pjt.petmily.domain.sns.comment.dto;

import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.sns.comment.entity.Comment;
import com.pjt.petmily.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;


@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String commentContent;
    private LocalDateTime commentTime;
    private String userEmail;
    private String userNickname; // 유저 닉네임 필드 추가
    private String userProfileImg; // 유저 프로필 사진 URL 필드 추가
    private Long boardId;
    private Long parentId;
    private String userRing;

    public static CommentDto fromCommentEntity(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setCommentContent(comment.getCommentContent());
        commentDto.setCommentTime(comment.getCommentTime());
        commentDto.setUserEmail(comment.getUser().getUserEmail());

        // 유저 닉네임과 프로필 사진 URL 세팅
        commentDto.setUserNickname(comment.getUser().getUserNickname());
        commentDto.setUserProfileImg(comment.getUser().getUserProfileImg());

        commentDto.setBoardId(comment.getBoard().getBoardId());

        User user = comment.getUser();
        Long ringId = user.getUserRing();
        Optional<Item> ringOptional = user.getInventoryList().stream()
                .map(Inventory::getItem)
                .filter(item -> item.getItemId().equals(ringId))
                .findFirst();
        ringOptional.ifPresent(ring -> commentDto.setUserRing(ring.getItemColor()));

        if (comment.getParent() != null) {
            commentDto.setParentId(comment.getParent().getCommentId());
        }

        return commentDto;
    }
}
