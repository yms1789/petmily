import kakaoLoginButton from 'static/images/kakaoLoginButton.png';

function LoginKakao() {
  const CLIENT_ID = `${process.env.REACT_APP_KAKAO_REST_API_KEY}`;
  const REDIRECT_URI = `${process.env.REACT_APP_KAKAO_REDIRECT_URL}`;
  const kakaoURL = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const handleKakaoLogin = () => {
    window.location.href = kakaoURL;
  };

  return (
    <button
      type="button"
      aria-label="카카오 로그인"
      className="cursor-pointer bg-transparent h-[4rem] w-[4rem] flex justify-center items-center p-0"
      onClick={handleKakaoLogin}
    >
      <img
        alt="카카오 로그인"
        src={kakaoLoginButton}
        className="h-full w-full p-0 m-0 bg-kakao border-[1px] border-solid border-[#f7de27] rounded-full"
      />
    </button>
  );
}

export default LoginKakao;
