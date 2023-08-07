package com.pjt.petmily.domain.sns.board;

public class BoardException {
    public static class BoardNotFoundException extends RuntimeException {
        public BoardNotFoundException(String message) {
            super(message);
        }
    }

    public static class BoardDeletionException extends RuntimeException {
        public BoardDeletionException(String message) {
            super(message);
        }
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }
}
