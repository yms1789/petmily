import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import { styled } from '@mui/material';
import { bool } from 'prop-types';
import { useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import headerLogo from '../static/images/headerLogo.svg';
import { placeholderImage } from '../utils/utils';
import CONSTANTS from '../utils/constants';

function Header({ isLoggedIn } = false) {
  const [link, setLink] = useState('');
  const StyledArrowDownIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowDownIosRoundedIcon',
    slot: 'Wrapper',
  })({
    transform: 'rotate(90deg)',
  });

  return (
    <>
      <div
        className={`flex items-center justify-between rounded-[20px] bg-white ${
          link === '마이페이지' ? 'min-w-[1832px]' : 'min-w-[1340px]'
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
              link === CONSTANTS.HEADER.PRODUCT
                ? 'text-dodgerblue'
                : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setLink(CONSTANTS.HEADER.PRODUCT);
            }}
          >
            {CONSTANTS.HEADER.PRODUCT}
          </Link>
          <Link
            to="/curation"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              link === CONSTANTS.HEADER.CURATION
                ? 'text-dodgerblue'
                : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setLink(CONSTANTS.HEADER.CURATION);
            }}
          >
            {CONSTANTS.HEADER.CURATION}
          </Link>
          <Link
            to="/social"
            className={`no-underline px-5 font-semibold whitespace-nowrap ${
              link === CONSTANTS.HEADER.SOCIAL
                ? 'text-dodgerblue'
                : 'text-darkgray'
            } hover:text-dodgerblue`}
            onClick={() => {
              setLink(CONSTANTS.HEADER.SOCIAL);
            }}
          >
            {CONSTANTS.HEADER.SOCIAL}
          </Link>
        </div>
        {isLoggedIn ? (
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
                src={placeholderImage}
                className="w-14 h-14 rounded-[100px]"
                alt=""
              />
            </div>
            <div
              className="rounded-31xl 
                   bg-white text-black border-solid border-2 border-dodgerblue
               overflow-hidden flex flex-row py-2.5 px-5 items-center justify-start gap-[0.5rem]"
            >
              <div className="relative tracking-[0.01em] leading-[125%] font-extrabold px-2 rounded-xl text-lg">
                nickname
              </div>
              <StyledArrowDownIosRoundedIcon className="text-dodgerblue" />
            </div>
          </div>
        )}
      </div>
      <Outlet />
    </>
  );
}
Header.propTypes = {
  isLoggedIn: bool,
};
export default Header;
