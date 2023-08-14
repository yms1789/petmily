package com.pjt.petmily.domain.sns.board.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedResponseBoardDto {
    private List<ResponseBoardAllDto> boards;
    private boolean isLast;

    public PagedResponseBoardDto(List<ResponseBoardAllDto> boards, boolean isLast) {
        this.boards = boards;
        this.isLast = isLast;
    }
}
