package com.pjt.petmily.domain.user.follow;

import org.springframework.data.jpa.repository.JpaRepository;


import com.pjt.petmily.domain.user.User;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
}


