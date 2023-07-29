// import { useCallback } from 'react';

import RenderCuration from '../components/RenderCuration';
// import SearchBar from '../components/SearchBar';
import { placeholderImage } from '../utils/utils';
// import CustomSelect from '../components/CustomSelect';

function Curation() {
  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] max-w-full relative text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <img
            className="relative w-full h-[200px]"
            alt=""
            src={placeholderImage}
          />
          <div className="h-20" />
          <b className="text-center self-stretch relative text-13xl tracking-[0.01em] leading-[125%] text-black">
            HOT TOPIC
          </b>
          <div className="h-10" />
          {/* <div className="flex flex-row justify-around items-center w-full h-auto mt-20 mb-20">
            <div className="relative flex gap-5 justify-start flex-row w-full h-auto">
              <CustomSelect
                select="카테고리"
                options={['강아지', '고양이', '기타동물']}
              />
              <CustomSelect select="정렬" options={['이름순', '최신순']} />
            </div>
            <SearchBar page="큐레이션" />
          </div>
          <RenderCuration category="인기" /> */}
          <RenderCuration category="강아지" />
          <RenderCuration category="고양이" />
          <RenderCuration category="기타동물" />
        </div>
      </div>
    </div>
  );
}

export default Curation;
