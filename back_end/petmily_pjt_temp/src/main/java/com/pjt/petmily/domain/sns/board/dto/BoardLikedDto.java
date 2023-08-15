package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.hashtag.HashTag;
import com.pjt.petmily.domain.sns.board.photo.Photo;
import com.pjt.petmily.domain.sns.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardLikedDto {
    private Long boardId;
    private String boardContent;
    private List<String> photoUrls;
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userNickname;
    private String userProfileImageUrl;
    private List<CommentDto> commentList;
    private List<String> hashTags;
    private boolean likedByCurrentUser;
    private boolean followedByCurrentUser;

    public static BoardLikedDto fromBoardEntity(Board board){
        BoardLikedDto boardLikedDto = new BoardLikedDto();
        boardLikedDto.setBoardId(board.getBoardId());
        boardLikedDto.setBoardContent(board.getBoardContent());
        boardLikedDto.setBoardUploadTime(board.getBoardUploadTime());
        boardLikedDto.setHeartCount(board.getHeartCount());
        boardLikedDto.setUserEmail(board.getUser().getUserEmail());
        boardLikedDto.setUserNickname(board.getUser().getUserNickname());
        boardLikedDto.setUserProfileImageUrl(board.getUser().getUserProfileImg());

        List<CommentDto> commentList = board.getCommentList()
                .stream()
                .map(CommentDto::fromCommentEntity)
                .collect(Collectors.toList());
        boardLikedDto.setCommentList(commentList);

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardLikedDto.setPhotoUrls(photoUrls);

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
        boardLikedDto.setHashTags(hashTags);

        boolean likedByCurrentUser = board.getHeartList().stream()
                .anyMatch(heart -> heart.getUser().getUserEmail().equals(board.getUser().getUserEmail()));
        boardLikedDto.setLikedByCurrentUser(likedByCurrentUser);

        boolean followedByCurrentUser = board.getUser().getFollowingList().stream()
                .anyMatch(follow -> follow.getFollowing().getUserEmail().equals(board.getUser().getUserEmail()));
        boardLikedDto.setFollowedByCurrentUser(followedByCurrentUser);


        return boardLikedDto;
    }
}
