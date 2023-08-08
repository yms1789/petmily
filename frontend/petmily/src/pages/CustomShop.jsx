// import { useEffect } from 'react';
import { useState, useCallback } from 'react';
// import { useRecoilValue } from 'recoil';
// import { useNavigate } from 'react-router-dom';

import StorefrontIcon from '@mui/icons-material/Storefront';
// import authAtom from 'states/auth';
import {
  GachaComponent,
  PointLog,
  Inventory,
  PortalPopup,
  GachaModal,
  GachaLoadingModal,
} from 'components';
import { ReactComponent as StarCoin } from 'static/images/starCoin.svg';

// import useFetch from 'utils/fetch';

const items = [
  [
    { itemTitle: 'ALL', price: 10 },
    { itemTitle: '프로필 링', price: 20 },
  ],
  [
    { itemTitle: '뱃지', price: 20 },
    { itemTitle: '커버 이미지', price: 30 },
  ],
];
const logs = [
  {
    type: true,
    point: 10,
    message: '산책',
    date: new Date(),
  },
  {
    type: true,
    point: 10,
    message: '출첵',
    date: new Date(),
  },
  {
    type: false,
    point: 10,
    message: '고양이 게임',
    date: new Date(),
  },
];

function CustomShop() {
  // const navigate = useNavigate();
  // const auth = useRecoilValue(authAtom);
  // const fetchData = useFetch();
  // useEffect(() => {
  //   if (!auth || !Object.keys(auth).length) {
  //     navigate('/login');
  //   }
  //   async function checkAuth() {
  //     try {
  //       await fetchData.post('authenticate');
  //     } catch (error) {
  //       navigate('/login');
  //     }
  //   }
  //   checkAuth();
  // }, []);

  const [gachaLoadingModalOpen, setGachaLoadingModalOpen] = useState(false);
  const [gachaModalOpen, setGachaModalOpen] = useState(false);

  const openGachaLoadingModal = useCallback(() => {
    setGachaLoadingModalOpen(true);
  }, []);
  const closeGachaLoadingModal = useCallback(() => {
    setGachaLoadingModalOpen(false);
  }, []);
  const openGachaModal = useCallback(() => {
    setGachaModalOpen(true);
  }, []);
  const closeGachaModal = useCallback(() => {
    setGachaModalOpen(false);
  }, []);

  return (
    <div className="relative bg-whitesmoke-100 min-w-[1340px] w-full max-w-full h-fit text-left text-11xl text-dodgerblue font-pretendard">
      <div className="absolute px-9 min-w-[1340px] w-[96%] top-10 flex flex-row items-start justify-start gap-[60px] text-xl text-gray">
        <PointLog logs={logs} />
        <div className="rounded-11xl min-w-[700px] bg-dodgerblue basis-[50%] h-fit flex flex-col items-center justify-start text-11xl text-white">
          <div className="self-stretch rounded-t-11xl rounded-b-none bg-dodgerblue flex flex-col p-6 items-start justify-start gap-[32px]">
            <div className="self-stretch flex flex-row items-center justify-between">
              <div className="flex flex-row items-center justify-center">
                <div className="self-stretch flex flex-row items-center justify-center gap-[12px]">
                  <StorefrontIcon fontSize="large" />
                  <b className="relative tracking-[0.01em] leading-[125%]">
                    상점
                  </b>
                </div>
              </div>
            </div>
            <div className="w-[59px] flex flex-col items-start justify-start gap-[7px] text-center text-base">
              <div className="overflow-hidden flex flex-row py-0 px-3 items-start justify-start">
                <b className="relative leading-[19px] whitespace-nowrap">
                  내 코인
                </b>
              </div>
              <div className="flex flex-row items-center justify-center gap-[12px] text-[40px]">
                <StarCoin />
                <b className="relative leading-[19px]">100</b>
              </div>
            </div>
          </div>
          <div className="self-stretch basis-1/4 flex-1 rounded-11xl flex flex-col p-6 items-start justify-start gap-[24px] text-xl text-gray">
            <div className="self-stretch flex-1 flex flex-row items-start justify-start gap-[24px]">
              {items[0].map(ele => (
                <GachaComponent
                  itemTitle={ele.itemTitle}
                  price={ele.price}
                  modalOpen={openGachaLoadingModal}
                />
              ))}
            </div>
            <div className="self-stretch flex-1 flex flex-row items-start justify-start gap-[24px]">
              {items[1].map(ele => (
                <GachaComponent
                  itemTitle={ele.itemTitle}
                  price={ele.price}
                  modalOpen={openGachaLoadingModal}
                />
              ))}
            </div>
          </div>
        </div>
        <Inventory />
      </div>
      {gachaLoadingModalOpen && (
        <PortalPopup
          overlayColor="rgba(113, 113, 113, 0.4)"
          placement="Centered"
          onOutsideClick={closeGachaLoadingModal}
        >
          <GachaLoadingModal
            onClose={closeGachaLoadingModal}
            gachaOpen={openGachaModal}
          />
        </PortalPopup>
      )}
      {gachaModalOpen && (
        <PortalPopup
          overlayColor="rgba(113, 113, 113, 0.4)"
          placement="Centered"
          onOutsideClick={closeGachaModal}
        >
          <GachaModal onClose={closeGachaModal} />
        </PortalPopup>
      )}
    </div>
  );
}
export default CustomShop;
