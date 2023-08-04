package com.pjt.petmily.domain.sns.heart;

public class HeartException {

    public static class HeartNotFoundException extends RuntimeException {
        public HeartNotFoundException(String message) {
            super(message);
        }
    }
}
