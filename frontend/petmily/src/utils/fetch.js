import { useRecoilState } from 'recoil';
import authAtom from 'states/auth';
import axios from 'axios';

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
  function handleResponse(response) {
    return response.text().then(text => {
      const data = text && JSON.parse(text);

      if (!response.ok) {
        if ([401, 403].includes(response.status) && auth?.token) {
          // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
          console.log('error', text);
          localStorage.removeItem('user');
          setAuth(null);
        }

        const error = (data && data.message) || response.statusText;
        return Promise.reject(error);
      }

      return data;
    });
  }
  function request(method) {
    return async (url, body) => {
      const requestOptions = {
        method,
        headers: authHeader(url),
      };
      if (body) {
        requestOptions.headers['Content-Type'] = 'application/json';
        requestOptions.body = JSON.stringify(body);
      }
      const response = await axios({
        method: requestOptions.method,
        url,
        data: body,
      });
      console.log('fetchResponse', response);
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
