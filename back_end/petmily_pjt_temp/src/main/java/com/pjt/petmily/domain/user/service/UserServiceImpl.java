package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.User;
import com.pjt.petmily.domain.user.dto.*;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.global.awss3.service.S3Uploader;
import com.pjt.petmily.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final com.pjt.petmily.global.awss3.service.S3Uploader s3Uploader;

    // 중복 이메일 확인


    // 회원가입완료 (DB 저장)
    @Override
    public User signUp(UserSignUpDto userSignUpDto) {

        User user = User.builder()
                .userEmail(userSignUpDto.getUserEmail())
                .userPw(bCryptPasswordEncoder.encode(userSignUpDto.getUserPw()))
                .userPoint(0L)
                .build();
        userRepository.save(user);

        return user;
    }

    @Autowired
    public UserServiceImpl(UserRepository repository, S3Uploader s3Uploader) {
        this.userRepository = repository;
        this.s3Uploader = s3Uploader;
    }

    // 닉네임 중복 확인
    @Override
    public boolean checkNicknameExists(String userNickname) {
        return userRepository.findByUserNickname(userNickname).isPresent();
    }


//    @Override
//    public User loginUser(UserLoginDto userLoginDto) {
//        String userEmail = userLoginDto.getUserEmail();
//        String userPw = userLoginDto.getUserPw();
//        Optional<User> existEmail = userRepository.findByUserEmail(userEmail);
//        if (existEmail.isPresent()) {
//            User user = existEmail.get();
//            if (bCryptPasswordEncoder.matches(userPw, existEmail.get().getUserPw())) {
//                String refreshToken = JwtService.createRefreshToken(userEmail);
////                existEmail.get().updateUserToken(refreshToken);
//                user.updateUserToken(refreshToken);
//                userRepository.save(user);
//                return user;
//
//            }
//        }
//        return null;
//    }

    @Override
    public ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto) {
        String userEmail = userLoginDto.getUserEmail();
        String userPw = userLoginDto.getUserPw();
        Optional<User> existEmail = userRepository.findByUserEmail(userEmail);
        if (existEmail.isPresent()) {
            User user = existEmail.get();
            if (bCryptPasswordEncoder.matches(userPw, existEmail.get().getUserPw())) {
                String refreshToken = JwtService.createRefreshToken(userEmail);
                String accessToken = JwtService.createAccessToken(userEmail);
                user.updateUserToken(refreshToken);
                userRepository.save(user);

                LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, user);
                return ResponseDto.setSucess("로그인성공", loginResponseDto);

            }
        }
        return ResponseDto.setFailed("이메일이 존재하지 않거나 비밀번호가 틀림");

    }

    @Override
    @Transactional
    public String updateUserInfo(UserInfoEditDto userInfoEditDto) {
        User user = userRepository.findByUserEmail(userInfoEditDto.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        String userNickname = userInfoEditDto.getUserNickname();
        String userLikePet = userInfoEditDto.getUserLikePet();

        System.out.println(userNickname +" " + userLikePet);
        user.updateUserInfo(userNickname, userLikePet);
        return userNickname;
    }

    @Override
    @Transactional
    public Optional<String> updateUserImg(String userEmail, MultipartFile file) throws Exception {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        System.out.println("사진 업로드?");
        Optional<String> userProfileImg = file == null?  Optional.empty() : s3Uploader.uploadFile(file, "profile");
        if(userProfileImg.isPresent()) {
            user.updateUserImg(userProfileImg.get());
        }
        return userProfileImg;
    }

    // 이메일 입력받으면 비밀번호 변경
    @Override
    public ResponseDto<String> changePassword(String userEmail, String newPw) {
        Optional<User> findUser = userRepository.findByUserEmail(userEmail);
        User user = findUser.get();
        user.changeUserPw(bCryptPasswordEncoder.encode(newPw));
        userRepository.save(user);
        return ResponseDto.setSucess("비밀번호 변경 선공",newPw);
    }

    // 사용자의 비밀번호가 일치하는지 체크
    @Override
    public boolean passwordCheck(String userEmail, String old_password) {
        // DB에서 이메일에 해당하는 비밀번호 찾기
        Optional<User> existEmail = userRepository.findByUserEmail(userEmail);
        // old_password 일치여부확인
        boolean checkPw = bCryptPasswordEncoder.matches(old_password, existEmail.get().getUserPw());
        return checkPw;
    }

    @Override
    public ResponseDto<String> deleteUser(String userEmail) {
        Optional<User> findUser = userRepository.findByUserEmail(userEmail);
        User user = findUser.get();
        userRepository.delete(user);
        return ResponseDto.setSucess("유저정보삭제완료",null);
    }


    @Override
    public boolean attendance(UserSignUpEmailDto userEmailDto) {
        User user = userRepository.findByUserEmail(userEmailDto.getUserEmail()).get();
        LocalDate attendanceData= user.getUserAttendance();
        if (attendanceData == null || !attendanceData.equals(LocalDate.now())) {
            return true;
        } else {
            user.setUserAttendance(LocalDate.now());
            return false;
        }
    }

}
