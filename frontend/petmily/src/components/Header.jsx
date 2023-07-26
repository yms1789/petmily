// import { useCallback } from 'react';
import { Link, Outlet } from 'react-router-dom';
import headerLogo from '../static/images/headerLogo.svg';

function Header() {
  return (
    <>
      <div className="flex items-center justify-between rounded-[20px] bg-white min-w-[1340px] max-w-full h-[80px] px-6 m-2 text-dodgerblue font-pretendard">
        <div className="flex items-center">
          <img
            className="w-[160px] h-auto object-cover"
            alt=""
            src={headerLogo}
          />
        </div>
        <div className="flex items-center text-xl text-darkgray">
          <Link
            to="/product"
            className="no-underline px-5 font-semibold whitespace-nowrap text-darkgray hover:text-dodgerblue"
          >
            인기용품 최저가
          </Link>
          <Link
            to="/curation"
            className="no-underline px-5 font-semibold whitespace-nowrap text-darkgray hover:text-dodgerblue"
          >
            큐레이션
          </Link>
          <Link
            to="/social"
            className="no-underline px-5 pr-10 font-semibold whitespace-nowrap text-darkgray hover:text-dodgerblue"
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
