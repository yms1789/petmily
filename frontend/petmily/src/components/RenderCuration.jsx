import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import PetsIcon from '@mui/icons-material/Pets';
import { styled } from '@mui/material';
import { string } from 'prop-types';
import React from 'react';
import { placeholderImage } from '../utils/utils';

const placeholderCurations = Array(5).fill('');
function RenderCuration({ animal }) {
  const StyledPetsIcon = styled(PetsIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({
    width: '40px',
    height: 'auto',
  });
  const StyledArrowForwardIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({});
  return (
    <div className="flex flex-col items-start justify-start gap-[2.25rem]">
      <div className="self-stretch flex flex-row items-center justify-between">
        <div className="rounded-31xl bg-dodgerblue overflow-hidden flex flex-row py-2.5 px-5 items-center justify-start gap-[0.63rem]">
          <StyledPetsIcon className="relative w-[14px] h-auto" />
          <div className="relative tracking-[0.01em] leading-[125%] font-extrabold p-2 rounded-xl">
            {animal}
          </div>
        </div>
        <div className="rounded-31xl overflow-hidden flex flex-row py-2.5 px-5 items-center gap-[12px] text-[1.25rem] text-dodgerblue border-[2px] border-solid">
          <b className="relative tracking-[0.01em] leading-[125%] text-dodgerblue">
            더보기
          </b>
          <StyledArrowForwardIosRoundedIcon className="text-dodgerblue" />
        </div>
      </div>
      <div className="w-[1840px] flex flex-row items-start justify-start gap-[24.96px] text-[1rem] text-gray">
        {placeholderCurations.map(() => {
          return (
            <div className="flex-1 rounded-11xl bg-white overflow-hidden flex flex-col pt-0 px-0 pb-6 items-center justify-center gap-[16px]">
              <img
                className="relative overflow-hidden shrink-0 object-cover"
                alt=""
                src={placeholderImage}
              />
              <div className="flex flex-col items-start justify-center gap-[16px]">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <div className="relative tracking-[0.01em] leading-[125%] font-medium flex items-start w-[540px] shrink-0">
                    {animal} 유산균 급여 시 장점과 구매 전 체크리스트
                    블라블라블라
                  </div>
                </div>
                <div className="relative text-[0.88rem] tracking-[0.01em] leading-[125%] text-darkgray flex items-center w-[320px]">
                  {animal} 유산균 급여 시 장점과 구매 전 체크리스트는요
                  블라블라블라...
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
RenderCuration.propTypes = {
  animal: string,
};
export default RenderCuration;
