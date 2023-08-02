import { placeholderImage } from 'utils/utils';

function SocialRecomment() {
  return (
    <div>
      <span className="h-[0.06rem] w-full bg-gray2 inline-block" />
      <div className="flex items-center justify-center">
        <div className="w-[2.5rem] h-[2.5rem] flex items-center justify-center mt-2">
          <img
            className="w-[2.5rem] h-[2.5rem] object-cover rounded-full overflow-hidden"
            alt=""
            src={placeholderImage}
          />
        </div>
        <div className="flex flex-col mt-2 gap-[0.4rem] ml-4 w-full">
          <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
            <b className="text-gray">Devon Lane</b>
            <div className="font-medium">@johndue</div>
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
