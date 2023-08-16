package com.pjt.petmily.domain.sns.comment.exception;

public class CommentException {
    public static class CommentNotFoundException extends RuntimeException {
        public CommentNotFoundException(String message) {
            super(message);
        }
    }

    public class CommentSaveFailedException extends RuntimeException {
        public CommentSaveFailedException(String message) {
            super(message);
        }
    }

}