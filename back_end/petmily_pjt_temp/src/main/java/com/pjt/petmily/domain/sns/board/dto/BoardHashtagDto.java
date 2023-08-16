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
public class BoardHashtagDto {
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


    public static BoardHashtagDto fromBoardEntity(Board board){
        BoardHashtagDto boardHashtagDto = new BoardHashtagDto();
        boardHashtagDto.setBoardId(board.getBoardId());
        boardHashtagDto.setBoardContent(board.getBoardContent());
        boardHashtagDto.setBoardUploadTime(board.getBoardUploadTime());
        boardHashtagDto.setHeartCount(board.getHeartCount());
        boardHashtagDto.setUserEmail(board.getUser().getUserEmail());
        boardHashtagDto.setUserNickname(board.getUser().getUserNickname());
        boardHashtagDto.setUserProfileImageUrl(board.getUser().getUserProfileImg());

        List<CommentDto> commentList = board.getCommentList()
                .stream()
                .map(CommentDto::fromCommentEntity)
                .collect(Collectors.toList());
        boardHashtagDto.setComments(commentList);

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardHashtagDto.setPhotoUrls(photoUrls);

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName)
                .collect(Collectors.toList());
        boardHashtagDto.setHashTags(hashTags);

        return boardHashtagDto;
    }
}
