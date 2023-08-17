package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.shop.entity.Item;
import com.pjt.petmily.domain.shop.repository.ItemRepository;
import com.pjt.petmily.domain.user.entity.StaticImg;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.dto.*;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.domain.user.repository.StaticImgRepository;
import com.pjt.petmily.global.awss3.service.S3Uploader;
import com.pjt.petmily.global.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private StaticImgRepository staticImgRepository;
    private final com.pjt.petmily.global.awss3.service.S3Uploader s3Uploader;
    @Autowired
    private ItemRepository itemRepository;

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



    @Override
    public ResponseDto<LoginResponseDto> loginUser(UserLoginDto userLoginDto) {
        String userEmail = userLoginDto.getUserEmail();
        String userPw = userLoginDto.getUserPw();
        try {
            Optional<User> existEmail = userRepository.findByUserEmail(userEmail);

            if (existEmail.isPresent()) {
                User user = existEmail.get();

                if (bCryptPasswordEncoder.matches(userPw, user.getUserPw())) {
                    String refreshToken = JwtService.createRefreshToken(userEmail);
                    String accessToken = JwtService.createAccessToken(userEmail);
                    user.updateUserToken(refreshToken);
                    userRepository.save(user);

                    Item ERing = itemRepository.findByItemId(user.getUserRing());
                    Item EBackground = itemRepository.findByItemId(user.getUserBackground());
                    Item EBadge = itemRepository.findByItemId(user.getUserBadge());

                    UserLoginInfoDto userLoginInfoDto = new UserLoginInfoDto();
                    userLoginInfoDto.setUserEmail(user.getUserEmail());
                    userLoginInfoDto.setUserToken(user.getUserToken());
                    userLoginInfoDto.setUserNickname(user.getUserNickname());
                    userLoginInfoDto.setUserProfileImg(user.getUserProfileImg());
                    userLoginInfoDto.setUserLikePet(user.getUserLikePet());
                    userLoginInfoDto.setUserBadge(EBadge);
                    userLoginInfoDto.setUserRing(ERing);
                    userLoginInfoDto.setUserBackground(EBackground);
                    userLoginInfoDto.setUserLoginDate(user.getUserLoginDate());
                    userLoginInfoDto.setUserIsSocial(user.getUserIsSocial());
                    userLoginInfoDto.setUserAttendance(user.getUserAttendance());

                    LoginResponseDto loginResponseDto = new LoginResponseDto(accessToken, userLoginInfoDto);
                    return ResponseDto.setSucess("로그인성공", loginResponseDto);
                }
            }
            return ResponseDto.setFailed("이메일이 존재하지 않거나 비밀번호가 틀림");
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return ResponseDto.setFailed("로그인 중 에러 발생");
        }
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

//    @Override
//    @Transactional
//    public Optional<String> updateUserImg(String userEmail, MultipartFile file) throws Exception {
//        User user = userRepository.findByUserEmail(userEmail)
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
//
//        Optional<String> userProfileImg = file == null? Optional.empty() : s3Uploader.uploadFile(file, "profile");
//
//        if (userProfileImg.isPresent()) {
//            String profileImg = userProfileImg.get();
//            user.updateUserImg(profileImg);
//            userRepository.save(user);
////        } else {
////            List<StaticImg> allImages = staticImgRepository.findAll();
////            if (!allImages.isEmpty()) {
////                int randomIndex = new Random().nextInt(allImages.size());
////                StaticImg randomStaticImg = allImages.get(randomIndex);
////                user.updateUserImg(randomStaticImg.getDefaultImgUrl());
////            }
//        }
//        return userProfileImg;
//    }

    @Override
    @Transactional
    public Optional<String> updateUserImg(String userEmail, MultipartFile file) throws Exception {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        System.out.println("사진 업로드?");
        Optional<String> userProfileImg = file == null?  Optional.empty() : s3Uploader.uploadFile(file, "profile");
        if(userProfileImg.isPresent()) {
            String profileImg = userProfileImg.get();
            user.updateUserImg(profileImg);
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
        String userEmail = userEmailDto.getUserEmail();
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            LocalDateTime attendanceData= user.getUserAttendance();
            if (attendanceData == null) {
                user.setUserAttendance(LocalDateTime.now());
                userRepository.save(user);
                return true;
            } else {
//                LocalDate compareDate = attendanceData.plus(9, ChronoUnit.HOURS).toLocalDate();
                LocalDate compareDate = attendanceData.toLocalDate();
                if (!compareDate.equals(LocalDate.now())) {
                    user.setUserAttendance(LocalDateTime.now());
                    userRepository.save(user);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

}
