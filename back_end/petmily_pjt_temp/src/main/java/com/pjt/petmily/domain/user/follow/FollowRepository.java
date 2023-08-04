package com.pjt.petmily.domain.user.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
    int countByFollower_IdAndFollowing_Id(int followerId, int followingId);

    @Modifying
    @Transactional
    void deleteByFollowing_IdAndFollower_Id(int followingId, int followerId);
}
