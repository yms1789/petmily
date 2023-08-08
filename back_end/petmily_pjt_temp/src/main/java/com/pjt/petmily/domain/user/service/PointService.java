package com.pjt.petmily.domain.user.service;


import com.pjt.petmily.domain.user.Point;
import com.pjt.petmily.domain.user.dto.PointDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PointService {

    void updatePoint(boolean pointType, int cost, String userEmail, String pointContent);

    List<Point> usagePointData(String userEmail);

}
