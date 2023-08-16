package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.entity.Board;
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
public class BoardWriteDto {
    private Long boardId;
    private String boardContent;
    private List<String> photoUrls;
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userNickname;
    private String userProfileImageUrl;
    private List<CommentDto> comments;
    private List<String> hashTags;
    private boolean likedByCurrentUser;
    private boolean followedByCurrentUser;


    public static BoardWriteDto fromBoardEntity(Board board){
        BoardWriteDto boardWriteDto = new BoardWriteDto();
        boardWriteDto.setBoardId(board.getBoardId());
        boardWriteDto.setBoardContent(board.getBoardContent());
        boardWriteDto.setBoardUploadTime(board.getBoardUploadTime());
        boardWriteDto.setHeartCount(board.getHeartCount());
        boardWriteDto.setUserEmail(board.getUser().getUserEmail());
        boardWriteDto.setUserNickname(board.getUser().getUserNickname());
        boardWriteDto.setUserProfileImageUrl(board.getUser().getUserProfileImg());

        List<CommentDto> commentList = board.getCommentList()
                .stream()
                .map(CommentDto::fromCommentEntity)
                .collect(Collectors.toList());
        boardWriteDto.setComments(commentList);

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardWriteDto.setPhotoUrls(photoUrls);

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
        boardWriteDto.setHashTags(hashTags);

        boolean likedByCurrentUser = board.getHeartList().stream()
                .anyMatch(heart -> heart.getUser().getUserEmail().equals(board.getUser().getUserEmail()));
        boardWriteDto.setLikedByCurrentUser(likedByCurrentUser);

        boolean followedByCurrentUser = board.getUser().getFollowingList().stream()
                .anyMatch(follow -> follow.getFollowing().getUserEmail().equals(board.getUser().getUserEmail()));
        boardWriteDto.setFollowedByCurrentUser(followedByCurrentUser);

        return boardWriteDto;
    }
}
