package com.pjt.petmily.domain.pet;

public class PetException {
    public static class PetNotFoundException extends RuntimeException {
        public PetNotFoundException(String message) {
            super(message);
        }
    }

    public static class PetDeletionException extends RuntimeException {
        public PetDeletionException(String message) {
            super(message);
        }
    }

}
