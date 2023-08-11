import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import userAtom from 'states/users';
import useFetch from 'utils/fetch';

function Inventory() {
  const inventoryFetch = useFetch();
  const [user, setUser] = useRecoilState(userAtom);
  const { userEmail, userBackground, userBadge, userRing } = user;
  const [equipments, setEquipments] = useState({});
  useEffect(() => {
    async function fetchInventoryData() {
      try {
        const inventoryData = await inventoryFetch.get(
          `item/inventory?userEmail=${userEmail}`,
        );
        console.log(inventoryData);
        setEquipments(inventoryData);
      } catch (error) {
        console.log(error);
      }
    }
    fetchInventoryData();
  }, []);

  const handleElementClick = async (elementId, itemType) => {
    try {
      const response = await inventoryFetch.put('item/equipment', {
        userEmail,
        itemId: elementId,
        itemType,
      });
      console.log(response);
      if (itemType === 'ring') setUser({ ...user, userRing: response });
      if (itemType === 'badge') setUser({ ...user, userBadge: response });
      if (itemType === 'background')
        setUser({ ...user, userBackground: response });
    } catch (error) {
      console.log(error);
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
        {equipments.ring ? (
          equipments.ring.map(ele => {
            return (
              <div
                key={ele.itemName}
                className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]"
              >
                <div className="self-stretch flex flex-row items-start justify-between">
                  <div className="flex flex-row items-center justify-center gap-[12px]">
                    <div
                      className={`relative w-[30px] h-[30px] object-cover border-solid border-2 rounded-[100px] border-${ele.element}`}
                    />
                    <div className="relative font-semibold">{ele.itemName}</div>
                  </div>
                  {!(userRing.itemId === ele.itemId) ? (
                    <div
                      role="presentation"
                      className="bg-dodgerblue hover:brightness-90 h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-white rounded-xl cursor-pointer"
                      onClick={() => {
                        handleElementClick(ele.itemId, 'ring');
                      }}
                    >
                      <b className="relative tracking-[0.05em] leading-[19px]">
                        장착
                      </b>
                    </div>
                  ) : (
                    <div
                      role="presentation"
                      className="bg-white hover:brightness-90 h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-dodgerblue rounded-xl cursor-pointer border-solid border-2 border-dodgerblue"
                      onClick={() => {
                        handleElementClick(null, 'ring');
                      }}
                    >
                      <b className="relative tracking-[0.05em] leading-[19px]">
                        해제
                      </b>
                    </div>
                  )}
                </div>
              </div>
            );
          })
        ) : (
          <div className="px-5">보유한 링이 없습니다.</div>
        )}
      </div>
      <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
        <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
          <div className="relative font-semibold">뱃지</div>
          <div className="self-stretch relative bg-whitesmoke-200 h-px" />
        </div>
        {equipments?.badge ? (
          equipments?.badge.map(ele => {
            return (
              <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
                <div className="self-stretch flex flex-row items-start justify-between">
                  <div className="flex flex-row items-center justify-center gap-[12px]">
                    <div
                      className={`relative w-[30px] h-[30px] object-cover border-solid border-2 rounded-[100px] border-${ele.element}`}
                    />
                    <div className="relative font-semibold">{ele.itemName}</div>
                  </div>
                  {!(userBadge.itemId === ele.itemId) ? (
                    <div
                      role="presentation"
                      className="bg-dodgerblue hover:brightness-90 h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-white rounded-xl cursor-pointer"
                      onClick={() => {
                        handleElementClick(ele.itemId, 'badge');
                      }}
                    >
                      <b className="relative tracking-[0.05em] leading-[19px]">
                        장착
                      </b>
                    </div>
                  ) : (
                    <div
                      role="presentation"
                      className="bg-white hover:brightness-90 h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-dodgerblue rounded-xl cursor-pointer border-solid border-2 border-dodgerblue"
                      onClick={() => {
                        handleElementClick(null, 'badge');
                      }}
                    >
                      <b className="relative tracking-[0.05em] leading-[19px]">
                        해제
                      </b>
                    </div>
                  )}
                </div>
              </div>
            );
          })
        ) : (
          <div className="px-5">보유한 뱃지가 없습니다.</div>
        )}
      </div>
      <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
        <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
          <div className="relative font-semibold">커버 이미지</div>
          <div className="self-stretch relative bg-whitesmoke-200 h-px" />
        </div>
        {equipments.background ? (
          equipments?.background.map(ele => {
            return (
              <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
                <div className="self-stretch flex flex-row items-start justify-between">
                  <div className="flex flex-row items-center justify-center gap-[12px]">
                    <div
                      className={`relative w-[30px] h-[30px] object-cover border-solid border-2 rounded-[100px] border-${ele.element}`}
                    />
                    <div className="relative font-semibold">{ele.itemName}</div>
                  </div>
                  {!(userBackground.itemId === ele.itemId) ? (
                    <div
                      role="presentation"
                      className="bg-dodgerblue hover:brightness-90 h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-white rounded-xl cursor-pointer"
                      onClick={() => {
                        handleElementClick(ele.itemId, 'background');
                      }}
                    >
                      <b className="relative tracking-[0.05em] leading-[19px]">
                        장착
                      </b>
                    </div>
                  ) : (
                    <div
                      role="presentation"
                      className="bg-white hover:brightness-90 h-[30px] overflow-hidden flex flex-row py-[15px] px-5 box-border items-center justify-center text-center text-mini text-dodgerblue rounded-xl cursor-pointer border-solid border-2 border-dodgerblue"
                      onClick={() => {
                        handleElementClick(null, 'background');
                      }}
                    >
                      <b className="relative tracking-[0.05em] leading-[19px]">
                        해제
                      </b>
                    </div>
                  )}
                </div>
              </div>
            );
          })
        ) : (
          <div className="px-5">보유한 커버이미지가 없습니다.</div>
        )}
      </div>
    </div>
  );
}
export default Inventory;
