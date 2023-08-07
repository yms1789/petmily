import { useRecoilState } from 'recoil';
import axios from 'axios';
import authAtom from 'states/auth';

function useFetch() {
  const [auth, setAuth] = useRecoilState(authAtom);

  function authHeader(url) {
    // return auth header with jwt if user is logged in and request is to the api url
    const token = auth?.token;
    const isLoggedIn = !!token;
    const isApiUrl = url.startsWith(process.env.REACT_APP_API_URL);
    if (isLoggedIn && isApiUrl) {
      return { Authorization: `Bearer ${token}` };
    }
    return {};
  }
  async function handleResponse(response) {
    console.log('response', response);
    const { data } = response;

    if (!response.statusText === 'OK') {
      // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
      if (response.status === 401) {
        const newAccessToken = await axios.get('토큰 갱신');
        setAuth(prevAuth => ({ ...prevAuth, accessToken: newAccessToken }));
        const newConfig = {
          ...response.config,
          headers: {
            ...response.config.headers,
            Authorization: `Bearer ${newAccessToken}`,
          },
        };
        // 중단된 요청 (에러난 요청)을 새로운 토큰으로 재전송
        const originalResponse = await axios.request(newConfig);
        console.log(originalResponse);
        return originalResponse.data.data;
      }
      setAuth(null);

      const error = (data && data.message) || response.message;
      return Promise.reject(error);
    }
    console.log('data', data);
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
        requestOptions.body = JSON.stringify(body);
      }
      const response = await axios({
        method: requestOptions.method,
        url,
        data: body,
        headers: requestOptions.headers,
      });
      return handleResponse(response);
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
