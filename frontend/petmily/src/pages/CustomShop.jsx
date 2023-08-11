import { useEffect, useState, useCallback } from 'react';

import { useRecoilState, useRecoilValue } from 'recoil';
import { useNavigate } from 'react-router-dom';

import StorefrontIcon from '@mui/icons-material/Storefront';
import authAtom from 'states/auth';
import {
  GachaComponent,
  PointLog,
  Inventory,
  PortalPopup,
  GachaModal,
  GachaLoadingModal,
} from 'components';
import { ReactComponent as StarCoin } from 'static/images/starCoin.svg';
import userAtom from 'states/users';
import useFetch from 'utils/fetch';
import { gachaButtons, priceToString } from 'utils/utils';

function CustomShop() {
  const navigate = useNavigate();
  const auth = useRecoilValue(authAtom);
  const fetchData = useFetch();
  const [user, setUser] = useRecoilState(userAtom);
  const [pointLogs, setPointLogs] = useState([]);
  const [inventoryItems, setInventoryItems] = useState([]);

  useEffect(() => {
    if (!auth || !Object.keys(auth).length) {
      setUser(null);
      navigate('/login');
    }
    async function checkAuth() {
      try {
        await fetchData.post('authenticate');
      } catch (error) {
        navigate('/login');
      }
    }
    async function fetchPointLog() {
      try {
        const response = await fetchData.get(
          `usagePoint?userEmail=${user.userEmail}`,
        );
        console.log(response);
        setPointLogs(response);
      } catch (error) {
        console.log(error);
      }
    }
    async function fetchInventory() {
      try {
        const response = await fetchData.get(
          `item/inventory?userEmail=${user.userEmail}`,
        );
        console.log('inven', response);
        setInventoryItems(response);
      } catch (error) {
        console.log(error);
      }
    }
    checkAuth();
    fetchPointLog();
    fetchInventory();
  }, []);

  const [gachaLoadingModalOpen, setGachaLoadingModalOpen] = useState(false);
  const [gachaModalOpen, setGachaModalOpen] = useState(false);
  const [gachaSelect, setGachaSelect] = useState('');
  const [gachaItem, setGachaItem] = useState(null);

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
        <PointLog logs={pointLogs} />
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
                <b className="relative leading-[19px]">
                  {priceToString(user.userPoint)}
                </b>
              </div>
            </div>
          </div>
          <div className="self-stretch basis-1/4 flex-1 rounded-11xl flex flex-col p-6 items-start justify-start gap-[24px] text-xl text-gray">
            <div className="self-stretch flex-1 flex flex-row items-start justify-start gap-[24px]">
              {gachaButtons[0].map(ele => (
                <GachaComponent
                  itemTitle={ele.itemTitle}
                  price={ele.price}
                  modalOpen={openGachaLoadingModal}
                  setGachaSelect={setGachaSelect}
                />
              ))}
            </div>
            <div className="self-stretch flex-1 flex flex-row items-start justify-start gap-[24px]">
              {gachaButtons[1].map(ele => (
                <GachaComponent
                  itemTitle={ele.itemTitle}
                  price={ele.price}
                  modalOpen={openGachaLoadingModal}
                  setGachaSelect={setGachaSelect}
                />
              ))}
            </div>
          </div>
        </div>
        <Inventory inventoryItems={inventoryItems} />
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
            gachaSelect={gachaSelect}
            setGachaItem={setGachaItem}
          />
        </PortalPopup>
      )}
      {gachaModalOpen && (
        <PortalPopup
          overlayColor="rgba(113, 113, 113, 0.4)"
          placement="Centered"
          onOutsideClick={closeGachaModal}
        >
          <GachaModal onClose={closeGachaModal} gachaItem={gachaItem} />
        </PortalPopup>
      )}
    </div>
  );
}
export default CustomShop;
