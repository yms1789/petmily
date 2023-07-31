import { placeholderImage } from '../utils/utils';

function SocialRecomment() {
  return (
    <div>
      <span className="mt-2 h-[0.06rem] w-full bg-gray2 inline-block" />
      <div className="flex items-center my-1">
        <div className="w-[2.5rem] h-[2.5rem]">
          <img
            className="w-[2.5rem] h-[2.5rem] object-cover rounded-full overflow-hidden"
            alt=""
            src={placeholderImage}
          />
        </div>
        <div className="flex flex-col gap-[0.4rem] ml-4 w-full">
          <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
            <b className="relative text-gray">Devon Lane</b>
            <div className="relative font-medium">@johndue</div>
          </div>
          <div className="flex justify-between w-full font-pretendard text-base">
            <div>우리집 강아지 커여웡</div>
            <div className="text-slategray font-medium">{`23s `}</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SocialRecomment;
