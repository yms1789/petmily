import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import useFetch from 'utils/fetch';

function LoginKakaoCallback() {
  const navigation = useNavigate();

  const fetchKakao = useFetch();

  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    const code = params.get('code');
    console.log('일단 인가코드는 프론트가 받음', code);

    const sendCodeToBackend = async () => {
      try {
        const response = await fetchKakao.post('login/kakao', null, {
          params: code,
        });
        console.log('백엔드로 전송되기는 함', response);
        navigation('/');
      } catch (error) {
        console.log(error);
      }
    };

    sendCodeToBackend();
  }, [navigation]);
}

export default LoginKakaoCallback;
