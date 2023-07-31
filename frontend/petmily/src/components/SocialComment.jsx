import { placeholderImage } from 'utils/utils';
import SocialRecomment from './SocialRecomment';

function SocialComment() {
  return (
    <div>
      <span className="mt-1 h-[0.06rem] w-full bg-gray2 inline-block" />
      <div className="flex items-start my-1">
        <div className="w-[2.5rem] h-[2.5rem] mt-1">
          <img
            className="w-[2.5rem] h-[2.5rem] object-cover rounded-full overflow-hidden"
            alt=""
            src={placeholderImage}
          />
        </div>
        <div className="flex flex-col gap-[0.4rem] mx-4 w-full">
          <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
            <b className="relative text-gray">Devon Lane</b>
            <div className="relative font-medium">@johndue</div>
          </div>
          <div className="flex justify-between w-full font-pretendard text-base">
            <div>우리집 강아지 커여웡</div>
            <div className="text-slategray font-medium">{`23s `}</div>
          </div>
          <SocialRecomment />
          <SocialRecomment />
        </div>
      </div>
    </div>
  );
}

export default SocialComment;
