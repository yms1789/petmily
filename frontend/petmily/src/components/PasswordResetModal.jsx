import { useCallback, useRef, useState } from 'react';

import axios from 'axios';
import { func } from 'prop-types';

/**
 *
 * 비밀번호 재설정 파트
 * 이메일 입력 → 소셜 로그인으로 가입된 이메일 or 가입되어 있지 않은 이메일이면 에러 메시지 발생
 *          → 인증코드를 보냄 → 인증 버튼 클릭 → 보낸 코드와 입력한 코드가 다르면 에러 메시지 발생
 *                                     → 인증코드 인증까지 완료되었다면 비밀번호 재설정 버튼 활성화 → 비밀번호 초기화 시키고 메일로 보내줌
 *
 */
function PasswordResetModal({ onClose }) {
  const emailRef = useRef(null);
  const [validEmail, setValidEmail] = useState('');
  const [validCode, setValidCode] = useState('');

  const [visibleValidEmailError, setVisibleEmailError] = useState(false);
  const [visibleValidCodeError, setVisibleValidCodeError] = useState(false);
  const [confirmation, setConfirmation] = useState({
    email: false,
    code: false,
  });

  const onChangeVailidEamil = useCallback(e => {
    setVisibleEmailError(false);
    setValidEmail(e.target.value.trim());
  }, []);
  const onChageValidCode = useCallback(e => {
    setVisibleValidCodeError(false);
    setValidCode(e.target.value.trim());
  }, []);

  const handleValidationEmail = useCallback(async () => {
    console.log('인증 클릭');
    if (
      !/^(([^<>()\\[\]\\.,;:\s@\\"]+(\.[^<>()\\[\]\\.,;:\s@\\"]+)*)|(\\".+\\"))@(([^<>()[\]\\.,;:\s@\\"]+\.)+[^<>()[\]\\.,;:\s@\\"]{2,})$/.test(
        validEmail,
      )
    ) {
      setVisibleEmailError(true);
      return;
    }
    try {
      const response = await axios.post('resetpassword/email', {
        userEmail: validEmail,
      });
      console.log(response);
      // 응답코드가 200이면
      if (response.status === 200) {
        setConfirmation({ ...confirmation, email: true });
      }
    } catch (error) {
      const errorResponse = error.response;
      console.log(errorResponse);
      setVisibleEmailError(true);
    }
  }, [confirmation, validEmail]);
  const handleValidationCode = useCallback(async () => {
    console.log('인증 클릭');
    try {
      const response = await axios.post('email/verification', {
        userEmail: validEmail,
        code: validCode,
      });
      console.log(response);
      if (response.status === 200) {
        setConfirmation({ ...confirmation, code: true });
      }
    } catch (error) {
      const errorResponse = error.response;
      console.log(errorResponse);
      setVisibleValidCodeError(true);
    }
  }, [confirmation, validCode, validEmail]);

  const handleReset = useCallback(async () => {
    try {
      const response = await axios.put('resetpassword/reset', {
        userEmail: validEmail,
      });
      console.log(response);
      if (response.status === 200) {
        setConfirmation({ ...confirmation, code: true });
      }
    } catch (error) {
      const errorResponse = error.response;
      console.log(errorResponse);
      setVisibleValidCodeError(true);
    }
    alert('비밀번호 초기화 완료');
    onClose();
  }, [confirmation, onClose, validEmail]);

  return (
    <div className="relative rounded-[10px] bg-white w-[656px] h-[450px] max-w-full flex flex-col justify-center items-center max-h-full text-left text-xl text-darkgray font-pretendard">
      <div className="absolute flex flex-col items-start justify-center gap-[23px]">
        <div className="relative tracking-[0.01em] leading-[125%] text-gray">
          가입한 이메일 주소를 입력해주세요.
        </div>
        <div className="flex-1 rounded-xl w-full flex flex-row items-center justify-end relative text-5xl text-white">
          <input
            className="w-full focus:outline-none self-stretch rounded-3xs bg-white flex flex-row py-5 px-5
            items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
            focus:border-dodgerblue focus:border-1.5 font-pretendard text-xl 
            hover:brightness-95 focus:brightness-100"
            ref={emailRef}
            placeholder="이메일"
            onChange={onChangeVailidEamil}
          />
          <span
            role="presentation"
            onClick={handleValidationEmail}
            className="absolute bg-dodgerblue px-5 py-3 rounded-3xs my-0 mx-[!important] right-5 text-white tracking-[0.01em] leading-[125%] flex items-center justify-center w-[45.03px] h-[20.62px] shrink-0 z-[1] cursor-pointer"
          >
            확인
          </span>
        </div>
        {visibleValidEmailError ? (
          <span className="text-red-500 text-base w-full">
            유효한 이메일 주소를 입력해주세요.
          </span>
        ) : null}
        <div className="flex-1 rounded-xl w-full flex flex-row items-center justify-end relative text-5xl text-white">
          <input
            className="focus:outline-none self-stretch rounded-3xs bg-white w-full flex flex-row py-5 px-5
            items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
            focus:border-dodgerblue focus:border-1.5 font-pretendard text-xl 
            hover:brightness-95 focus:brightness-100"
            // ref={loginEmail}
            placeholder="인증 코드"
            onChange={onChageValidCode}
          />
          <span
            role="presentation"
            className="absolute bg-dodgerblue px-5 py-3 rounded-3xs my-0 mx-[!important] right-5 text-white tracking-[0.01em] leading-[125%] flex items-center justify-center w-[45.03px] h-[20.62px] shrink-0 z-[0] cursor-pointer"
            onClick={handleValidationCode}
          >
            인증
          </span>
        </div>
        {visibleValidCodeError ? (
          <span className="text-red-500 text-base w-full">
            인증코드가 맞지 않습니다.
          </span>
        ) : null}
        <button
          type="button"
          className={`self-stretch rounded-31xl ${
            !(confirmation.code && confirmation.email)
              ? 'bg-darkgray'
              : 'bg-dodgerblue hover:brightness-110'
          } overflow-hidden flex flex-row py-[21px] px-[172px] items-center justify-center text-5xl text-white cursor-pointer`}
          onClick={handleReset}
          disabled={!(confirmation.code && confirmation.email)}
        >
          <b
            type="button"
            className="relative tracking-[0.01em] leading-[125%]"
          >
            비밀번호 재설정
          </b>
        </button>
      </div>
    </div>
  );
}

PasswordResetModal.propTypes = {
  onClose: func,
};

export default PasswordResetModal;
