import MaleRoundedIcon from '@mui/icons-material/MaleRounded';
import { useNavigate } from 'react-router';
import { styled } from '@mui/material';

import { placeholderImage } from '../utils/utils';

const sampleTexts = Array.from({ length: 5 }, (_, i) => i);

function MyPetInfo() {
  const StyledMaleRoundedIcon = styled(MaleRoundedIcon, {
    name: 'StyledMaleRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#1f90fe',
  });
  const navigate = useNavigate();
  const toModifyPetInfo = () => {
    navigate('/petinfo/edit');
  };
  return (
    <div className="rounded-11xl bg-white min-w-[20%] h-[768px] flex flex-col basis-1/4 p-[1rem] box-border items-start justify-start gap-[0.75rem] text-[1.25rem]">
      <div className="self-stretch relative h-[46px]">
        <div className="relative top-[4px] left-[16px] font-semibold">
          내 반려동물
        </div>
        <div className="absolute top-[45px] left-[0px] bg-dark-7 w-[447px] h-px" />
      </div>
      <div className="relative w-full text-[1.31rem]">
        <div className="relative bg-white w-full h-[200px]">
          <img
            className="absolute w-full h-[200px] object-cover"
            alt=""
            src={placeholderImage(Math.floor(Math.random()) * 101)}
          />
        </div>
        <div className="absolute top-60 left-4 flex flex-row w-full items-end">
          <div className="absolute rounded-[100px] box-border w-[98px] h-[95px] overflow-hidden border-[4px] border-solid border-gray">
            <div className="absolute top-[0px] rounded-[100px] w-[90px] h-[90px] overflow-hidden">
              <img
                className="absolute w-full h-full object-cover"
                alt=""
                src={placeholderImage(Math.floor(Math.random()) * 101)}
              />
            </div>
          </div>
          <div
            role="presentation"
            onClick={toModifyPetInfo}
            className="absolute right-4 top-[0.5px] rounded-[100px] box-border w-28 h-[39px] overflow-hidden flex flex-row py-[0.94rem] px-[0.19rem] items-center justify-center text-center text-[0.94rem] text-dodgerblue border-[1px] border-solid border-dodgerblue"
          >
            <b className="flex-1 relative leading-[1.19rem]">정보 수정</b>
          </div>
        </div>
      </div>
      <div className="relative mt-16 flex flex-col items-start justify-start gap-[0.31rem]">
        <b className="relative tracking-[-0.01em] z-[0]">망고</b>
        <div className="flex flex-row items-center gap-1 relative text-[1rem] tracking-[-0.01em] font-medium w-20 z-[1]">
          {`2살 `}
          <StyledMaleRoundedIcon />
        </div>
      </div>
      <div className="rounded-11xl bg-white w-full flex flex-col py-[0.31rem] px-[0.94rem] box-border items-start justify-start gap-[0.94rem] text-[0.94rem]">
        <div className="self-stretch flex flex-col items-start justify-start gap-[0.38rem] text-[0.88rem] text-slategray">
          <b className="relative text-[0.94rem] tracking-[0.02em] text-gray">
            특이사항
          </b>
          <div className="flex flex-row items-start justify-start">
            <div className="relative tracking-[-0.02em] font-medium">
              배고프면 신경질 적임
            </div>
          </div>
          <div className="flex flex-row items-start justify-start">
            <div className="relative tracking-[-0.02em] font-medium">
              주인(여민수) 닮아서 혼자 드립치고 혼자 웃음
            </div>
          </div>
        </div>
        {sampleTexts.map(ele => {
          return (
            <div
              key={ele}
              className="flex flex-col items-start justify-start gap-[0.38rem]"
            >
              <b className="relative tracking-[0.02em] inline-block w-[235px]">
                다른 글 {ele + 2}
              </b>
              <div className="flex flex-row items-start justify-start text-[0.88rem] text-slategray">
                <div className="relative tracking-[-0.02em] font-medium">
                  다른 글 {ele + 2}의 내용
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default MyPetInfo;
