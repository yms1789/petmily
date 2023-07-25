// 네이버 로그인 JDK 사용로직
import React, { useEffect } from 'react';

const { naver } = window;

function LoginNaver() {
  const initializeNaverLogin = () => {
    const naverLogin = new naver.LoginWithNaverId({
      clientId: `${process.env.REACT_APP_NAVER_CLIENT_ID}`,
      callbackUrl: `${process.env.REACT_APP_NAVER_CALLBACK_URL}`,
      clientSecret: `${process.env.REACT_APP_NAVER_STATE_STRING}`,
      isPopup: true,
      loginButton: { color: 'green', type: 1, height: '60' },
    });
    naverLogin.init();
  };

  useEffect(() => {
    initializeNaverLogin();
  }, []);

  return <div id="naverIdLogin" />;
}

export default LoginNaver;
