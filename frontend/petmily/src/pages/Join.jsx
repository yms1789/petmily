import ArrowDropDownOutlinedIcon from '@mui/icons-material/ArrowDropDownOutlined';
import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import ClearRoundedIcon from '@mui/icons-material/ClearRounded';
import { styled } from '@mui/material';
import { func, string } from 'prop-types';
import { useRef, useState, useCallback } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { isSameCheck, validateEmail, validatePassword } from '../utils/utils';

function EmailSelect({ addr, onChange }) {
  const StyledArrowDropDownOutlinedIcon = styled(ArrowDropDownOutlinedIcon, {
    name: 'StyledArrowDropDownOutlinedIcon',
    slot: 'Wrapper',
  })({
    color: '#a6a7ab',
    fontSize: 40,
    '&:hover': { color: '#1f90fe' },
  });
  return (
    <>
      <select
        className="appearance-none w-full h-full px-4 py-1 pr-8 text-base font-pretendard-medium rounded-lg 
    focus:outline-none border-solid border-[1.5px] border-darkgray focus:border-dodgerblue focus:border-1.5"
        value={addr}
        onChange={onChange}
      >
        <option value="none">선택해주세요</option>
        <option value="naver.com">naver.com</option>
        <option value="nate.com">nate.com</option>
        <option value="gmail.com">gmail.com</option>
        <option value="yahoo.com">yahoo.com</option>
        <option value="hanmail.net">hanmail.net</option>
        <option value="직접입력">직접입력</option>
      </select>
      <div className="absolute inset-y-0 right-0 flex items-center pr-2 pointer-events-none">
        <StyledArrowDropDownOutlinedIcon className="w-5 h-5 text-gray-400" />
      </div>
    </>
  );
}
function EmailInput({ value, onChange, setIsInput }) {
  return (
    <>
      <input
        type="text"
        className="focus:outline-none w-full h-full px-4
      rounded-3xs border-solid border-[1.5px] border-darkgray 
      focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
        placeholder="선택해주세요"
        value={value === '직접입력' ? null : value}
        onChange={onChange}
        required
      />
      <ClearRoundedIcon
        className="absolute right-3"
        onClick={() => {
          setIsInput(false);
        }}
      />
    </>
  );
}

