package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardLikedDto {
    private Long boardId;
    private String boardContent;
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userProfileImageUrl;

    public static BoardLikedDto fromBoardEntity(Board board){
        BoardLikedDto boardLikedDto = new BoardLikedDto();
        boardLikedDto.setBoardId(board.getBoardId());
        boardLikedDto.setBoardContent(board.getBoardContent());
        boardLikedDto.setBoardUploadTime(board.getBoardUploadTime());
        boardLikedDto.setHeartCount(board.getHeartCount());
        boardLikedDto.setUserEmail(board.getUser().getUserEmail());
        boardLikedDto.setUserProfileImageUrl(board.getUser().getUserProfileImg());
        return boardLikedDto;
    }
}
