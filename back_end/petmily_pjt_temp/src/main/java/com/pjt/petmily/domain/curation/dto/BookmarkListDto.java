package com.pjt.petmily.domain.curation.dto;

import lombok.Data;

@Data
public class BookmarkListDto {
    private Long bookmarkId;

    public BookmarkListDto(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
}