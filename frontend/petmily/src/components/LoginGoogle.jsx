import { useGoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import googleLoginButtonImage from 'static/images/googleLoginButton.png';

function LoginGoogle() {
  const responseGoogle = async response => {
    console.log(response.access_token);
    try {
      const res = await axios.post(
        'login/google',
        {},
        {
          headers: {
            Authorization: `Bearer ${response.access_token}`,
            'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
          },
        },
      );
      console.log(res);
    } catch (error) {
      console.log(error);
    }
  };

  const googleLoginButton = useGoogleLogin({
    onSuccess: responseGoogle,
    onFailure: () => console.log('onFailure'),
  });

  return (
    <div
      role="presentation"
      className="flex"
      onClick={() => googleLoginButton()}
    >
      <img
        src={googleLoginButtonImage}
        alt="구글로그인버튼"
        className="h-[4rem] w-[4rem] border-[1px] bg-white border-solid border-[#dadce0] rounded-full"
      />
    </div>
  );
}

export default LoginGoogle;
