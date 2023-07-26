import { useEffect } from 'react';
import axios from 'axios';

function KakaoCallback() {
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
      } catch (error) {
        console.log(error);
      }
    };

    sendCodeToBackend();
  }, []);

  return <div>callback</div>;
}

export default KakaoCallback;
