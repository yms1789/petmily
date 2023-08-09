import { useState } from 'react';
import { placeholderImage } from 'utils/utils';

const tempMessages = Array.from({ length: 5 }, (_, i) => i);
function FollowRecommend() {
  const [showOnFollow, setShowOnFollow] = useState(false);

  const handleFollow = () => {
    setShowOnFollow(!showOnFollow);
  };

  return (
    <div className="mx-4 basis-1/4 flex h-[800px] rounded-xl bg-white min-w-[20%] flex-col p-[1rem] items-start justify-start gap-[0.38rem] font-pretendard">
      <div className="flex w-full flex-col items-start justify-center gap-[1rem] text-[1.25rem]">
        <div className="ml-1 font-semibold">팔로우 추천</div>
        <div className="bg-slate-200 w-full h-[1.5px]" />
      </div>
      {tempMessages.map(ele => {
        return (
          <div
            key={ele}
            className="self-stretch flex flex-col items-start justify-start gap-[0.63rem]"
          >
            <div className="w-full flex flex-row py-[0.75rem] px-[1rem] box-border items-center justify-between">
              <div className="w-fill gap-4 flex flex-row items-center justify-between">
                <div className="h-11 w-11 rounded-full overflow-hidden">
                  <img
                    className="h-11 w-11 overflow-hidden object-cover"
                    alt=""
                    src={placeholderImage(35)}
                  />
                </div>
                <div className="flex flex-col items-start justify-start gap-[0.3rem]">
                  <b className="">Bessie Cooper</b>
                  <div className="text-[1rem] font-medium text-lightgray">
                    blahblah
                  </div>
                </div>
              </div>
              <div
                role="presentation"
                className={`${
                  showOnFollow
                    ? 'bg-white font-bold'
                    : 'bg-dodgerblue text-white'
                } text-dodgerblue border-dodgerblue border-[2px] border-solid  rounded-full h-7 w-fill px-2.5 overflow-hidden whitespace-nowrap flex flex-row box-border items-center justify-center text-center`}
                onClick={handleFollow}
              >
                <p className="text-sm">팔로우</p>
              </div>
            </div>
            {ele < tempMessages.length - 1 ? (
              <div className="bg-slate-200 w-full h-[1px]" />
            ) : null}
          </div>
        );
      })}
    </div>
  );
}

export default FollowRecommend;
