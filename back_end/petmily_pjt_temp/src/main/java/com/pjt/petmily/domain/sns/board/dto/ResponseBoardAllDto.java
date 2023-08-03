package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.photo.Photo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ResponseBoardAllDto {
    private Long boardId;
    private String boardContent;
    private LocalDateTime boardUploadTime;
    private String userEmail;
    private List<String> photoUrls;

    // Remove this as it is not necessary.
    // private Board board;

    public static ResponseBoardAllDto toBoardDto(Board board) {
        ResponseBoardAllDto boardDto = new ResponseBoardAllDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardUploadTime(board.getBoardUploadTime());
        boardDto.setUserEmail(board.getUser().getUserEmail());

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardDto.setPhotoUrls(photoUrls);

        return boardDto;
    }
}