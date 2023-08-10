import { placeholderImage } from 'utils/utils';

function Alarm() {
  return (
    <div className="font-pretendard text-base p-5 flex flex-col gap-4 w-[21rem] h-[30rem] rounded-xl bg-dodgerblue absolute top-[105px] right-[220px]">
      <div className="text-xl text-white">알림</div>
      <div className="py-4 flex justify-start items-start gap-4 bg-white rounded-xl w-full h-fill">
        <div className="pl-4">
          <img
            className="h-[2.5rem] w-[2.5rem] rounded-full object-cover"
            src={placeholderImage(8)}
            alt=""
          />
        </div>
        <div className="pr-4 whitespace-wrap text-sm break-all flex flex-col gap-2">
          <div className="flex whitespace-nowrap gap-1 items-center">
            <div className="text-black font-semibold">닉네임</div>
            <div className="text-darkgray font-medium">
              님이 보낸 알림이 있습니다.
            </div>
          </div>
          <div className="flex whitespace-wrap gap-1 items-center">
            <div className="text-slategray font-semibold">게시물 내용</div>
            <div className="text-darkgray font-medium">
              글에 새로운 댓글이 있습니다.sdfsdfsdfsdsdsdfsfsdf
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Alarm;
