package com.pjt.petmily.domain.sns.heart.exception;

public class HeartException {

    public static class HeartNotFoundException extends RuntimeException {
        public HeartNotFoundException(String message) {
            super(message);
        }
    }
}
