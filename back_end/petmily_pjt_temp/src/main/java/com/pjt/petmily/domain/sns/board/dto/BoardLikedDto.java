package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
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
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userNickname;
    private String userProfileImageUrl;
    private List<CommentDto> commentList;

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
        return boardLikedDto;
    }
}