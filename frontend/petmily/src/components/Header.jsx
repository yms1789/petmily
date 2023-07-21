// import { useCallback } from 'react';
import { Link, Outlet } from 'react-router-dom';
import headerLogo from '../static/images/headerLogo.svg';

function Header() {
  // const onMenuCurationTitleText4Click = useCallback(() => {
  //   // Please sync "Curation" to the project
  // }, []);

  // const onMenuProductTitleText4Click = useCallback(() => {
  //   // Please sync "Product" to the project
  // }, []);
  return (
    <>
      <div className="relative rounded-[30px] bg-white w-full h-[120px] overflow-hidden flex flex-row py-[18px] px-5 box-border items-center justify-between text-left text-[30px] text-dodgerblue font-pretendard">
        <div className="relative w-[206px] h-[63px] font-one-mobile-pop-otf">
          <img
            className="absolute top-[calc(50%_-_31.5px)] left-[0px] w-[189px] h-auto object-cover"
            alt=""
            src={headerLogo}
          />
        </div>
        <div className="relative w-[438px] h-7 text-3xl text-darkgray">
          <Link
            to="/curation"
            className="no-underline absolute top-[0px] left-[calc(50%_-_7px)] tracking-[0.01em] leading-[125%] font-semibold text-darkgray hover:text-dodgerblue"
          >
            큐레이션
          </Link>
          <Link
            to="/social"
            className="no-underline absolute top-[0px] left-[calc(50%_+_142px)] tracking-[0.01em] leading-[125%] font-semibold whitespace-nowrap text-darkgray hover:text-dodgerblue"
          >
            소통하기
          </Link>
          <Link
            to="/product"
            className="no-underline absolute top-[0px] left-[calc(50%_-_219px)] tracking-[0.01em] leading-[125%] font-semibold text-darkgray hover:text-dodgerblue"
          >
            인기용품 최저가
          </Link>
        </div>
        <div className="w-[205px] flex flex-row items-center justify-between text-lg text-black relative">
          <div className="flex-1 overflow-hidden flex flex-row py-2.5 px-[13px] items-center justify-center">
            <Link
              to="/login"
              className="no-underline relative tracking-[0.01em] leading-[125%] text-black hover:text-dodgerblue"
            >
              로그인
            </Link>
          </div>
          <div className="self-stretch flex-1 rounded-[50px] overflow-hidden flex flex-row py-3 px-0 items-center justify-center text-gray">
            <Link
              to="/join"
              className="no-underline relative tracking-[0.01em] leading-[125%] text-black hover:text-dodgerblue"
            >
              회원가입
            </Link>
          </div>
          <span className="absolute left-[52%] transform -translate-x-1/2 h-[28px] w-[2px] bg-darkgray" />
        </div>
      </div>
      <Outlet />
    </>
  );
}

export default Header;
