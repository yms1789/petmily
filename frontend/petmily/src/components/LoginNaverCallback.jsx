import { useEffect } from 'react';
// import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function LoginNaverCallback() {
  // const navigate = useNavigate();
  const url = new URL(window.location.href).href;

  const params = new URLSearchParams(url.split('#')[1]);
  const code = params.get('access_token');
  const state = params.get('state');
  // console.log(code, state);

  useEffect(() => {
    const naver = async () => {
      try {
        const response = await axios.post(
          'login/naver',
          {},
          {
            headers: {
              Authorization: `Bearer ${code}}`,
              'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
            },
          },
        );
        console.log(code, state);
        console.log(response, response.headers.authorization);

        // navigate('/auth/naver/callback');
      } catch (error) {
        console.log(code, state);
        console.log(error);
      }
    };
    if (code) {
      naver();
    }
  });
  return <div>NaverCallback</div>;
}

export default LoginNaverCallback;
