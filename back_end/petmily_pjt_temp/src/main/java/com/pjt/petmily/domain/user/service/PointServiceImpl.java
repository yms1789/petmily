package com.pjt.petmily.domain.user.service;

import com.pjt.petmily.domain.user.entity.Point;
import com.pjt.petmily.domain.user.entity.User;
import com.pjt.petmily.domain.user.exception.UserNotFoundException;
import com.pjt.petmily.domain.user.repository.PointRepository;
import com.pjt.petmily.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PointServiceImpl implements PointService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    @Autowired
    public PointServiceImpl(UserRepository userRepository, PointRepository pointRepository) {
        this.userRepository = userRepository;
        this.pointRepository = pointRepository; // Inject PointRepository
    }

    @Override
    // 포인트 적립 차감 및 사용내역 저장
    public void updatePoint(boolean pointType, int cost, String userEmail, String pointContent) {

        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + userEmail));
        if (pointType) {
            addPoints(user, cost);
        } else {
            deductPoints(user, cost);
        }
        Point point = Point.builder()
                .pointContent(pointContent)
                .pointType(pointType)
                .pointCost(cost)
                .pointUsageDate(LocalDateTime.now())
                .user(user)
                .build();
        pointRepository.save(point);
    }

    private void addPoints(User user, int amount) {
        user.setUserPoint(user.getUserPoint() + amount);
        // 기타 포인트 적립과 관련된 로직 추가
    }

    public class InsufficientPointsException extends RuntimeException {
        public InsufficientPointsException(String message) {
            super(message);
        }
    }

    private void deductPoints(User user, int amount) {
        if (user.getUserPoint() < amount) {
            throw new InsufficientPointsException("포인트 부족");
        }
        user.setUserPoint(user.getUserPoint() - amount);
        // 기타 포인트 차감과 관련된 로직 추가
    }

    @Override
    public List<Point> usagePointData(String userEmail) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));
        List<Point> userUsageData = pointRepository.findByUser_UserId(user.getUserId());
        return userUsageData;
    }

}
