// import { useEffect } from "react";
// import axios from "axios"
// import { BACKEND_URL } from '../utils/utils';
import kakaoLoginButton from '../static/images/kakaoLoginButton.png';

function KakaoLogin() {
  const CLIENT_ID = `${process.env.REACT_APP_KAKAO_REST_API_KEY}`;
  const REDIRECT_URI = `${process.env.REACT_APP_KAKAO_REDIRECT_URL}`;
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const handleKakaoLogin = () => {
    window.open(kakaoURL, '_blank', 'width=500,height=500');
  };

  return (
    <button
      type="button"
      aria-label="카카오 로그인"
      className="bg-transparent flex justify-center items-center p-0"
      onClick={handleKakaoLogin}
    >
      <img
        alt="카카오 로그인"
        src={kakaoLoginButton}
        className="h-[38.4px] w-[38.4px] border-[1px] border-solid border-[#dadce0] rounded-full"
      />
    </button>
  );
}

export default KakaoLogin;
