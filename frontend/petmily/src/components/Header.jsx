import { CustomSelect } from 'components';
import { Link, Outlet } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';
import authAtom from 'states/auth';
import headerAtom from 'states/headers';
import headerLogo from 'static/images/headerLogo.svg';
import CONSTANTS from 'utils/constants';
import { placeholderImage } from 'utils/utils';

function Header() {
  const auth = useRecoilValue(authAtom);
  const [clickedHeader, setClickedHeader] = useRecoilState(headerAtom);
  return (
    <>
      <div
        className={`flex items-center justify-between rounded-[20px] bg-white ${
          clickedHeader === '마이페이지' ? 'min-w-[1832px]' : 'min-w-[1340px]'
        } max-w-full h-[80px] px-6 m-2 text-dodgerblue font-pretendard`}
      >
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
              clickedHeader === CONSTANTS.HEADER.PRODUCT
                ? 'text-dodgerblue'
                : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setClickedHeader(CONSTANTS.HEADER.PRODUCT);
            }}
          >
            {CONSTANTS.HEADER.PRODUCT}
          </Link>
          <Link
            to="/curation"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              clickedHeader === CONSTANTS.HEADER.CURATION
                ? 'text-dodgerblue'
                : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setClickedHeader(CONSTANTS.HEADER.CURATION);
            }}
          >
            {CONSTANTS.HEADER.CURATION}
          </Link>
          <Link
            to="/social"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              clickedHeader === CONSTANTS.HEADER.SOCIAL
                ? 'text-dodgerblue'
                : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setClickedHeader(CONSTANTS.HEADER.SOCIAL);
            }}
          >
            {CONSTANTS.HEADER.SOCIAL}
          </Link>
        </div>
        {!auth ? (
          <div className="flex items-center justify-between text-lg text-black relative">
            <div className="rounded-full flex items-center justify-center">
              <Link
                to="/login"
                className="no-underline whitespace-nowrap text-black hover:text-dodgerblue"
              >
                {CONSTANTS.HEADER.LOGIN}
              </Link>
            </div>
            <span className="h-[1.4rem] w-[2px] mx-3 bg-darkgray" />
            <div className="rounded-full flex items-center justify-center text-gray">
              <Link
                to="/join"
                className="no-underline whitespace-nowrap text-black hover:text-dodgerblue"
              >
                {CONSTANTS.HEADER.JOIN}
              </Link>
            </div>
          </div>
        ) : (
          <div className="flex items-center justify-between text-lg text-black relative gap-5">
            <div className="rounded-full flex items-center justify-center">
              <img
                src={placeholderImage(Math.floor(Math.random()) * 101)}
                className="w-14 h-14 rounded-[100px]"
                alt=""
              />
            </div>
            <CustomSelect
              component="header"
              select="nickname"
              options={['설정', '마이페이지', '로그아웃']}
            />
          </div>
        )}
      </div>
      <Outlet />
    </>
  );
}
export default Header;
