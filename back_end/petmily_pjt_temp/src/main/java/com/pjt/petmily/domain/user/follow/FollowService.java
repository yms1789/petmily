package com.pjt.petmily.domain.user.follow;

import com.pjt.petmily.domain.user.repository.UserRepository;
import com.pjt.petmily.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    public void save(Long login_id, Long page_id) { // 팔로우
        Follow f = new Follow();

//        f.setFollowing(userRepository.findByUserEmail(userEmail));
//        f.setFollower(userRepository.findByUserEmail(page_id));

        followRepository.save(f);
    }

    public void delete(Long followingId, Long followerId) { // 언팔로우
        followRepository.deleteByFollowing_IdAndFollower_Id(followingId, followerId);
    }

    public boolean isFollowing(Long followerId, Long followingId) { // 팔로우가 되어있는지 확인
        return followRepository.countByFollower_IdAndFollowing_Id(followerId, followingId) > 0;
    }
}

