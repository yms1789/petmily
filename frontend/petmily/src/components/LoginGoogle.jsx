import React from 'react';
import { GoogleOAuthProvider, GoogleLogin } from '@react-oauth/google';
import axios from 'axios';
import { BACKEND_URL } from '../utils/utils';

function GoogleLoginPage() {
  const responseGoogle = async response => {
    console.log(response.credential);
    try {
      const res = await axios.get(BACKEND_URL, response.credential);
      console.log(res);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <GoogleOAuthProvider clientId={`${process.env.REACT_APP_GOOGLE_API_TOKEN}`}>
      <GoogleLogin
        render={renderProps => (
          <button
            type="button"
            onClick={() => {
              renderProps.onClick();
            }}
            disabled={renderProps.disabled}
          >
            Sign in with google
          </button>
        )}
        type="icon"
        shape="circle"
        onSuccess={responseGoogle}
        onFailure={responseGoogle}
        cookiePolicy="single_host_origin"
      />
    </GoogleOAuthProvider>
  );
}

export default GoogleLoginPage;
