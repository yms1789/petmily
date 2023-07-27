// import { useCallback } from 'react';

import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import PetsIcon from '@mui/icons-material/Pets';
import { styled } from '@mui/material';
import { useState, useRef } from 'react';
import RenderCuration from '../components/RenderCuration';
import SearchBar from '../components/SearchBar';
import { placeholderImage } from '../utils/utils';

function Curation() {
  const StyledPetsIcon = styled(PetsIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({
    width: '30px',
    height: 'auto',
  });

  const StyledArrowDownIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowDownIosRoundedIcon',
    slot: 'Wrapper',
  })({
    transform: 'rotate(90deg)',
  });

  const [selection, setSelection] = useState({
    category: '',
    sort: '',
  });

  const selectCategoryRef = useRef(null);
  const selectSortRef = useRef(null);

  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] max-w-full relative text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <img
            className="relative w-full h-[200px]"
            alt=""
            src={placeholderImage}
          />
          <div className="flex flex-row justify-around items-center w-full h-auto mt-20 mb-20">
            <div className="relative flex gap-5 justify-start flex-row w-full h-auto">
              <div
                className="relative rounded-31xl bg-dodgerblue overflow-hidden flex flex-row 
              py-[0.7rem] px-[1.3rem] items-center justify-end gap-[0.75rem] cursor-pointer"
              >
                <StyledPetsIcon className="absolute left-3" />
                <select
                  ref={selectCategoryRef}
                  className="bg-transparent w-[120px] ml-6 text-white appearance-none relative tracking-[0.01em] leading-[125%] font-extrabold text-xl"
                  value={selection.category}
                  onChange={e => {
                    setSelection({ ...selection, category: e.target.value });
                  }}
                >
                  <option value="" disabled selected className="text-black">
                    카테고리
                  </option>
                  <option value="강아지" className="text-black">
                    강아지
                  </option>
                  <option value="고양이" className="text-black">
                    고양이
                  </option>
                  <option value="기타동물" className="text-black">
                    기타동물
                  </option>
                </select>
                <StyledArrowDownIosRoundedIcon className="absolute right-4" />
              </div>
              <div
                className="relative top-[0px] rounded-31xl bg-dodgerblue overflow-hidden 
              flex flex-row py-[0.7rem] px-[1.3rem] items-center justify-end gap-[0.75rem] cursor-pointer"
              >
                <StyledPetsIcon className="" />
                <select
                  ref={selectSortRef}
                  className="bg-transparent w-[80px] mx-1 text-white appearance-none relative tracking-[0.01em] leading-[125%] font-extrabold text-xl focus:outline-none focus:border-blue"
                  value={selection.sort}
                  onChange={e => {
                    setSelection({ ...selection, sort: e.target.value });
                  }}
                >
                  <option value="" disabled selected className="text-black">
                    정렬
                  </option>
                  <option value="강아지" className="text-black">
                    이름순
                  </option>
                  <option value="고양이" className="text-black">
                    날짜순
                  </option>
                  {/* <option value="기타동물">기타동물</option> */}
                </select>
                <StyledArrowDownIosRoundedIcon className="absolute" />
              </div>
            </div>
            <SearchBar page="큐레이션" />
          </div>
          <RenderCuration category="인기" />
          <div className="h-40" />
          <RenderCuration category="건강" />
          <RenderCuration category="미용" />
          <RenderCuration category="식품" />
        </div>
      </div>
    </div>
  );
}

export default Curation;
