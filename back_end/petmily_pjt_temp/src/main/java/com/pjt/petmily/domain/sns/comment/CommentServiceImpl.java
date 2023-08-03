package com.pjt.petmily.domain.sns.comment;

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
                    .orElseThrow(() -> new CommentNotFoundException("Parent comment not found with id " + parentId));
            comment.setParent(parent);
        }

        return commentRepository.save(comment);
    }
}