package com.pjt.petmily.domain.user.controller;

import com.pjt.petmily.domain.sns.board.dto.BoardLikedDto;
import com.pjt.petmily.domain.sns.board.dto.BoardWriteDto;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.dto.*;
import com.pjt.petmily.domain.user.dto.UserLoginDto;
import com.pjt.petmily.domain.user.follow.dto.FollowerDto;
import com.pjt.petmily.domain.user.follow.dto.FollowingDto;
import com.pjt.petmily.domain.user.entity.Point;
import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.domain.user.service.EmailService;
import com.pjt.petmily.domain.user.service.PointService;
import com.pjt.petmily.domain.user.service.UserService;
import com.pjt.petmily.global.awss3.service.S3Uploader;
import com.pjt.petmily.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;
    private final EmailService emailService;
    private final S3Uploader s3Uploader;
    private final PointService pointService;

    // 이메일 인증 번호 전송
    @PostMapping("/signup/email")
    @Operation(summary = "회원가입시 이메일 확인", description = "회원 가입 시 이메일 중복 확인 및 이메일 인증 코드 발송")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이메일 인증 코드 발송 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "이미 존재하는 이메일"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    })
    public ResponseEntity<String> emailConfirm(@RequestBody UserSignUpEmailDto userSignUpEmailDto) throws Exception {
        boolean emailExists = emailService.checkEmailExists(userSignUpEmailDto.getUserEmail());

        // 이메일 중복 확인
        if (emailExists) {
            return new ResponseEntity<>("이미 존재하는 이메일입니다", HttpStatus.UNAUTHORIZED);
        } else {
            String confirm = emailService.sendSimpleMessage(userSignUpEmailDto.getUserEmail());

            return new ResponseEntity<>(confirm, HttpStatus.OK);
        }
    }

    // 이메일 인증 코드 확인(회원가입, 비밀번호초기화)
    @PostMapping("/email/verification")
    @Operation(summary = "이메일 인증 코드 확인(회원가입, 비밀번호초기화)", description = "회원 가입 및 비밀번호 초기화 시 이메일 인증 코드 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 코드 일치"),
            @ApiResponse(responseCode = "401", description = "인증 코드 불일치"),
            @ApiResponse(responseCode = "500", description = "서버 오류"),
    })
    public ResponseEntity<String> verifyCode(@RequestBody UserEmailVerifyDto userEmailVerifyDto) {
        String ePw = emailService.getVerificationCode(userEmailVerifyDto.getUserEmail());
        System.out.println("code : " + userEmailVerifyDto.getCode());
        System.out.println("code match : " + ePw.equals(userEmailVerifyDto.getCode()));

        if (ePw.equals(userEmailVerifyDto.getCode())) {
            return new ResponseEntity<>(HttpStatus.OK); // 인증 코드 일치
        } else {
            return new ResponseEntity<>("인증 코드가 일치하지 않습니다", HttpStatus.UNAUTHORIZED); // 인증 코드 불일치
        }
    }

    // 회원가입
    @PostMapping("/signup")
    @Operation(summary = "회원 가입", description = "회원 가입을 위한 메소드")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "500", description= "서버 오류")
    })
    public String signUp(@RequestBody UserSignUpDto userSignUpDto) throws Exception {
        userService.signUp(userSignUpDto);
        return "회원가입 성공";
    }


    // 로그인(accessToken만 줌)
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
//        User authenticatedUser = userService.loginUser(userLoginDto);
//        if (authenticatedUser != null) {
//            String accessToken = JwtService.createAccessToken(authenticatedUser.getUserEmail());
//            return ResponseEntity.ok(accessToken);
//        } else {
//            return new ResponseEntity<>("로그인실패", HttpStatus.UNAUTHORIZED);
//        }
//    }

    // 로그인(정보 다줌)
    @PostMapping("/login")
    @Operation(summary = "로그인")
    public ResponseDto<LoginResponseDto> login(@RequestBody UserLoginDto userLoginDto) {
        ResponseDto<LoginResponseDto> result = userService.loginUser(userLoginDto);
        return result;
    }

    // 로그아웃
    @PostMapping("/logout")
    public boolean logout() {
        return true;
    }

    // 비밀번호 초기화 - 인증코드 발송
    @PostMapping("/resetpassword/email")
    @Operation(summary = "이메일 확인 인증코드 발송", description = "body로 이메일 요청, 인증코드 응답")
    public ResponseEntity<String> emailCheck(@RequestBody UserSignUpEmailDto userSignUpEmailDto) throws Exception {
        // 이메일 중복 확인
        boolean emailExists = emailService.checkEmailExists(userSignUpEmailDto.getUserEmail());
        // 이메일 유무확인
        if (emailExists) {
            String confirm = emailService.sendSimpleMessage(userSignUpEmailDto.getUserEmail());
            return new ResponseEntity<>(confirm, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("존재하지 않는 이메일입니다", HttpStatus.UNAUTHORIZED);
        }
    }

    // 닉네임 중복 확인
    @PostMapping("/nickname/check")
    @Operation(summary = "닉네임 중복 확인", description = "회원 정보 입력시 닉네임 중복 여부 확인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 중복 안됨"),
            @ApiResponse(responseCode = "401", description = "닉네임 중복"),
    })
    public ResponseEntity<Void> checkNickname(@RequestBody UserNicknameDto userNicknameDto) {
        boolean nickNameExists = emailService.checkNicknameExists(userNicknameDto.getUserNickname());

        // 닉네임 중복 확인
        if (nickNameExists) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping("/mypage/edit")
    @Operation(summary = "유저 정보 초기 입력 및 수정", description = "유저 정보 초기 입력 및 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "정보 저장 완료"),
            @ApiResponse(responseCode = "401", description = "정보 저장 실패"),
    })
    public ResponseEntity<UserInfoEditResponseDto> updateUserInfo(@RequestPart UserInfoEditDto userInfoEditDto,
                                                 @RequestPart(value = "file", required=false) MultipartFile file
    ) {
        String userEmail = userInfoEditDto.getUserEmail();

        try {
            String userProfileImg = userService.updateUserImg(userEmail, file).get();

            userService.updateUserInfo(userInfoEditDto);
            UserInfoEditResponseDto response = new UserInfoEditResponseDto(userInfoEditDto, userProfileImg);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/profile/{userEmail}")
    @Operation(summary = "프로필 페이지", description = "마이페이지 및 유저정보 페이지")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String userEmail){
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        return new ResponseEntity<>(UserProfileDto.fromUserEntity(user), HttpStatus.OK);
    }

    @GetMapping("/profile/{userEmail}/follower")
    @Operation(summary = "팔로워 조회", description = "해당 유저 팔로워 조회")
    public ResponseEntity<List<FollowerDto>> getFollowers(@PathVariable String userEmail,
                                                          @RequestParam String currentUser) {
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<FollowerDto> followers = user.getFollowerList().stream()
                    .map(follow -> FollowerDto.fromFollowEntity(follow, currentUser))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(followers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/profile/{userEmail}/following")
    @Operation(summary = "팔로잉 조회", description = "해당 유저 팔로잉 조회")
    public ResponseEntity<List<FollowingDto>> getFollowingUsers(@PathVariable String userEmail,
                                                                @RequestParam String currentUser) {
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            List<FollowingDto> followingUsers = user.getFollowingList().stream()
                    .map(follow -> FollowingDto.fromFollowEntity(follow, currentUser))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(followingUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/profile/{userEmail}/writeboard")
    @Operation(summary = "작성한 게시글 조회", description = "해당 유저가 작성한 게시글 조회")
    public ResponseEntity<List<BoardWriteDto>> getUserWriteBoards(@PathVariable String userEmail,
                                                                  @RequestParam String currentUser){
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            List<BoardWriteDto> writeBoardList = user.getBoardList().stream()
                    .map(BoardWriteDto::fromBoardEntity)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(writeBoardList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/profile/{userEmail}/likeboard")
    @Operation(summary = "좋아요한 게시글 조회", description = "해당 유저가 좋아요한 게시글 조회")
    public ResponseEntity<List<BoardLikedDto>> getUserLikeBoards(@PathVariable String userEmail,
                                                                 @RequestParam String currentUser){
        Optional<User> userOptional = userRepository.findByUserEmail(userEmail);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            List<BoardLikedDto> likedBoardList = user.getHeartList().stream()
                    .map(heart -> BoardLikedDto.fromBoardEntity(heart.getBoard(), currentUser))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(likedBoardList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }



    // 비밀번호 초기화 - 초기화된 비밀번호 이메일로 발송
    @PutMapping("/resetpassword/reset")
    @Operation(summary = "초기화된 비밀번호 이메일로 발송")
    public ResponseDto<String> passwordReset(@RequestBody UserSignUpEmailDto userSignUpEmailDto) throws Exception {
        String sendNewPw = emailService.sendNewPasswordMessage(userSignUpEmailDto.getUserEmail());
        ResponseDto<String> result = userService.changePassword(userSignUpEmailDto.getUserEmail(), sendNewPw);
        return result;
    }

    // 비밀번호 변경
    @PutMapping("/changepassword")
    @Operation(summary = "비밀번호 변경", description = "이메일,현재비밀번호,새로운비밀번호 요청시 결과 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경성공"),
            @ApiResponse(responseCode = "401", description = "현재 비밀번호 불일치"),
    })
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) throws Exception {
        boolean passwordCheck = userService.passwordCheck(changePasswordDto.getUserEmail(), changePasswordDto.getOldPassword());
        if (passwordCheck) {
            userService.changePassword(changePasswordDto.getUserEmail(), changePasswordDto.getNewPassword());

            return new ResponseEntity<>("비밀번호가 변경완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("기존 비밀번호 불일치", HttpStatus.UNAUTHORIZED);
        }
    }

    // 회원탈퇴 passwordcheck
    @GetMapping("/signout/passwordcheck")
    @Operation(summary = "회원탈퇴 비밀번호체크", description = "일치시 true, 불일치시 false (둘다200응답)")
    public ResponseEntity<Boolean> signOutPasswordCheck(@RequestParam String userEmail,
                                                        @RequestParam String userPw) {
        boolean result = userService.passwordCheck(userEmail, userPw);
        return ResponseEntity.ok(result);

    }

    // 회원 탈퇴
    @DeleteMapping("/signout/deleteuser")
    @Operation(summary = "회원탈퇴", description = "비밀번호체크후에 이거하면 DB에서 삭제")
    public ResponseEntity<String> signOut(@RequestBody UserLoginDto userSignOutDto) {
            userService.deleteUser(userSignOutDto.getUserEmail());
            return new ResponseEntity<>("회원탈퇴 완료", HttpStatus.OK);
    }


    private final JwtService jwtService;

    //@RequestHeader("Authorization") String accessToken
    // 토큰 유효성검사
    @PostMapping("/authenticate")
    @Operation(summary = "[Test용]accessToken 유효성검사", description = "유효시 200, 만료시 401, 유효하지않을때 400")
    public ResponseEntity<String> authenticate(@RequestHeader("Authorization") String authorizationHeader) {

        String accessToken = authorizationHeader.substring(7);

        Integer isAccessTokenValid = jwtService.validateToken(accessToken);
        if (isAccessTokenValid == 1) {
            String userEmail = jwtService.extractUserEmailFromAccessToken(accessToken);

            if (userEmail != null && jwtService.isUserValid(userEmail)) {
                // 유효한 Access Token과 유효한 사용자인 경우
                // 처리 로직을 수행하고 결과를 클라이언트에 반환합니다.
                return ResponseEntity.ok("Authenticated successfully.");
            }
        }
        HttpStatus status = isAccessTokenValid==2 ? HttpStatus.UNAUTHORIZED : HttpStatus.BAD_REQUEST;
        String message = isAccessTokenValid==2 ? "Access Token 만료" : "올바르지않은 토큰";
        return ResponseEntity.status(status).body(message);
    }


    // 토큰 재발급
    @PostMapping("/refreshAccessToken")
    @Operation(summary = "accessToken재발급")
    public ResponseEntity<String> refreshAccessToken(@RequestBody TokenRequestDto tokenRequestDto) {
        String refreshToken = tokenRequestDto.getRefreshToken();
        String userEmail = tokenRequestDto.getUserEmail();
        String storedRefreshToken = jwtService.refreshtokenCheck(userEmail);
        if (refreshToken.equals(storedRefreshToken)) {
            String newAccessToken = jwtService.createAccessToken(userEmail);
            return ResponseEntity.ok(newAccessToken);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리프레시 토큰이 일치하지 않음");
        }
    }

    // 포인트 사용내역 조회
    @GetMapping("/usagePoint")
    @Operation(summary="포인트 사용내역 조회")
    public ResponseEntity<List<Point>> usagePoint(@RequestParam String userEmail) {
        List<Point> pointUsageData = pointService.usagePointData(userEmail);
        return ResponseEntity.ok(pointUsageData);
    }

    @PutMapping("/pointUpdate")
    @Operation(summary="test용 수동 포인트 적립 및 사용")
    public ResponseEntity<String> updatePoint(@RequestBody PointUsageDto pointUsageDto) {
        pointService.updatePoint(pointUsageDto.getPointType(),pointUsageDto.getPointCost(),pointUsageDto.getUserEmail(),pointUsageDto.getPointContent());
        return ResponseEntity.ok("포인트업데이트");
    }

    @PutMapping("/attendance")
    @Operation(summary="출석체크")
    public ResponseEntity<Boolean> attendance(@RequestBody UserSignUpEmailDto userEmailDto) {
        boolean result = userService.attendance(userEmailDto);
        if (result) {
            pointService.updatePoint(true,1,userEmailDto.getUserEmail(),"출석체크");
        }
        return ResponseEntity.ok(result);
    }

    // 유저 보유 포인트 조회
    @GetMapping("/userpoint")
    @Operation(summary="유저 보유 포인트 조회")
    public ResponseEntity<?> userpoint(@RequestParam String userEmail) {
        Optional<User> user = userRepository.findByUserEmail(userEmail);
        Long getUserPoint = user.get().getUserPoint();
        return ResponseEntity.ok(getUserPoint);
    }


}
