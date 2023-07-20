import { useState, useCallback } from 'react';
import PasswordResetModal from '../components/PasswordResetModal';
import PortalPopup from '../components/PortalPopup';

function Login() {
  const [isPasswordResetModalOpen, setPasswordResetModalOpen] = useState(false);

  // const openPasswordResetModal = useCallback(() => {
  //   setPasswordResetModalOpen(true);
  // }, []);

  const closePasswordResetModal = useCallback(() => {
    setPasswordResetModalOpen(false);
  }, []);

  // const onText3Click = useCallback(() => {
  //   // Please sync "Join" to the project
  // }, []);

  return (
    <>
      <div className="relative flex flex-co items-center justify-center bg-whitesmoke-100 w-full h-screen overflow-hidden text-left text-5xl text-dodgerblue font-pretendard">
        <div className="absolute h-[773px] flex flex-col items-center justify-center gap-[43px]">
          <div className="relative w-[197px] h-[214px] text-[50px] font-one-mobile-pop-otf">
            <div className="absolute top-1/4 left-[17px] tracking-[0.3em] inline-block w-[164px] whitespace-nowrap font-one-mobile-pop-otf">
              펫밀리
            </div>
          </div>
          <div className="self-stretch flex flex-col items-center justify-start gap-[20px] text-xl text-darkgray">
            <div className="rounded-8xs bg-white box-border w-[568px] overflow-hidden flex flex-row py-[18px] px-4 items-center justify-start border-[2px] border-solid border-whitesmoke-200">
              <div className="relative tracking-[0.01em] leading-[125%]">
                아이디
              </div>
            </div>
            <div className="self-stretch rounded-8xs bg-white overflow-hidden flex flex-row py-[18px] px-4 items-center justify-start border-[2px] border-solid border-whitesmoke-200">
              <div className="relative tracking-[0.01em] leading-[125%]">
                비밀번호
              </div>
            </div>
          </div>
          <div className="self-stretch rounded-31xl bg-dodgerblue h-[72.02px] flex flex-row items-center justify-center text-white">
            <b className="relative tracking-[0.01em] leading-[125%]">로그인</b>
          </div>
          <div className="flex flex-row items-start justify-start gap-[23px] text-slategray">
            <b
              className="relative tracking-[0.01em] leading-[125%] flex items-center w-[153px] shrink-0 cursor-pointer"
              // onClick={openPasswordResetModal}
            >
              비밀번호 재설정
            </b>
            <b
              className="relative tracking-[0.01em] leading-[125%] flex items-center w-[84px] shrink-0 cursor-pointer"
              // onClick={onText3Click}
            >
              회원가입
            </b>
          </div>
          <div className="w-full flex flex-row items-center justify-start gap-[49px] text-center text-[16px] text-lightslategray">
            <hr className="border-solid w-full h-0.5 bg-darkgray brightness-125" />
            <div className="relative inline-block w-[22px] shrink-0">OR</div>
            <hr className="border-solid w-full h-0.5 bg-darkgray brightness-125" />
          </div>
          <div className="flex flex-row items-start justify-start gap-[48px]">
            <div className="relative w-[82px] h-[82px]">
              <div className="absolute top-[0px] left-[0px] rounded-31xl bg-whitesmoke-200 w-[82px] h-[82px] overflow-hidden" />
              <img
                className="absolute top-[16.4px] left-[16.4px] w-[49.2px] h-[49.2px] object-cover"
                alt=""
                src="/image-52@2x.png"
              />
            </div>
            <img
              className="relative w-[82.71px] h-[81.98px] object-cover"
              alt=""
              src="/kakaologinbutton@2x.png"
            />
            <img
              className="relative w-[82.71px] h-[81.02px] object-cover"
              alt=""
              src="/naverloginbutton@2x.png"
            />
          </div>
        </div>
      </div>
      {isPasswordResetModalOpen && (
        <PortalPopup
          overlayColor="rgba(113, 113, 113, 0.3)"
          placement="Centered"
          onOutsideClick={closePasswordResetModal}
        >
          <PasswordResetModal onClose={closePasswordResetModal} />
        </PortalPopup>
      )}
    </>
  );
}

export default Login;
