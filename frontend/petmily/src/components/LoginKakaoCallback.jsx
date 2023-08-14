import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { CircularProgress } from '@mui/material';
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
        const response = await fetchKakao.get(`oauth/kakao?code=${code}`);
        console.log('백엔드로 전송되기는 함', response);
        navigation('/');
      } catch (error) {
        console.log(error);
      }
    };

    sendCodeToBackend();
  }, [navigation]);
  return (
    <div className="flex w-full h-full flex-col gap-10 justify-center items-center text-darkgray">
      <div>로그인 중입니다.</div>
      <CircularProgress color="inherit" size={70} />
    </div>
  );
}

export default LoginKakaoCallback;
