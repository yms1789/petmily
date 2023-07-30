import RenderCuration from 'components/RenderCuration';
import { placeholderImage } from 'utils/utils';

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
          <RenderCuration category="강아지" />
          <RenderCuration category="고양이" />
          <RenderCuration category="기타동물" />
        </div>
      </div>
    </div>
  );
}

export default Curation;
