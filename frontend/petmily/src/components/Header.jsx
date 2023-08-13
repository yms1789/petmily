import { useState } from 'react';
import { Link, Outlet } from 'react-router-dom';

import { useRecoilState, useRecoilValue } from 'recoil';
import headerLogo from 'static/images/headerLogo.svg';
import CONSTANTS from 'utils/constants';
import authAtom from 'states/auth';
import userAtom from 'states/users';
import headerAtom from 'states/headers';

import PortalPopup from './PortalPopup';
import Alarm from './Alarm';
import CustomSelect from './CustomSelect';

function Header() {
  const auth = useRecoilValue(authAtom);
  const userLogin = useRecoilValue(userAtom);
  const [clickedHeader, setClickedHeader] = useRecoilState(headerAtom);
  const [alarmtDot, setAlarmDot] = useState(true);
  const [showAlarmModal, setShowAlarmModal] = useState(false);

  const onAlarmClick = () => {
    if (alarmtDot) {
      setAlarmDot(false);
    }
    setShowAlarmModal(!showAlarmModal);
  };

  const closeAlarmModal = () => {
    setShowAlarmModal(false);
  };

  return (
    <>
      <div
        className={`flex items-center justify-between rounded-[20px] bg-white ${
          clickedHeader === '마이페이지' || clickedHeader === '상점'
            ? 'min-w-[1400px]'
            : 'min-w-[1280px]'
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
              clickedHeader === CONSTANTS.HEADER.CURATION ||
              clickedHeader === ''
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
            <div
              role="presentation"
              onClick={onAlarmClick}
              className="relative rounded-full flex items-center justify-center"
            >
              <img
                src={userLogin.userProfileImage}
                className="w-12 h-12 rounded-full"
                alt=""
              />
              {alarmtDot ? (
                <div className="absolute top-0 -right-1 w-4 h-4 rounded-full bg-red" />
              ) : null}
            </div>
            <CustomSelect
              component="header"
              select={userLogin.userNickname}
              options={['상점', '마이페이지', '로그아웃']}
            />
          </div>
        )}
      </div>
      <Outlet />
      {showAlarmModal && (
        <PortalPopup
          overlayColor="rgba(113, 113, 113, 0.4)"
          placement="Top right"
          onOutsideClick={closeAlarmModal}
          alarm="alarm"
        >
          <Alarm onClose={closeAlarmModal} />
        </PortalPopup>
      )}
    </>
  );
}
export default Header;
