package com.pjt.petmily.domain.sns.comment.dto;

import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.sns.board.hashtag.HashTag;
import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.comment.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentWebDto {
    private Long boardId;
    private String boardContent;
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userNickname;
    private String userProfileImageUrl;
    private String userRing;
    private List<String> photoUrls;
    private List<String> hashTags;
    private List<CommentDto> comments;
    private boolean likedByCurrentUser;
    private boolean followedByCurrentUser;

    public static CommentWebDto fromCommentEntity(Comment comment) {
        CommentWebDto commentWebDto = new CommentWebDto();

        // 기존에 설정된 필드들
        commentWebDto.setBoardId(comment.getBoard().getBoardId());
        commentWebDto.setBoardContent(comment.getBoard().getBoardContent());
        commentWebDto.setBoardUploadTime(comment.getBoard().getBoardUploadTime());

        commentWebDto.setUserEmail(comment.getUser().getUserEmail());
        commentWebDto.setUserProfileImageUrl(comment.getUser().getUserProfileImg());
        commentWebDto.setUserNickname(comment.getUser().getUserNickname());

        Optional<Item> ringOptional = comment.getBoard().getUser().getInventoryList().stream()
                .map(Inventory::getItem)
                .filter(item -> "ring".equalsIgnoreCase(item.getItemType()))
                .findFirst();
        ringOptional.ifPresent(ring -> commentWebDto.setUserRing(ring.getItemColor()));

        commentWebDto.setHeartCount(comment.getBoard().getHeartCount());

        List<String> photoUrls = comment.getBoard().getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        commentWebDto.setPhotoUrls(photoUrls);

        List<String> hashTags = comment.getBoard().getHashTagList().stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
        commentWebDto.setHashTags(hashTags);

        List<CommentDto> comments = comment.getBoard().getCommentList().stream()
                .map(CommentDto::fromCommentEntity)
                .collect(Collectors.toList());
        commentWebDto.setComments(comments);

        boolean likedByCurrentUser = comment.getBoard().getHeartList().stream()
                .anyMatch(heart -> heart.getUser().getUserEmail().equals(comment.getUser()));
        commentWebDto.setLikedByCurrentUser(likedByCurrentUser);

        boolean followedByCurrentUser = comment.getBoard().getUser().getFollowingList().stream()
                .anyMatch(follow -> follow.getFollowing().getUserEmail().equals(comment.getUser()));
        commentWebDto.setFollowedByCurrentUser(followedByCurrentUser);
        return commentWebDto;
    }
}
