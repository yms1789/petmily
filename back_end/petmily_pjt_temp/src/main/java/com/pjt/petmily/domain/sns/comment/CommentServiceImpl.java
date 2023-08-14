package com.pjt.petmily.domain.sns.comment;

import com.pjt.petmily.domain.noti.entity.Noti;
import com.pjt.petmily.domain.noti.entity.NotiType;
import com.pjt.petmily.domain.noti.repository.NotiRepository;
import com.pjt.petmily.domain.sns.board.Board;
import com.pjt.petmily.domain.sns.board.BoardException;
import com.pjt.petmily.domain.sns.board.BoardRepository;
import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.exception.UserNotFoundException;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private NotiRepository notiRepository;

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException.CommentNotFoundException("Comment not found with id " + commentId));
    }

    @Transactional
    public Comment createComment(Long boardId, String userEmail, String commentContent, Long parentId) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + userEmail));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException.BoardNotFoundException("Board not found with id " + boardId));

        Comment comment = Comment.builder()
                .commentContent(commentContent)
                .user(user)
                .board(board)
                .commentTime(LocalDateTime.now())
                .build();

        if (parentId != null) {
            Comment parent = commentRepository.findByCommentId(parentId)
                    .orElseThrow(() -> new CommentException.CommentNotFoundException("Parent comment not found with id " + parentId));
            comment.setParent(parent);
        }
        Comment savedComment = commentRepository.save(comment);

        Noti noti = Noti.builder()
                .notiType(NotiType.COMMENT)
                .fromUser(user)
                .toUser(board.getUser())
                .createDate(LocalDateTime.now())
                .isChecked(false)
                .build();

        notiRepository.save(noti);

        return savedComment;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException.CommentNotFoundException("Comment not found with id " + commentId));
        commentRepository.delete(comment);
    }
}