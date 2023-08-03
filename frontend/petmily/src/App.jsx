import { useEffect, useState } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import { GoogleOAuthProvider } from '@react-oauth/google';

import {
  Header,
  LoginGoogle,
  LoginKakaoCallback,
  LoginNaverCallback,
} from 'components';
import {
  Curation,
  CurationCategory,
  CurationPet,
  Join,
  Login,
  MyPage,
  PetInfo,
  Product,
  ProductPet,
  Social,
  UserInfo,
} from 'pages';

function App() {
  const [isLoggedIn, setisLoggedIn] = useState(false);
  const handleRoute = () => {
    // 로그인이 되었을 때
    setisLoggedIn(true);
    // 안되었을 때
  };
  useEffect(() => {
    return () => {
      handleRoute();
    };
  }, [isLoggedIn]);

  return (
    <div className="App">
      <GoogleOAuthProvider clientId={process.env.REACT_APP_GOOGLE_API_TOKEN}>
        <BrowserRouter>
          <Routes>
            <Route element={<Header />}>
              <Route path="*" element={<Curation />} />
              <Route path="curation" element={<Curation />} />
              <Route path="product" element={<Product />} />
              <Route path="social" element={<Social />} />
              <Route path="mypage" element={<MyPage />} />
              <Route path="/pet/*" element={<CurationPet />} />
              <Route path="/category/*" element={<CurationCategory />} />
              <Route path="/product/*" element={<ProductPet />} />
            </Route>
            <Route path="/join" element={<Join />} />
            <Route path="/userinfo" element={<UserInfo />} />
            <Route path="/petinfo" element={<PetInfo />} />
            <Route
              path="/login"
              element={<Login o />}
              component={LoginGoogle}
            />
            <Route
              path="login/oauth2/code/kakao"
              element={<LoginKakaoCallback />}
            />
            <Route
              path="login/oauth2/code/naver"
              element={<LoginNaverCallback />}
            />
          </Routes>
        </BrowserRouter>
      </GoogleOAuthProvider>
    </div>
  );
}

export default App;
