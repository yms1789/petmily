// import { useCallback } from 'react';

import RenderCuration from '../components/RenderCuration';
import SearchBar from '../components/SearchBar';

function Curation() {
  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <SearchBar />
      <div className="relative top-40 tracking-[0.05em] leading-[125%] font-semibold text-black text-[1.75rem]">
        HOT TOPIC
      </div>
      <div className="min-w-[1340px] max-w-full relative top-60 text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <RenderCuration animal="강아지" />
          <RenderCuration animal="고양이" />
          <RenderCuration animal="기타동물" />
        </div>
      </div>
    </div>
  );
}

export default Curation;
