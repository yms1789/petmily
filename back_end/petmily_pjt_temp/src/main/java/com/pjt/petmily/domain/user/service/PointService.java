package com.pjt.petmily.domain.user.service;


import com.pjt.petmily.domain.user.entity.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PointService {

    // 포인트 사용 로직
    void updatePoint(boolean pointType, int cost, String userEmail, String pointContent);

    // 포인트 사용내역 조회
    List<Point> usagePointData(String userEmail);

}
