package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.shop.entity.Inventory;
import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.sns.board.entity.Board;
import com.pjt.petmily.domain.sns.board.hashtag.HashTag;
import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResponseBoardAllDto {
    private Long boardId;
    private String boardContent;
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userProfileImageUrl;
    private String userNickname;
    private String userRing;
    private List<String> photoUrls;
    private List<String> hashTags;
    private List<CommentDto> comments;
    private boolean likedByCurrentUser;
    private boolean followedByCurrentUser;


    public static ResponseBoardAllDto toBoardDto(Board board, String currentUserEmail) {
        ResponseBoardAllDto boardDto = new ResponseBoardAllDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardUploadTime(board.getBoardUploadTime());
        boardDto.setUserEmail(board.getUser().getUserEmail());
        boardDto.setUserProfileImageUrl(board.getUser().getUserProfileImg());
        boardDto.setUserNickname(board.getUser().getUserNickname());

        Optional<Item> ringOptional = board.getUser().getInventoryList().stream()
                .map(Inventory::getItem)
                .filter(item -> "ring".equalsIgnoreCase(item.getItemType()))
                .findFirst();
        ringOptional.ifPresent(ring -> boardDto.setUserRing(ring.getItemColor()));

        boardDto.setHeartCount(board.getHeartCount());  // 게시글의 좋아요 수 설정

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardDto.setPhotoUrls(photoUrls);

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
        boardDto.setHashTags(hashTags);

        List<CommentDto> comments = board.getCommentList().stream()
                .map(CommentDto::fromCommentEntity)
                .collect(Collectors.toList());
        boardDto.setComments(comments);

        boolean likedByCurrentUser = board.getHeartList().stream()
                .anyMatch(heart -> heart.getUser().getUserEmail().equals(currentUserEmail));
        boardDto.setLikedByCurrentUser(likedByCurrentUser);

        boolean followedByCurrentUser = board.getUser().getFollowingList().stream()
                .anyMatch(follow -> follow.getFollowing().getUserEmail().equals(currentUserEmail));
        boardDto.setFollowedByCurrentUser(followedByCurrentUser);

        return boardDto;
    }

    public static ResponseBoardAllDto fromBoardEntity(Board board, String currentUserEmail) {
        ResponseBoardAllDto boardDto = new ResponseBoardAllDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardUploadTime(board.getBoardUploadTime());
        boardDto.setUserEmail(board.getUser().getUserEmail());
        boardDto.setUserProfileImageUrl(board.getUser().getUserProfileImg());
        boardDto.setUserNickname(board.getUser().getUserNickname());

        Optional<Item> ringOptional = board.getUser().getInventoryList().stream()
                .map(Inventory::getItem)
                .filter(item -> "ring".equalsIgnoreCase(item.getItemType()))
                .findFirst();
        ringOptional.ifPresent(ring -> boardDto.setUserRing(ring.getItemColor()));


        boardDto.setHeartCount(board.getHeartCount());  // 게시글의 좋아요 수 설정

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardDto.setPhotoUrls(photoUrls);

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
        boardDto.setHashTags(hashTags);

        List<CommentDto> comments = board.getCommentList().stream()
                .map(CommentDto::fromCommentEntity)
                .collect(Collectors.toList());
        boardDto.setComments(comments);

        boolean likedByCurrentUser = board.getHeartList().stream()
                .anyMatch(heart -> heart.getUser().getUserEmail().equals(currentUserEmail));
        boardDto.setLikedByCurrentUser(likedByCurrentUser);

        boolean followedByCurrentUser = board.getUser().getFollowingList().stream()
                .anyMatch(follow -> follow.getFollowing().getUserEmail().equals(currentUserEmail));
        boardDto.setFollowedByCurrentUser(followedByCurrentUser);

        return boardDto;
    }

}
