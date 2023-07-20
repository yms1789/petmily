import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import ArrowDropDownOutlinedIcon from '@mui/icons-material/ArrowDropDownOutlined';
import { styled } from '@mui/material';
import { useRef, useState } from 'react';

function Join() {
  const StyledArrowForwardIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#a6a7ab',
    '&:hover': { color: '#1f90fe' },
  });
  const StyledArrowDropDownOutlinedIcon = styled(ArrowDropDownOutlinedIcon, {
    name: 'StyledArrowDropDownOutlinedIcon',
    slot: 'Wrapper',
  })({
    color: '#a6a7ab',
    fontSize: 40,
    '&:hover': { color: '#1f90fe' },
  });
  const emailRef = useRef(null);
  const [selectedAddr, setSelectedAddr] = useState('');

  return (
    <div className="joinComponent">
      <div className="absolute top-[142px] left-[calc(50%_-_324px)] rounded-[20px] bg-white h-[1208.41px] flex flex-col p-10 box-border items-center justify-start gap-[42px]">
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
                className="focus:outline-none w-full h-full py-5 px-4 
                rounded-3xs border-solid border-[1.5px] border-darkgray 
                focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
                placeholder="이메일"
              />
            </div>
            <b className="relative text-13xl tracking-[0.01em] leading-[125%]">
              @
            </b>
            <div className="flex-1 rounded-3xs flex flex-col items-start justify-start relative gap-[10px]">
              <div className="relative rounded-3xs bg-white box-border w-[254px] h-[60.07px] z-[0] border-[1.5px] border-solid border-darkgray focus:outline-none" />
              <div className="my-0 mx-[!important] w-full h-full absolute flex flex-row items-center justify-center z-[1]">
                <div className="absolute inset-y-0 right-0 flex items-center pr-2 pointer-events-none z-10">
                  <StyledArrowDropDownOutlinedIcon className="w-5 h-5 text-gray-400" />
                </div>
                {selectedAddr === '이메일주소 직접입력' ? (
                  <input
                    type="text"
                    className="focus:outline-none w-full h-full py-5 px-4 
                  rounded-3xs border-solid border-[1.5px] border-darkgray 
                  focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
                    placeholder="이메일"
                  />
                ) : (
                  <select
                    className="appearance-none px-4 py-1 pr-8 text-base font-pretendard-medium rounded-lg 
                focus:outline-none border-solid border-[1.5px] border-darkgray focus:border-dodgerblue focus:border-1.5"
                    value={selectedAddr}
                    onChange={e => {
                      setSelectedAddr(e.target.value);
                    }}
                  >
                    <option value="">이메일주소 직접입력</option>
                    <option value="naver.com">naver.com</option>
                    <option value="nate.com">nate.com</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="yahoo.com">yahoo.com</option>
                    <option value="hanmail.net">hanmail.net</option>
                  </select>
                )}
              </div>
            </div>
          </div>
          <div
            className={`self-stretch rounded-3xs bg-${
              emailRef ? 'dodgerblue' : 'white'
            }overflow-hidden flex flex-row py-[21px] px-[199px] items-center justify-center border-[1.5px] border-solid border-darkgray`}
          >
            <b className="relative tracking-[0.01em] leading-[125%]">
              이메일 인증하기
            </b>
          </div>
        </div>
        <div className="w-[564px] flex flex-col items-start justify-start gap-[11px] text-xl">
          <b className="relative text-5xl tracking-[0.01em] leading-[125%]">
            비밀번호
          </b>
          <div className="relative tracking-[0.01em] leading-[125%] font-medium text-lightslategray">
            영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.
          </div>
          <div className="self-stretch rounded-3xs bg-white overflow-hidden flex flex-row py-5 px-4 items-center justify-start text-darkgray border-[1.5px] border-solid border-darkgray">
            <div className="relative tracking-[0.01em] leading-[125%]">
              비밀번호
            </div>
          </div>
        </div>
        <div className="w-[564px] flex flex-col items-start justify-start gap-[12px]">
          <b className="relative tracking-[0.01em] leading-[125%]">
            비밀번호 확인
          </b>
          <div className="self-stretch relative h-[61px] text-center text-xl text-darkgray">
            <div className="absolute top-[0px] left-[0px] rounded-3xs bg-white box-border w-[564px] overflow-hidden flex flex-row py-[18px] px-4 items-center justify-start border-[1.5px] border-solid border-darkgray">
              <div className="relative tracking-[0.01em] leading-[125%]">
                비밀번호 확인
              </div>
            </div>
          </div>
        </div>
        <div className="self-stretch flex-1 flex flex-col items-start justify-start gap-[13px] text-xl">
          <b className="relative text-5xl tracking-[0.01em] leading-[125%] flex items-center w-[84px] h-[28.29px] shrink-0">
            약관동의
          </b>
          <div className="flex flex-row items-center justify-start gap-[13px]">
            <div className="rounded-md bg-white box-border w-[22px] h-[20.75px] flex flex-row items-center justify-center border-[1px] border-solid border-slategray">
              <img
                className="relative w-0.5 h-0.5 opacity-[0]"
                alt=""
                src="/icon.svg"
              />
            </div>
            <div className="relative leading-[150%]">
              <b>전체동의</b>
              <span className="text-base text-slategray">
                {' '}
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
            <div className="rounded-md bg-white box-border w-[22px] h-[20.75px] flex flex-row items-center justify-center border-[1px] border-solid border-slategray">
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
                    <div className="rounded-md bg-white box-border w-[22px] h-[20.75px] flex flex-row items-center justify-center border-[1px] border-solid border-slategray">
                      <img
                        className="relative w-0.5 h-0.5 opacity-[0]"
                        alt=""
                        src="/icon2.svg"
                      />
                    </div>
                    <div className="relative leading-[150%] inline-block w-[113px] h-[28.29px] shrink-0">
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
                <div className="rounded-md bg-white box-border w-[22px] h-[20.75px] flex flex-row items-center justify-center border-[1px] border-solid border-slategray">
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
        <div className="rounded-[50px] bg-dodgerblue overflow-hidden flex flex-row py-[21px] px-[216px] items-center justify-center text-white">
          <b className="relative tracking-[0.01em] leading-[125%]">
            회원가입하기
          </b>
        </div>
        <div className="self-stretch flex flex-row items-start justify-center gap-[30px] text-xl">
          <div className="relative tracking-[0.01em] leading-[125%]">
            이미 아이디가 있으신가요?
          </div>
          <b className="relative [text-decoration:underline] tracking-[0.01em] leading-[125%] text-dodgerblue">
            로그인
          </b>
        </div>
      </div>
    </div>
  );
}

export default Join;
