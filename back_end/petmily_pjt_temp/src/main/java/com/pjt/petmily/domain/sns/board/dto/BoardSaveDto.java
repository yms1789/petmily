package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.user.User;

import java.time.LocalDateTime;

public class  BoardSaveDto {
    private String boardContent;
    private LocalDateTime boardUploadTime;
    private User user;

    public Board toEntity(){
        return Board.builder().
                boardContent(boardContent).
                boardUploadTime(LocalDateTime.now())
                .build();

    }
}
