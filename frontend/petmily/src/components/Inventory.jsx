import { placeholderImage } from 'utils/utils';

const sampleEquipments = {
  borders: [
    {
      id: 0,
      name: 'border1',
      element: 'dodgerblue',
    },
    {
      id: 1,
      name: 'border2',
      element: 'red',
    },
  ],
  badges: [
    {
      id: 0,
      name: 'badge1',
      element: placeholderImage(Math.floor(Math.random() * 1001) + 1),
    },
    {
      id: 1,
      name: 'badge2',
      element: placeholderImage(Math.floor(Math.random() * 1001) + 1),
    },
  ],
  cover: [
    {
      id: 0,
      name: 'cover1',
      element: placeholderImage(Math.floor(Math.random() * 1001) + 1),
    },
    {
      id: 1,
      name: 'cover2',
      element: placeholderImage(Math.floor(Math.random() * 1001) + 1),
    },
  ],
};

function Inventory() {
  const handleElementClick = buttonType => {
    if (buttonType === '장착') {
      console.log(buttonType);
    }
    if (buttonType === '해제') {
      console.log(buttonType);
    }
  };
  return (
    <div className="rounded-11xl bg-white min-w-[350px] basis-1/4 h-fit overflow-hidden shrink-0 flex flex-col py-4 px-0 box-border items-start justify-start gap-[12px] text-lg">
      <div className="self-stretch flex flex-col py-0 px-4 items-start justify-start gap-[17px] text-xl">
        <div className="relative font-semibold">내 보관함</div>
        <div className="relative bg-whitesmoke-200 w-[447px] h-px" />
      </div>
      <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
        <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
          <div className="relative font-semibold">프로필 링</div>
          <div className="self-stretch relative bg-whitesmoke-200 h-px" />
        </div>
        {sampleEquipments.borders.map(ele => {
          return (
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <div
                    className={`relative w-[30px] h-[30px] object-cover border-solid border-2 rounded-[100px] border-${ele.element}`}
                  />
                  <div className="relative font-semibold">{ele.name}</div>
                </div>
                <div
                  role="presentation"
                  className="bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-white rounded-xl cursor-pointer"
                  onClick={() => {
                    handleElementClick('장착');
                  }}
                >
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
        <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
          <div className="relative font-semibold">뱃지</div>
          <div className="self-stretch relative bg-whitesmoke-200 h-px" />
        </div>
        {sampleEquipments.badges.map(ele => {
          return (
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    src={ele.element}
                    alt=""
                    className="`relative w-[30px] h-[30px] rounded-xl object-cover"
                  />
                  <div className="relative font-semibold">{ele.name}</div>
                </div>
                <div
                  role="presentation"
                  className="box-border h-[30px] overflow-hidden flex flex-row py-[15px] px-[1.1rem] items-center justify-center text-center text-mini text-dodgerblue border-[2px]
                 border-solid border-dodgerblue rounded-xl cursor-pointer"
                  onClick={() => {
                    handleElementClick('해제');
                  }}
                >
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    해제
                  </b>
                </div>
              </div>
            </div>
          );
        })}
      </div>
      <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
        <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
          <div className="relative font-semibold">커버 이미지</div>
          <div className="self-stretch relative bg-whitesmoke-200 h-px" />
        </div>
        {sampleEquipments.cover.map(ele => {
          return (
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    src={ele.element}
                    alt=""
                    className="`relative w-[30px] h-[30px] rounded-md object-cover"
                  />
                  <div className="relative font-semibold">{ele.name}</div>
                </div>
                <div
                  role="presentation"
                  className="bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-white rounded-xl cursor-pointer"
                  onClick={() => {
                    handleElementClick('장착');
                  }}
                >
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}
export default Inventory;
