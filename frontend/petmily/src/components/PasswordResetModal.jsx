import { func } from 'prop-types';

function PasswordResetModal({ onClose }) {
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
            // ref={loginEmail}
            placeholder="이메일"
            // onChange={e => {
            //   setVisibleEmailError(false);
            //   setEmail(e.target.value);
            // }}
          />
          <span className="absolute bg-dodgerblue px-5 py-3 rounded-3xs my-0 mx-[!important] right-5 text-white tracking-[0.01em] leading-[125%] flex items-center justify-center w-[45.03px] h-[20.62px] shrink-0 z-[0]">
            확인
          </span>
        </div>
        <div className="flex-1 rounded-xl w-full flex flex-row items-center justify-end relative text-5xl text-white">
          <input
            className="focus:outline-none self-stretch rounded-3xs bg-white w-full flex flex-row py-5 px-5
            items-center justify-start text-black border-[1.5px] border-solid border-darkgray 
            focus:border-dodgerblue focus:border-1.5 font-pretendard text-xl 
            hover:brightness-95 focus:brightness-100"
            // ref={loginEmail}
            placeholder="인증 코드"
            // onChange={e => {
            //   setVisibleEmailError(false);
            //   setEmail(e.target.value);
            // }}
          />
          <span className="absolute bg-dodgerblue px-5 py-3 rounded-3xs my-0 mx-[!important] right-5 text-white tracking-[0.01em] leading-[125%] flex items-center justify-center w-[45.03px] h-[20.62px] shrink-0 z-[0]">
            인증
          </span>
        </div>
        <button
          type="button"
          className="self-stretch rounded-31xl bg-dodgerblue overflow-hidden flex flex-row py-[21px] px-[172px] items-center justify-center text-5xl text-white hover:brightness-110"
          onClick={onClose}
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
