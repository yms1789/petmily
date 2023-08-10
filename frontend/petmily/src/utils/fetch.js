import { useRecoilState, useRecoilValue } from 'recoil';
import axios from 'axios';
import authAtom from 'states/auth';
import userAtom from 'states/users';

function useFetch() {
  const [auth, setAuth] = useRecoilState(authAtom);
  const loginState = useRecoilValue(userAtom);
  function authHeader() {
    const token = auth?.accessToken;
    const isLoggedIn = !!token;
    if (isLoggedIn) {
      return { Authorization: `Bearer ${token}` };
    }
    return {};
  }

  async function handleResponse(response) {
    const { data } = response;

    if (response.statusText !== 'OK') {
      if (response.status === 401 || response.status === 403) {
        try {
          // 서버에 새로운 액세스 토큰 요청
          const newAccessTokenResponse = await axios.post(
            '/refreshAccessToken',
            { userEmail: loginState.userEmail, refreshToken: auth.userToken },
          );
          const newAccessToken = newAccessTokenResponse.data;
          setAuth(prevAuth => ({ ...prevAuth, accessToken: newAccessToken }));
          // 기존 요청 정보에 새로운 액세스 토큰을 포함하여 재요청
          const newConfig = {
            ...response.config,
            headers: {
              ...response.config.headers,
              Authorization: `Bearer ${newAccessToken}`,
            },
          };
          try {
            const originalResponse = await axios.request(newConfig);
            setAuth({ ...auth, accessToken: newAccessToken });
            return originalResponse.data;
          } catch (error) {
            return Promise.reject(error);
          }
        } catch (error) {
          setAuth(null); // 재발급 실패 시 로그아웃 처리 또는 적절한 에러 처리를 해야 합니다.
          return Promise.reject(error);
        }
      }

      setAuth(null);
      const error = (data && data.message) || response.message;
      return Promise.reject(error);
    }

    return data;
  }

  function request(method) {
    return async (url, body, page) => {
      const requestOptions = {
        method,
        headers: authHeader(url),
      };
      if (body) {
        requestOptions.headers['Content-Type'] =
          page === 'image' ? 'multipart/form-data' : 'application/json';
        requestOptions.data = body; // 'data' 속성에 요청 데이터 설정
      } else {
        requestOptions.headers['Content-Type'] = 'application/json';
      }

      try {
        const response = await axios({
          url,
          method,
          headers: requestOptions.headers,
          data: body,
          timeout: 5000,
        });
        return handleResponse(response);
      } catch (error) {
        // 에러 처리 로직 추가
        if (error.response.status === 403 || error.response.status === 401) {
          return handleResponse(error.response);
        }
        setAuth(null);
        return Promise.reject(error);
      }
    };
  }

  return {
    get: request('GET'),
    post: request('POST'),
    patch: request('PATCH'),
    put: request('PUT'),
    delete: request('DELETE'),
  };
}

export default useFetch;
