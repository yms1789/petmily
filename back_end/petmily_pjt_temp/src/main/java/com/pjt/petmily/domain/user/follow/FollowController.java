package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.follow.dto.FollowUserDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {


    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private FollowService followService;

    @PostMapping("/follow/{userId}")
    @Operation(summary = "팔로우", description = "해당 유저 팔로우")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @RequestBody FollowUserDto followUserDto) {
        try {
            String message = followService.followUser(userId, followUserDto);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/follow/{userId}")
    @Operation(summary = "언팔로우", description = "해당 유저 언팔로우")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, @RequestBody FollowUserDto followUserDto) {
        try {
            String message = followService.unfollowUser(userId, followUserDto);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



}
