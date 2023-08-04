package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.hashtag.HashTag;
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
    private List<String> hashTags;


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

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName) // Assuming HashTag has a method getTagText()
                .collect(Collectors.toList());
        boardDto.setHashTags(hashTags);


        return boardDto;
    }

    public static ResponseBoardAllDto fromBoardEntity(Board board) {
        ResponseBoardAllDto boardDto = new ResponseBoardAllDto();
        boardDto.setBoardId(board.getBoardId());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardUploadTime(board.getBoardUploadTime());
        boardDto.setUserEmail(board.getUser().getUserEmail());

        List<String> photoUrls = board.getPhotoList().stream()
                .map(Photo::getPhotoUrl)
                .collect(Collectors.toList());
        boardDto.setPhotoUrls(photoUrls);

        List<String> hashTags = board.getHashTagList().stream()
                .map(HashTag::getHashTagName) // Assuming HashTag has a method getTagText()
                .collect(Collectors.toList());
        boardDto.setHashTags(hashTags);

        return boardDto;
    }
}