import { useEffect } from 'react';
import axios from 'axios';

function KakaoCallback() {
  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    const code = params.get('code');
    // const grantType = 'authorization_code';
    // const REST_API_KEY = `${process.env.REACT_APP_KAKAO_REST_API_KEY}`;
    // const REDIRECT_URI = `${process.env.REACT_APP_KAKAO_REDIRECT_URL}`;
    console.log(code);

    try {
      axios.post('login/kakao', { code });
    } catch (error) {
      console.log(error);
    }
  }, []);

  return <div>callback</div>;
}
export default KakaoCallback;
