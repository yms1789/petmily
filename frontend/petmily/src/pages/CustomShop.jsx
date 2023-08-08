// import { useEffect } from 'react';
// import { useRecoilValue } from 'recoil';
// import { useNavigate } from 'react-router-dom';

import StorefrontIcon from '@mui/icons-material/Storefront';
// import authAtom from 'states/auth';
import { ReactComponent as StarCoin } from 'static/images/starCoin.svg';
import { GatchaComponent, PointLog } from 'components';
// import useFetch from 'utils/fetch';

const items = [
  [
    { itemTitle: '프로필 링', price: 5 },
    { itemTitle: '뱃지', price: 10 },
  ],
  [
    { itemTitle: '커버 이미지', price: 10 },
    { itemTitle: '프로필 링', price: 50 },
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

  return (
    <div className="relative bg-whitesmoke-100 min-w-[1340px] w-full max-w-full h-[1080px] text-left text-11xl text-dodgerblue font-pretendard">
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
                <GatchaComponent itemTitle={ele.itemTitle} price={ele.price} />
              ))}
            </div>
            <div className="self-stretch flex-1 flex flex-row items-start justify-start gap-[24px]">
              {items[1].map(ele => (
                <GatchaComponent itemTitle={ele.itemTitle} price={ele.price} />
              ))}
            </div>
          </div>
        </div>
        <div className="rounded-11xl bg-white min-w-[350px] basis-1/4 h-[768px] overflow-hidden shrink-0 flex flex-col py-4 px-0 box-border items-start justify-start gap-[12px] text-lg">
          <div className="self-stretch flex flex-col py-0 px-4 items-start justify-start gap-[17px] text-xl">
            <div className="relative font-semibold">내 보관함</div>
            <div className="relative bg-whitesmoke-200 w-[447px] h-px" />
          </div>
          <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
              <div className="relative font-semibold">프로필 링</div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    className="relative w-[30px] h-[30px] object-cover"
                    alt=""
                    src="/ripple@2x.png"
                  />
                  <div className="relative font-semibold">PURPLE</div>
                </div>
                <div className="rounded-9980xl bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-3 box-border items-center justify-center text-center text-mini text-white">
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    className="relative w-[30px] h-[30px] object-cover"
                    alt=""
                    src="/ripple1@2x.png"
                  />
                  <div className="relative font-semibold">PURPLE</div>
                </div>
                <div className="rounded-9980xl bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-3 box-border items-center justify-center text-center text-mini text-white">
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
          </div>
          <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
              <div className="relative font-semibold">뱃지</div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    className="relative w-[30px] h-[30px] object-cover"
                    alt=""
                    src="/ripple2@2x.png"
                  />
                  <div className="relative font-semibold">PURPLE</div>
                </div>
                <div className="rounded-9980xl box-border h-[30px] overflow-hidden flex flex-row py-[15px] px-3 items-center justify-center text-center text-mini text-dodgerblue border-[2px] border-solid border-dodgerblue">
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    해제
                  </b>
                </div>
              </div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    className="relative w-[30px] h-[30px] object-cover"
                    alt=""
                    src="/ripple3@2x.png"
                  />
                  <div className="relative font-semibold">PURPLE</div>
                </div>
                <div className="rounded-9980xl bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-3 box-border items-center justify-center text-center text-mini text-white">
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
          </div>
          <div className="self-stretch rounded-11xl bg-white flex flex-col py-0 px-3 items-start justify-start gap-[12px]">
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[12px] text-dodgerblue">
              <div className="relative font-semibold">커버 이미지</div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    className="relative w-[30px] h-[30px] object-cover"
                    alt=""
                    src="/ripple4@2x.png"
                  />
                  <div className="relative font-semibold">PURPLE</div>
                </div>
                <div className="rounded-9980xl bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-3 box-border items-center justify-center text-center text-mini text-white">
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
            <div className="self-stretch flex flex-col py-1 px-4 items-start justify-start gap-[24px]">
              <div className="self-stretch flex flex-row items-start justify-between">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <img
                    className="relative w-[30px] h-[30px] object-cover"
                    alt=""
                    src="/ripple5@2x.png"
                  />
                  <div className="relative font-semibold">PURPLE</div>
                </div>
                <div className="rounded-9980xl bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[15px] px-3 box-border items-center justify-center text-center text-mini text-white">
                  <b className="relative tracking-[0.05em] leading-[19px]">
                    장착
                  </b>
                </div>
              </div>
              <div className="self-stretch relative bg-whitesmoke-200 h-px" />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export default CustomShop;
