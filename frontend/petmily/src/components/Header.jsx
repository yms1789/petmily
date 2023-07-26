import { Link, Outlet } from 'react-router-dom';
import { useState } from 'react';
import headerLogo from '../static/images/headerLogo.svg';

function Header() {
  const [link, setLink] = useState('');
  return (
    <>
      <div className="flex items-center justify-between rounded-[20px] bg-white min-w-[1340px] max-w-full h-[80px] px-6 m-2 text-dodgerblue font-pretendard">
        <div className="flex items-center">
          <img
            className="w-[180px] h-auto object-cover"
            alt=""
            src={headerLogo}
          />
        </div>
        <div className="flex items-center text-xl text-darkgray">
          <Link
            to="/product"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              link === '인기용품 최저가' ? 'text-dodgerblue' : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setLink('인기용품 최저가');
            }}
          >
            인기용품 최저가
          </Link>
          <Link
            to="/curation"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              link === '큐레이션' ? 'text-dodgerblue' : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setLink('큐레이션');
            }}
          >
            큐레이션
          </Link>
          <Link
            to="/social"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              link === '소통하기' ? 'text-dodgerblue' : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setLink('소통하기');
            }}
          >
            소통하기
          </Link>
        </div>
        <div className="flex items-center justify-between text-lg text-black relative">
          <div className="rounded-full flex items-center justify-center">
            <Link
              to="/login"
              className="no-underline whitespace-nowrap text-black hover:text-dodgerblue"
            >
              로그인
            </Link>
          </div>
          <span className="h-[1.4rem] w-[2px] mx-3 bg-darkgray" />
          <div className="rounded-full flex items-center justify-center text-gray">
            <Link
              to="/join"
              className="no-underline whitespace-nowrap text-black hover:text-dodgerblue"
            >
              회원가입
            </Link>
          </div>
        </div>
      </div>
      <Outlet />
    </>
  );
}

export default Header;
