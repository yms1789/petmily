package com.pjt.petmily.domain.social;

import java.util.Map;

public interface SocialLoginService {

    Map<String, Object> getGoogleUserInfo(String accessToken);
}
