package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.hashtag.HashTagRequestDto;
import com.pjt.petmily.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class  BoardSaveDto {
    private BoardRequestDto boardRequestDto;
    private HashTagRequestDto hashTagRequestDto;
    private List<MultipartFile> boardImgFiles;

    public BoardSaveDto(BoardRequestDto boardRequestDto,
                             HashTagRequestDto hashTagRequestDto,
                             List<MultipartFile> boardImgFiles) {
        this.boardRequestDto = boardRequestDto;
        this.hashTagRequestDto = hashTagRequestDto;
        this.boardImgFiles = boardImgFiles;
    }
}
