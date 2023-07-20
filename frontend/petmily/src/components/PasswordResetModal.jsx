function PasswordResetModal() {
  return (
    <div className="relative rounded-[10px] bg-white w-[626px] h-[377px] max-w-full max-h-full overflow-auto text-left text-xl text-darkgray font-pretendard">
      <div className="absolute top-[27px] left-[29px] flex flex-col items-start justify-start gap-[23px]">
        <div className="relative tracking-[0.01em] leading-[125%] text-gray">
          가입한 이메일 주소를 입력해주세요.
        </div>
        <div className="rounded-8xs bg-white box-border w-[564px] overflow-hidden flex flex-row py-2 px-4 items-center justify-start gap-[347px] border-[2px] border-solid border-whitesmoke-200">
          <div className="relative tracking-[0.01em] leading-[125%] flex items-center w-[70px] shrink-0">
            이메일
          </div>
          <div className="flex-1 rounded-xl bg-dodgerblue flex flex-col items-center justify-start relative text-5xl text-white">
            <b className="absolute my-0 mx-[!important] top-[14.43px] left-[calc(50%_-_22.5px)] tracking-[0.01em] leading-[125%] flex items-center w-[45.03px] h-[20.62px] shrink-0 z-[0]">
              확인
            </b>
          </div>
        </div>
        <div className="rounded-8xs bg-white overflow-hidden flex flex-row py-2 px-4 items-center justify-start gap-[347px] border-[2px] border-solid border-whitesmoke-200">
          <div className="relative tracking-[0.01em] leading-[125%]">
            인증코드
          </div>
          <div className="flex-1 rounded-xl bg-dodgerblue flex flex-col items-center justify-start relative text-5xl text-white">
            <b className="absolute my-0 mx-[!important] top-[14.43px] left-[calc(50%_-_22.5px)] tracking-[0.01em] leading-[125%] flex items-center w-[45.03px] h-[20.62px] shrink-0 z-[0]">
              인증
            </b>
          </div>
        </div>
        <div className="self-stretch rounded-31xl bg-dodgerblue overflow-hidden flex flex-row py-[21px] px-[172px] items-center justify-center text-5xl text-white">
          <b className="relative tracking-[0.01em] leading-[125%]">
            비밀번호 재설정
          </b>
        </div>
      </div>
    </div>
  );
}

export default PasswordResetModal;
