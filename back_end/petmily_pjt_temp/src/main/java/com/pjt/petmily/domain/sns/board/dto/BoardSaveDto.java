package com.pjt.petmily.domain.sns.board.dto;

import com.pjt.petmily.domain.sns.comment.dto.CommentDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class BoardSaveDto {
    private Long boardId;
    private String boardContent;
    private LocalDateTime boardUploadTime;
    private int heartCount;
    private String userEmail;
    private String userNickname;
    private String userProfileImageUrl;
    private String userRing;
    private List<String> photoUrls;
    private List<String> hashTags;
    private List<CommentDto> comments;
    private boolean likedByCurrentUser;
    private boolean followedByCurrentUser;
}

