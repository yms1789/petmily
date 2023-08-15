import { useCallback, useEffect, useState } from 'react';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import { useRecoilState, useRecoilValue } from 'recoil';

import EventAvailableIcon from '@mui/icons-material/EventAvailable';
import { styled } from '@mui/material';
import swal from 'sweetalert';

import headerLogo from 'static/images/headerLogo.svg';
import CONSTANTS from 'utils/constants';
import authAtom from 'states/auth';
import userAtom from 'states/users';
import headerAtom from 'states/headers';

import useFetch from 'utils/fetch';
import PortalPopup from './PortalPopup';
import Alarm from './Alarm';
import CustomSelect from './CustomSelect';

function Header() {
  const StyledEventAvailableIcon = styled(EventAvailableIcon, {
    name: 'StyledEventAvailableIcon',
    slot: 'Wrapper',
  })({
    fontSize: 50,
    paddingTop: 5,
    '&:hover': { color: '#1f90fe' },
  });
  const navigate = useNavigate();
  const auth = useRecoilValue(authAtom);
  const user = useRecoilValue(userAtom);
  const [clickedHeader, setClickedHeader] = useRecoilState(headerAtom);
  const [alarmtDot, setAlarmDot] = useState(true);
  const [showAlarmModal, setShowAlarmModal] = useState(false);
  const fetchAttendance = useFetch();

  const readAlarmCheck = async () => {
    try {
      const response = await fetchAttendance.get(
        `/noti/checked/${user.userEmail}`,
      );
      setAlarmDot(response);
      console.log('알림읽기', response);
    } catch (error) {
      console.log(error);
    }
  };

  const onAlarmClick = () => {
    if (alarmtDot) {
      setAlarmDot(false);
    }
    setShowAlarmModal(!showAlarmModal);
  };

  const closeAlarmModal = async () => {
    try {
      await fetchAttendance.get(`/noti/status/${user.userEmail}`);
    } catch (error) {
      console.log(error);
    }
    setShowAlarmModal(false);
  };

  const handleAttendance = useCallback(async () => {
    try {
      const data = await fetchAttendance.put('attendance', {
        userEmail: user.userEmail,
      });
      console.log('att', data);
      if (data === true) {
        swal('출석 완료');
      } else {
        swal('이미 출석을 완료했습니다.');
      }
    } catch (error) {
      console.log(error);
    }
  }, []);

  useEffect(() => {
    readAlarmCheck();
  }, []);

  return (
    <>
      <div
        className={`fixed top-2 z-50 w-[98%] px-2 flex items-center justify-between rounded-[20px] bg-white ${
          clickedHeader === '마이페이지' || clickedHeader === '상점'
            ? 'min-w-[1400px]'
            : 'min-w-[1280px]'
        } max-w-full h-[80px] text-dodgerblue font-pretendard`}
      >
        <div
          className="flex items-center cursor-pointer"
          role="presentation"
          onClick={() => {
            navigate('/curation');
          }}
        >
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
            <StyledEventAvailableIcon
              onClick={handleAttendance}
              className="cursor-pointer"
            />
            <div
              role="presentation"
              onClick={onAlarmClick}
              className="relative rounded-full flex items-center justify-center"
            >
              <img
                src={user.userProfileImg}
                className="w-12 h-12 rounded-full"
                alt=""
              />
              {alarmtDot ? (
                <div className="absolute top-0 -right-1 w-4 h-4 rounded-full bg-red" />
              ) : null}
            </div>
            <CustomSelect
              component="header"
              select={user.userNickname}
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
