import { GoogleOAuthProvider } from '@react-oauth/google';

import { React, useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import {
  Curation,
  Join,
  Product,
  Social,
  UserInfo,
  PetInfo,
  Login,
} from './pages/index';
import LoginKakaoCallBack from './components/LoginKakaoCallback';
import LoginNaverCallBack from './components/LoginNaverCallback';
import LoginGoogle from './components/LoginGoogle';

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
            </Route>
            <Route path="/join" element={<Join />} />
            <Route path="/userinfo" element={<UserInfo />} />
            <Route path="/petinfo" element={<PetInfo />} />
            <Route path="/login" element={<Login />} component={LoginGoogle} />
            <Route
              path="/auth/kakao/callback"
              element={<LoginKakaoCallBack />}
            />
            <Route
              path="/auth/naver/callback"
              element={<LoginNaverCallBack />}
            />
          </Routes>
        </BrowserRouter>
      </GoogleOAuthProvider>
    </div>
  );
}

export default App;