function Join() {
  const navigate = useNavigate();
  const StyledArrowForwardIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#a6a7ab',
    '&:hover': { color: '#1f90fe' },
  });

  const [selectedSuffix, setSelectedSuffix] = useState('');
  const [selectedAddr, setSelectedAddr] = useState('');
  const [isInput, setIsInput] = useState(false);
  const [password, setPassword] = useState('');
  const [auth, setAuth] = useState({ email: false, code: false });
  const [verifyCode, setVerifyCode] = useState('');
  const [checkPassword, setCheckPassword] = useState('');
  const [visibleError, setVisibleError] = useState({
    email: false,
    password: false,
    checkPassword: false,
    code: false,
  });
  useState(false);
  const emailInput = useRef(null);
  const passwordInput = useRef(null);
  const checkPasswordInput = useRef(null);
  const verifyRef = useRef(null);

  const checkForm = () => {
    if (selectedAddr && selectedSuffix && password && checkPassword) {
      return true;
    }
    return false;
  };

  const handleJoin = async (email, inputPassword, inputCheckPassword) => {
    // 백엔드에 회원가입 정보 전달
    console.log('Join', inputPassword, inputCheckPassword);
    try {
      if (validateEmail(email)) {
        throw new Error(String(validateEmail(email)));
      }
      if (validatePassword(inputCheckPassword)) {
        throw new Error(String(validatePassword(inputCheckPassword)));
      }
      if (isSameCheck(inputPassword, inputCheckPassword)) {
        throw new Error(String(isSameCheck(inputPassword, inputCheckPassword)));
      }
      const response = await axios.post('signup', {
        userEmail: email,
        userPw: password,
      });
      console.log(response);
      alert('회원가입 완료');
      navigate('/login');
    } catch (error) {
      console.log('error', error);
      if (error.message === '유효한 이메일 주소를 입력해주세요.') {
        setVisibleError({ ...visibleError, email: true });
        emailInput.current.focus();
      }
      if (
        error.message ===
        '비밀번호는 영문자와 숫자를 포함한 8자 이상이어야 합니다.'
      ) {
        setVisibleError({ ...visibleError, password: true });
        passwordInput.current.focus();
      }
      if (error.message === '비밀번호를 다시 확인하세요.') {
        setVisibleError({ ...visibleError, checkPassword: true });
        checkPasswordInput.current.focus();
      }
    }
  };
  const handleValidationCode = useCallback(async () => {
    // 백엔드에 입력한 인증코드와 일치하는지 요청하는 메서드
    console.log('인증 클릭');
    try {
      // const response = await axios.post('signup/email/verification', {
      //   userEmail: `${selectedAddr}@${selectedSuffix}`,
      //   code: verifyCode,
      // });
      const response = await axios.post(
        `signup/email/verification?code=${verifyCode}&userEmail=${selectedAddr}@${selectedSuffix}`,
      );
      console.log(response);
      verifyRef.current.disabled = true;
      alert('인증 완료');
      setAuth({ ...auth, code: true });
    } catch (error) {
      const errorResponse = error.response;
      console.log(errorResponse);
      setVisibleError({ ...visibleError, code: true });
    }
  }, [auth, selectedAddr, selectedSuffix, verifyCode, visibleError]);

  const onChageValidCode = useCallback(
    e => {
      setVerifyCode(e.target.value.trim());
      setVisibleError({ ...visibleError, code: false });
    },
    [visibleError],
  );

  const handleEmailAuth = async email => {
    // 백엔드에 이메일 인증 요청하는 메서드
    try {
      const emailValidation = validateEmail(email);
      if (emailValidation.length > 0) throw new Error(emailValidation);
      const response = await axios.post('signup/email', {
        userEmail: email,
      });
      console.log(response);
      if (response.status === 200) {
        emailInput.current.disabled = true;
      }
      setAuth({ ...auth, email: true });
    } catch (error) {
      console.log('error', error.message);
    }
  };

  return (
    <div className="joinComponent">
      <div
        className="absolute top-[142px] left-[calc(50%_-_324px)] rounded-[20px]
       bg-white flex flex-col p-10 box-border items-center justify-start gap-[42px]"
      >
        <b className="self-stretch relative text-13xl tracking-[0.01em] leading-[125%]">
          회원가입
        </b>
        <div className="flex flex-col items-start justify-start gap-[18px] text-darkgray">
          <b className="relative tracking-[0.01em] leading-[125%] text-gray">
            이메일
          </b>
          <div className="w-[567px] flex flex-row items-center justify-start gap-[13px] text-xl">
            <div className="flex-1 bg-white flex flex-row items-center justify-center">
              <input
                type="text"
                className={`"focus:outline-none w-full h-full py-[21px] px-4 
                rounded-3xs border-solid border-[1.5px] border-darkgray 
                focus:border-dodgerblue focus:border-1.5 font-pretendard 
                text-base ${
                  auth.email
                    ? 'brightness-95'
                    : 'hover:brightness-95 focus:brightness-100'
                }`}
                placeholder="이메일"
                ref={emailInput}
                onChange={e => {
                  setVisibleError({ ...visibleError, email: false });
                  setSelectedAddr(e.target.value);
                }}
              />
            </div>
            <b className="relative text-13xl tracking-[0.01em] leading-[125%]">
              @
            </b>
            <div className="flex-1 rounded-3xs flex flex-col items-start justify-start relative gap-[10px]">
              <div
                className="relative rounded-3xs bg-white box-border w-[254px] 
              h-[60.07px] z-[0]  border-darkgray focus:outline-none"
              />
              <div
                className="my-0 mx-[!important] w-full h-full absolute flex 
              flex-row items-center justify-center z-[1] hover:brightness-95 focus:brightness-100"
              >
                {isInput ? (
                  <EmailInput
                    value={selectedSuffix}
                    onChange={e => setSelectedSuffix(e.target.value)}
                    setIsInput={setIsInput}
                  />
                ) : (
                  <EmailSelect
                    value={selectedAddr}
                    onChange={e => {
                      setSelectedSuffix(e.target.value);
                      if (e.target.value === '직접입력') {
                        setIsInput(true);
                      }
                    }}
                  />
                )}
              </div>
            </div>
          </div>
          {auth.email ? (
            <div className="flex-1 rounded-xl w-full flex flex-row items-center justify-end relative text-5xl text-white">
              <input
                className="focus:outline-none self-stretch rounded-3xs bg-white w-full flex flex-row py-5 px-5
              items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-xl 
              hover:brightness-95 focus:brightness-100"
                placeholder="인증 코드"
                onChange={onChageValidCode}
                ref={verifyRef}
                value={verifyCode}
              />
              <span
                role="presentation"
                className={`absolute px-5 py-3 rounded-3xs my-0 mx-[!important] right-5 text-white tracking-[0.01em] leading-[125%] flex items-center justify-center w-[45.03px] h-[20.62px] shrink-0 z-[0] cursor-pointer ${
                  verifyCode ? 'bg-dodgerblue' : 'bg-darkgray'
                }`}
                onClick={handleValidationCode}
                disabled={verifyCode}
              >
                인증
              </span>
            </div>
          ) : null}
          {visibleError.code ? (
            <span className="text-red-600">인증코드가 맞지 않습니다.</span>
          ) : null}

          <button
            type="submit"
            className={`self-stretch rounded-3xs ${
              selectedAddr && selectedSuffix ? 'bg-dodgerblue' : 'bg-white'
            }
            overflow-hidden flex flex-row py-[21px] px-[199px] 
            items-center justify-center border-[1.5px] border-solid border-darkgray hover:brightness-90 cursor-pointer`}
            onClick={() => {
              handleEmailAuth(`${selectedAddr}@${selectedSuffix}`);
            }}
            disabled={validateEmail(`${selectedAddr}@${selectedSuffix}`)}
          >
            <b
              className={`relative tracking-[0.01em] leading-[125%] ${
                selectedAddr && selectedSuffix ? 'text-white' : 'text-darkgray'
              } text-xl`}
            >
              이메일 인증하기
            </b>
          </button>
          {visibleError.email ||
          `${selectedAddr}${setSelectedSuffix}`.length <= 0 ? (
            <span className="text-red-500 text-base">
              유효한 이메일 주소를 입력해주세요.
            </span>
          ) : null}
        </div>
        <div className="w-[564px] flex flex-col items-start justify-start gap-[11px] text-xl">
          <b className="relative text-5xl tracking-[0.01em] leading-[125%]">
            비밀번호
          </b>
          <div className="relative tracking-[0.01em] leading-[125%] font-medium text-lightslategray">
            영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.
          </div>
          <input
            type="password"
            className="focus:outline-none self-stretch rounded-3xs bg-white flex flex-row py-5 px-4 
          items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
          focus:border-dodgerblue focus:border-1.5 font-pretendard text-base 
          hover:brightness-95 focus:brightness-100"
            ref={passwordInput}
            placeholder="비밀번호"
            onChange={e => {
              setVisibleError({ ...visibleError, password: false });
              setPassword(e.target.value);
            }}
          />
          {visibleError.password ? (
            <span className="text-red-600">
              영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.
            </span>
          ) : null}
        </div>
        <div className="w-[564px] flex flex-col items-start justify-start gap-[12px]">
          <b className="relative tracking-[0.01em] leading-[125%]">
            비밀번호 확인
          </b>
          <input
            type="password"
            className="focus:outline-none self-stretch rounded-3xs bg-white flex flex-row py-5 px-4 
          items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
          focus:border-dodgerblue focus:border-1.5 font-pretendard text-base 
          hover:brightness-95 focus:brightness-100"
            placeholder="비밀번호 확인"
            ref={checkPasswordInput}
            onChange={e => {
              setCheckPassword(e.target.value);
              setVisibleError({ ...visibleError, checkPassword: false });
            }}
          />
          {visibleError.checkPassword ? (
            <span className="text-red-600">비밀번호를 다시 확인하세요</span>
          ) : null}
        </div>
        <div className="self-stretch flex flex-col items-start justify-start gap-[13px] text-xl">
          <b
            className="relative text-5xl tracking-[0.01em] leading-[125%] 
          flex items-center w-[84px] h-[28.29px] shrink-0"
          >
            약관동의
          </b>
          <hr className="border-solid w-full h-0.5 bg-darkgray brightness-125" />
          <div className="flex flex-row items-center justify-start gap-[13px]">
            <div
              className="rounded-md bg-white box-border w-[22px] h-[20.75px] 
            flex flex-row items-center justify-center border-[1px] border-solid border-slategray"
            >
              <div className="relative w-0.5 h-0.5 opacity-[0]" />
            </div>
            <div className="relative leading-[150%]">
              <b>전체동의</b>
              <span className="text-[15px] text-slategray">
                선택항목에 대한 동의 포함
              </span>
            </div>
          </div>
          <div className="self-stretch relative max-w-full overflow-hidden h-0.5 shrink-0" />
          <div className="flex flex-row items-center justify-start gap-[13px]">
            <div
              className="rounded-md bg-white box-border w-[22px] h-[20.75px] 
            flex flex-row items-center justify-center border-[1px] border-solid border-slategray"
            />
            <div className="relative leading-[150%] inline-block w-[200px] h-[28.29px] shrink-0">
              <span>{`만 14세 이상입니다 `}</span>
              <span className="text-base text-dodgerblue">(필수)</span>
            </div>
          </div>
          <div className="self-stretch flex flex-row items-center justify-start">
            <div className="flex-1 flex flex-row items-center justify-start">
              <div className="flex-1 flex flex-row items-center justify-start">
                <div className="flex-1 flex flex-row items-center justify-between">
                  <div className="flex flex-row items-center justify-start gap-[13px]">
                    <div
                      className="rounded-md bg-white box-border w-[22px] h-[20.75px] 
                    flex flex-row items-center justify-center border-[1px] border-solid border-slategray"
                    >
                      <div className="relative w-0.5 h-0.5 opacity-[0]" />
                    </div>
                    <div className="relative leading-[150%] inline-block w-[113px] shrink-0">
                      <span>{`이용약관 `}</span>
                      <span className="text-base text-dodgerblue">(필수)</span>
                    </div>
                  </div>
                  <StyledArrowForwardIosRoundedIcon
                    onClick={() => {
                      console.log('click');
                    }}
                  />
                </div>
              </div>
            </div>
          </div>
          <div className="self-stretch flex flex-row items-center justify-start">
            <div className="flex-1 flex flex-row items-center justify-between">
              <div className="flex flex-row items-center justify-start gap-[13px]">
                <div
                  className="rounded-md bg-white box-border w-[22px] h-[20.75px]
                 flex flex-row items-center justify-center border-[1px] border-solid border-slategray"
                >
                  <div className="relative w-0.5 h-0.5 opacity-[0]" />
                </div>
                <div className="relative leading-[150%] inline-block w-[254px] h-[28.29px] shrink-0">
                  <span>{`개인정보 수집 및 이용 동의 `}</span>
                  <span className="text-base text-dodgerblue">(필수)</span>
                </div>
              </div>
              <StyledArrowForwardIosRoundedIcon
                onClick={() => {
                  console.log('click');
                }}
              />
            </div>
          </div>
          <div className="self-stretch flex flex-row items-center justify-between">
            <div className="flex flex-row items-center justify-center gap-[13px]">
              <div className="rounded-md bg-white box-border w-[22px] h-[20.75px] flex flex-row items-center justify-center border-[1px] border-solid border-slategray">
                {}
              </div>
              <div className="relative leading-[150%] inline-block w-[254px] h-[28.29px] shrink-0">
                <span>{`개인정보 마케팅 활용 동의  `}</span>
                <span className="text-base text-darkgray">(선택)</span>
              </div>
            </div>
            <StyledArrowForwardIosRoundedIcon
              onClick={() => {
                console.log('click');
              }}
            />
          </div>
        </div>
        <button
          type="submit"
          className={`rounded-[50px] ${
            checkForm() && auth.code ? 'bg-dodgerblue' : 'bg-darkgray'
          } w-full overflow-hidden flex flex-row py-[21px] px-[216px] items-center justify-center text-white hover:brightness-95 cursor-pointer`}
          onClick={() => {
            handleJoin(
              `${selectedAddr}@${selectedSuffix}`,
              password,
              checkPassword,
            );
          }}
          disabled={!(checkForm() && auth.code)}
        >
          <b className="relative tracking-[0.01em] leading-[125%] text-xl">
            회원가입하기
          </b>
        </button>
        <div className="self-stretch flex flex-row items-start justify-center gap-[10px] text-xl">
          <div className="relative tracking-[0.01em] leading-[125%]">
            이미 아이디가 있으신가요?
          </div>
          <Link
            to="/login"
            className="relative [text-decoration:underline] tracking-[0.01em] leading-[125%] font-bold text-dodgerblue hover:brightness-125"
          >
            로그인
          </Link>
        </div>
      </div>
    </div>
  );
}

EmailSelect.propTypes = {
  addr: string,
  onChange: func,
};
EmailInput.propTypes = {
  value: string,
  onChange: func,
  setIsInput: func,
};
export default Join;
