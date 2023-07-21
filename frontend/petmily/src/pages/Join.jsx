import ArrowDropDownOutlinedIcon from '@mui/icons-material/ArrowDropDownOutlined';
import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import ClearRoundedIcon from '@mui/icons-material/ClearRounded';
import { styled } from '@mui/material';
import { func, string } from 'prop-types';
import { useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import BACKEND_URL from '../utils/utils';

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
  const [checkPassword, setCheckPassword] = useState('');
  const [visibleEmailError, setVisibleEmailError] = useState(false);
  const [visiblePasswordError, setVisiblePasswordError] = useState(false);
  const emailInput = useRef(null);
  const passwordInput = useRef(null);
  const checkForm = () => {
    if (selectedAddr && selectedSuffix && password && checkPassword) {
      return true;
    }
    return false;
  };

  const validateEmail = email => {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
      return '유효한 이메일 주소를 입력해주세요.';
    }
    return '';
  };
  const validatePassword = inputPassword => {
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d).{8,}$/;
    if (!passwordPattern.test(inputPassword)) {
      return '비밀번호는 영문자와 숫자를 포함한 8자 이상이어야 합니다.';
    }
    return '';
  };

  const handleJoin = async (email, inputPassword, inputCheckPassword, e) => {
    // 백엔드에 회원가입 정보 전달
    e.preventDefault();
    console.log('Join', inputPassword, inputCheckPassword);
    try {
      if (validateEmail(email)) {
        throw new Error(validateEmail(email));
      }
      if (validatePassword(inputCheckPassword)) {
        throw new Error(validatePassword(inputCheckPassword));
      }
      const response = await axios.get(BACKEND_URL);
      console.log(response);
    } catch (error) {
      console.log('error', error);
      if (error.message === '유효한 이메일 주소를 입력해주세요.') {
        setVisibleEmailError(true);
        emailInput.current.focus();
      }
      if (
        error.message ===
        '비밀번호는 영문자와 숫자를 포함한 8자 이상이어야 합니다.'
      ) {
        setVisiblePasswordError(true);
        passwordInput.current.focus();
      }
    }
  };

  const handleEmailAuth = async (email, e) => {
    e.preventDefault();
    // 백엔드에 이메일 인증 요청하는 메서드
    try {
      const emailValidation = validateEmail(email);
      if (!emailValidation) throw new Error(emailValidation);
      const response = await axios.get(BACKEND_URL);
      console.log(response);
    } catch (error) {
      console.log('error', error);
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
                className="focus:outline-none w-full h-full py-[21px] px-4 
                rounded-3xs border-solid border-[1.5px] border-darkgray 
                focus:border-dodgerblue focus:border-1.5 font-pretendard 
                text-base hover:brightness-95 focus:brightness-100"
                placeholder="이메일"
                ref={emailInput}
                onChange={e => {
                  setVisibleEmailError(false);
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
              flex-row items-center jimport { Link } from 'react-router-dom';
ustify-center z-[1] hover:brightness-import BACKEND_URL from './../utils/utils';
95 focus:brightness-100"
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
          <button
            type="submit"
            className={`self-stretch rounded-3xs ${
              selectedAddr && selectedSuffix ? 'bg-dodgerblue' : 'bg-white'
            }
            overflow-hidden flex flex-row py-[21px] px-[199px] 
            items-center justify-center border-[1.5px] border-solid border-darkgray hover:brightness-90`}
            onClick={e => {
              handleEmailAuth(`${selectedAddr}@${selectedSuffix}`, e);
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
          {visibleEmailError ||
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
            className="focus:outline-none self-stretch rounded-3xs bg-white flex flex-row py-5 px-4 
          items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
          focus:border-dodgerblue focus:border-1.5 font-pretendard text-base 
          hover:brightness-95 focus:brightness-100"
            ref={passwordInput}
            placeholder="비밀번호"
            onChange={e => {
              setVisiblePasswordError(false);
              setPassword(e.target.value);
            }}
          />
          {visiblePasswordError ? (
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
            className="focus:outline-none self-stretch rounded-3xs bg-white flex flex-row py-5 px-4 
          items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
          focus:border-dodgerblue focus:border-1.5 font-pretendard text-base 
          hover:brightness-95 focus:brightness-100"
            placeholder="비밀번호 확인"
            onChange={e => {
              setCheckPassword(e.target.value);
            }}
          />
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
              <img
                className="relative w-0.5 h-0.5 opacity-[0]"
                alt=""
                src="/icon.svg"
              />
            </div>
            <div className="relative leading-[150%]">
              <b>전체동의</b>
              <span className="text-[15px] text-slategray">
                선택항목에 대한 동의 포함
              </span>
            </div>
          </div>
          <img
            className="self-stretch relative max-w-full overflow-hidden h-0.5 shrink-0"
            alt=""
            src="/vector-18.svg"
          />
          <div className="flex flex-row items-center justify-start gap-[13px]">
            <div
              className="rounded-md bg-white box-border w-[22px] h-[20.75px] 
            flex flex-row items-center justify-center border-[1px] border-solid border-slategray"
            >
              <img
                className="relative w-0.5 h-0.5 opacity-[0]"
                alt=""
                src="/icon1.svg"
              />
            </div>
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
                      <img
                        className="relative w-0.5 h-0.5 opacity-[0]"
                        alt=""
                        src="/icon2.svg"
                      />
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
                  <img
                    className="relative w-0.5 h-0.5 opacity-[0]"
                    alt=""
                    src="/icon3.svg"
                  />
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
            checkForm() ? 'bg-dodgerblue' : 'bg-darkgray'
          } w-full overflow-hidden flex flex-row py-[21px] px-[216px] items-center justify-center text-white hover:brightness-95`}
          onClick={e => {
            handleJoin(
              `${selectedAddr}@${selectedSuffix}`,
              password,
              checkPassword,
              e,
            );
          }}
          disabled={!checkForm()}
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
