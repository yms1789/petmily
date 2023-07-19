import { React, useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/Header';
import { Curation, Join, Product, Social } from './pages/index';

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
  const hideHeader = window.location.pathname.includes('join');

  return (
    <div className="App">
      <BrowserRouter>
        {!hideHeader && <Header />}
        <Routes>
          <Route path="/" element={<Curation />} />
          <Route path="/product" element={<Product />} />
          <Route path="/social" element={<Social />} />
          <Route path="/join" element={<Join />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
