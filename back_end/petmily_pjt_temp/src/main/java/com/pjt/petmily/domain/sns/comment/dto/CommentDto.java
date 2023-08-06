package com.pjt.petmily.domain.sns.comment.dto;

import com.pjt.petmily.domain.sns.comment.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentDto {
    private Long commentId;
    private String commentContent;
    private LocalDateTime commentTime;
    private String userEmail;
    private Long boardId;
    private Long parentId;
    private List<CommentDto> replies;

    public static CommentDto fromCommentEntity(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setCommentContent(comment.getCommentContent());
        commentDto.setCommentTime(comment.getCommentTime());
        commentDto.setUserEmail(comment.getUser().getUserEmail());
        commentDto.setBoardId(comment.getBoard().getBoardId());

        if (comment.getParent() != null) {
            commentDto.setParentId(comment.getParent().getCommentId());
        }

        if (!comment.getReplies().isEmpty()) {
            List<CommentDto> replies = comment.getReplies().stream()
                    .map(CommentDto::fromCommentEntity)
                    .collect(Collectors.toList());
            commentDto.setReplies(replies);
        }

        return commentDto;
    }
}
