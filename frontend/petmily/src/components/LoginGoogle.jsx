import React from 'react';
import axios from 'axios';
import { useGoogleLogin } from '@react-oauth/google';

function GoogleLoginPage() {
  const responseGoogle = async response => {
    console.log(response.access_token);
    try {
      const res = await axios.post(
        'login/google',
        {},
        {
          headers: {
            Authorization: `Bearer${response.access_token}`,
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
    <div>
      <div role="presentation" onClick={() => googleLoginButton()}>
        구글
      </div>
    </div>
  );
}

export default GoogleLoginPage;
