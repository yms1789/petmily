import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import axios from 'axios';

function LoginKakaoCallback() {
  const navigation = useNavigate();
  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    const code = params.get('code');
    console.log('일단 인가코드는 프론트가 받음', code);

    const sendCodeToBackend = async () => {
      try {
        const response = await axios.post('/login/kakao', null, {
          params: { code },
        });
        console.log('백엔드로 전송되기는 함', response);
        if (response.status === 200) {
          navigation('/');
        }
      } catch (error) {
        console.log(error);
      }
    };

    sendCodeToBackend();
  }, [navigation]);
}

export default LoginKakaoCallback;
