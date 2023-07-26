import { useEffect } from 'react';
import axios from 'axios';

function KakaoCallback() {
  useEffect(() => {
    const params = new URL(document.location.toString()).searchParams;
    const code = params.get('code');
    const grantType = 'authorization_code';
    const REST_API_KEY = `${process.env.REACT_APP_KAKAO_REST_API_KEY}`;
    const REDIRECT_URI = `${process.env.REACT_APP_KAKAO_REDIRECT_URL}`;
    console.log(code);

    axios
      .post(
        `https://kauth.kakao.com/oauth/token?grant_type=${grantType}&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&code=${code}`,
        {},
        {
          headers: {
            'Content-type': 'application/x-www-form-urlencoded;charset=utf-8',
          },
        },
      )
      .then(res => {
        console.log(res);
        const accesstoken = res.data;
        axios
          .post(
            `https://kapi.kakao.com/v2/user/me`,
            {},
            {
              headers: {
                Authorization: `Bearer ${accesstoken}`,
                'Content-type':
                  'application/x-www-form-urlencoded;charset=utf-8',
              },
            },
          )
          .then(ressd => {
            console.log('2번쨰', ressd);
          });
      })
      .catch(Error => {
        console.log(Error);
      });
  }, []);

  return <div>callback</div>;
}
export default KakaoCallback;
