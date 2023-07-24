import axios from 'axios';
import { useCallback, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import PasswordResetModal from '../components/PasswordResetModal';
import PortalPopup from '../components/PortalPopup';
import logo from '../static/images/logo.svg';

function Login() {
  const [isPasswordResetModalOpen, setPasswordResetModalOpen] = useState(false);
  const loginEmail = useRef(null);
  const loginPassword = useRef(null);
  const [validationError, setValidationError] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const openPasswordResetModal = useCallback(() => {
    setPasswordResetModalOpen(true);
  }, []);

  const closePasswordResetModal = useCallback(() => {
    setPasswordResetModalOpen(false);
  }, []);

  const handleLogin = async () => {
    // 로그인 데이터 백엔드에 전달
    try {
      const response = await axios.post('login', { email, password });
      console.log(response.status);
    } catch (error) {
      setValidationError(true);
      setPassword('');
    }
  };

  return (
    <>
      <div className="flex flex-col items-center justify-start bg-whitesmoke-100 w-full h-full overflow-hidden text-left text-5xl text-dodgerblue font-pretendard">
        <div className="min-h-screen w-[450px] flex flex-col items-center justify-center gap-[43px]">
          <div className="relative w-[197px] text-[50px] font-one-mobile-pop-otf">
            <img className="w-[200px] h-auto" src={logo} alt="" />
          </div>
          <div className="self-stretch flex flex-col items-center justify-start text-xl text-darkgray">
            <div className="rounded-8xs bg-white box-border w-full flex flex-row  items-center justify-start ">
              <input
                className={`focus:outline-none self-stretch rounded-tl-3xs rounded-tr-3xs bg-white w-full flex flex-row py-5 px-4
            items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
            ${
              validationError ? 'border-red-300' : 'focus:border-dodgerblue'
            }  focus:border-1.5 font-pretendard text-base 
            hover:brightness-95 focus:brightness-100`}
                ref={loginEmail}
                placeholder="이메일"
                onChange={e => {
                  setValidationError(false);
                  setEmail(e.target.value);
                }}
                value={email}
              />
            </div>
            <div className="rounded-8xs bg-white box-border w-full flex flex-row  items-center justify-start">
              <input
                type="password"
                className={`focus:outline-none self-stretch rounded-bl-3xs rounded-br-3xs bg-white w-full flex flex-row py-5 px-4
            items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
            ${
              validationError ? 'border-red-300' : 'focus:border-dodgerblue'
            } focus:border-1.5 font-pretendard text-base 
            hover:brightness-95 focus:brightness-100 mt-[-1px]`}
                ref={loginPassword}
                placeholder="비밀번호"
                value={password}
                onChange={e => {
                  setValidationError(false);
                  setPassword(e.target.value);
                }}
              />
            </div>
          </div>
          <div
            role="presentation"
            className="self-stretch rounded-31xl bg-dodgerblue h-[72.02px] flex flex-row items-center justify-center text-white hover:brightness-110 cursor-pointer"
            onClick={handleLogin}
          >
            <b className="relative tracking-[0.01em] leading-[125%]">로그인</b>
          </div>
          <div className="flex flex-row items-start justify-start gap-[50px] text-slategray">
            <span
              role="presentation"
              className="relative tracking-[0.01em] leading-[50%] flex items-center w-fit text-xl shrink-0 cursor-pointer  hover:brightness-125"
              onClick={openPasswordResetModal}
            >
              비밀번호 재설정
            </span>
            <Link
              className="relative no-underline text-slategray tracking-[0.01em] leading-[50%] flex items-center w-[84px] text-xl shrink-0 cursor-pointer hover:brightness-125"
              role="presentation"
              to="/join"
            >
              회원가입
            </Link>
          </div>
          <div className="w-full flex flex-row items-center justify-start gap-[49px] text-center text-[16px] text-lightslategray">
            <hr className="border-solid w-full h-0.5 bg-darkgray brightness-125" />
            <div className="relative inline-block w-[22px] shrink-0">OR</div>
            <hr className="border-solid w-full h-0.5 bg-darkgray brightness-125" />
          </div>
          <div className="flex flex-row items-start justify-start pb-10">
            <div className="relative w-[162px] h-[82px] overflow-hidden">
              <h3>소셜 로그인 칸</h3>
            </div>
          </div>
        </div>
      </div>
      {isPasswordResetModalOpen && (
        <PortalPopup
          overlayColor="rgba(113, 113, 113, 0.4)"
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
