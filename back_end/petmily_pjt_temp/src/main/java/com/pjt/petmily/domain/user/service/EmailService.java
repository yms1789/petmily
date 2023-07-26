package com.pjt.petmily.domain.user.service;

public interface EmailService {

    // 이메일 중복 확인
    boolean checkEmailExists(String userEmail);

    String sendSimpleMessage(String to)throws Exception;

    public String getVerificationCode(String email);
}
