// import { useCallback } from 'react';

import RenderCuration from '../components/RenderCuration';
import SearchBar from '../components/SearchBar';
import { placeholderImage } from '../utils/utils';

const placeholderData = Array(3).fill('');
function Curation() {
  return (
    <div className="bg-whitesmoke w-[1920px] h-[210.56rem] flex flex-col items-center justify-center overflow-hidden text-left text-[1.13rem] text-darkgray font-pretendard">
      <SearchBar />
      <div className="w-[1920px] absolute top-[272px] h-[38.94rem] text-[2.38rem] text-white">
        <div className="items-stretch flex flex-row justify-around">
          {placeholderData.map(() => {
            return (
              <img
                className="relative w-[610px] h-auto overflow-hidden shrink-0 object-cover"
                alt=""
                src={placeholderImage}
              />
            );
          })}
        </div>
        <div className="absolute top-[400px] left-[-10px] w-[960px] h-full brightness-125">
          <div className="relative flex flex-row justify-center items-center bg-dodgerblue [backdrop-filter:blur(100px)] w-[960px] h-[10.56rem] rounded-tr-[100px] rounded-br-[100px]">
            <div className="tracking-[0.01em] leading-[125%] font-semibold">
              인기 강아지 장난감 브랜드명
            </div>
          </div>
          <div className="flex flex-row justify-center gap-2 items-center relative bg-dodgerblue [backdrop-filter:blur(100px)] w-[960px] h-[168.96px] rounded-tr-[100px] rounded-br-[100px]">
            <div className="tracking-[0.01em] leading-[125%] font-semibold">
              12900원
            </div>
            <div className="text-[1.5rem] tracking-[0.01em] leading-[125%] font-semibold">
              ~ 최저가
            </div>
          </div>
        </div>
      </div>
      <div className="absolute top-[1200px] tracking-[0.05em] leading-[125%] font-semibold text-black text-[1.75rem]">
        HOT TOPIC
      </div>
      <div className="absolute top-[1500px] text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <RenderCuration animal="강아지" />
          <RenderCuration animal="고양이" />
          <RenderCuration animal="기타동물" />
        </div>
      </div>
      <div className="absolute top-[176px] left-[calc(50%_-_326px)] rounded-11xl bg-white w-[40.63rem] h-[3.75rem] overflow-hidden hidden">
        <img
          className="absolute top-[calc(50%_-_15px)] left-[596px] w-[1.88rem] h-[1.88rem] overflow-hidden"
          alt=""
          src="/search.svg"
        />
        <b className="absolute top-[calc(50%_-_11px)] left-[29px] tracking-[0.01em] leading-[125%]">
          검색어를 입력하세요
        </b>
      </div>
    </div>
  );
}

export default Curation;
