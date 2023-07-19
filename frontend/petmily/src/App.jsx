import { React, useState, useEffect } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import Header from './components/Header';
import Curation from './pages/Curation';
import Product from './pages/Product';
import Social from './pages/Social';

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
      <BrowserRouter>
        <Header />
        <Routes>
          <Route path="/" element={<Curation />} />
          <Route path="/product" element={<Product />} />
          <Route path="/social" element={<Social />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
