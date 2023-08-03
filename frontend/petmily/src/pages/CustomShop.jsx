import StorefrontIcon from '@mui/icons-material/Storefront';
import { ReactComponent as StarCoin } from 'static/images/starCoin.svg';

// const items = [
//   { itemTitle: '프로필 링', price: 5 },
//   { itemTitle: '뱃지', price: 10 },
//   { itemTitle: '커버 이미지', price: 10 },
//   { itemTitle: '프로필 링', price: 50 },
// ];

function CustomShop() {
  return (
    <div className="relative bg-whitesmoke-100 min-w-[1340px] w-full max-w-full h-[1080px] overflow-y-auto text-left text-11xl text-dodgerblue font-pretendard">
      <div className="absolute px-5 min-w-[1340px] w-[98%] top-10 flex flex-row items-start justify-start gap-[60px] text-xl text-gray">
        <div className="rounded-11xl bg-white min-w-[350px] h-[768px] overflow-hidden shrink-0 flex flex-col basis-1/4 p-4 box-border items-start justify-start gap-[6px]">
          <div className="flex flex-col items-start justify-center gap-[17px]">
            <div className="relative font-semibold">포인트 내역</div>
            <div className="relative bg-whitesmoke-200 w-[400px] h-px" />
          </div>
          <div className="self-stretch flex flex-col items-start justify-start gap-[6px]">
            <div className="self-stretch rounded-11xl bg-white flex flex-row p-3 items-start justify-start gap-[24px]">
              <img
                className="self-stretch relative max-h-full w-[3px]"
                alt=""
                src="/curation-title-line.svg"
              />
              <div className="w-[319px] flex flex-col items-start justify-start gap-[12px]">
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <div className="relative tracking-[0.05em] leading-[125%] font-semibold">
                    +10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    포인트가 지급되었습니다.
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  포인트 받는 행위를 한거 여기 적자 블라블라
                </div>
              </div>
            </div>
            <div className="relative bg-whitesmoke-200 w-[400px] h-px" />
          </div>
          <div className="self-stretch flex flex-col items-start justify-start gap-[6px]">
            <div className="self-stretch rounded-11xl bg-white flex flex-row p-3 items-start justify-start gap-[24px]">
              <img
                className="self-stretch relative max-h-full w-[3px]"
                alt=""
                src="/curation-title-line1.svg"
              />
              <div className="w-[319px] flex flex-col items-start justify-start gap-[12px]">
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <div className="relative tracking-[0.05em] leading-[125%] font-semibold">
                    -10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    포인트가 차감되었습니다.
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  포인트 쓰는 행위를 한거 여기 적자 블라블라
                </div>
              </div>
            </div>
            <div className="relative bg-whitesmoke-200 w-[400px] h-px" />
          </div>
          <div className="self-stretch flex flex-col items-start justify-start gap-[6px]">
            <div className="self-stretch rounded-11xl bg-white flex flex-row p-3 items-start justify-start gap-[24px]">
              <img
                className="self-stretch relative max-h-full w-[3px]"
                alt=""
                src="/curation-title-line2.svg"
              />
              <div className="w-[319px] flex flex-col items-start justify-start gap-[12px]">
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <div className="relative tracking-[0.05em] leading-[125%] font-semibold">
                    +10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    포인트가 지급되었습니다.
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  포인트 받는 행위를 한거 여기 적자 블라블라
                </div>
              </div>
            </div>
            <div className="relative bg-whitesmoke-200 w-[400px] h-px" />
          </div>
          <div className="self-stretch flex flex-col items-start justify-start gap-[6px]">
            <div className="self-stretch rounded-11xl bg-white flex flex-row p-3 items-start justify-start gap-[24px]">
              <img
                className="self-stretch relative max-h-full w-[3px]"
                alt=""
                src="/curation-title-line3.svg"
              />
              <div className="w-[319px] flex flex-col items-start justify-start gap-[12px]">
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <div className="relative tracking-[0.05em] leading-[125%] font-semibold">
                    +10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    포인트가 지급되었습니다.
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  포인트 받는 행위를 한거 여기 적자 블라블라
                </div>
              </div>
            </div>
            <div className="relative bg-whitesmoke-200 w-[400px] h-px" />
          </div>
          <div className="self-stretch flex flex-col items-start justify-start gap-[6px]">
            <div className="self-stretch rounded-11xl bg-white flex flex-row p-3 items-start justify-start gap-[24px]">
              <img
                className="self-stretch relative max-h-full w-[3px]"
                alt=""
                src="/curation-title-line4.svg"
              />
              <div className="w-[319px] flex flex-col items-start justify-start gap-[12px]">
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <div className="relative tracking-[0.05em] leading-[125%] font-semibold">
                    +10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    포인트가 지급되었습니다.
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  포인트 받는 행위를 한거 여기 적자 블라블라
                </div>
              </div>
            </div>
            <div className="relative bg-whitesmoke-200 w-[400px] h-px" />
          </div>
        </div>
        <div className="rounded-11xl min-w-[700px] bg-dodgerblue basis-[50%] h-[868px] flex flex-col items-center justify-start text-11xl text-white">
          <div className="self-stretch rounded-t-11xl rounded-b-none bg-dodgerblue flex flex-col p-9 items-start justify-start gap-[32px]">
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
              <div className="self-stretch flex-1 rounded-11xl bg-white flex flex-col p-6 items-center justify-start gap-[12px]">
                <StarCoin width={295} height={295} />
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <StarCoin width={50} height={50} />
                  <div className="relative tracking-[0.01em] leading-[125%] font-semibold">
                    +5
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    프로필 링 랜덤 뽑기
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  설명 블라블라
                </div>
              </div>
              <div className="self-stretch flex-1 rounded-11xl bg-white flex flex-col p-6 items-center justify-start gap-[12px]">
                <StarCoin width={295} height={295} />
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <StarCoin width={50} height={50} />
                  <div className="relative tracking-[0.01em] leading-[125%] font-semibold">
                    +10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    뱃지 랜덤 뽑기
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  설명 블라블라
                </div>
              </div>
            </div>
            <div className="self-stretch flex-1 flex flex-row items-start justify-start gap-[24px]">
              <div className="self-stretch flex-1 rounded-11xl bg-white flex flex-col p-6 items-start justify-start gap-[12px]">
                <img
                  className="self-stretch flex-1 relative max-w-full overflow-hidden max-h-full object-cover"
                  alt=""
                  src="/star-coin5@2x.png"
                />
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <img
                    className="relative w-[26px] h-[26px] object-cover"
                    alt=""
                    src="/star-coin6@2x.png"
                  />
                  <div className="relative tracking-[0.01em] leading-[125%] font-semibold">
                    +10
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    커버 이미지 랜덤 뽑기
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  설명 블라블라
                </div>
              </div>
              <div className="self-stretch flex-1 rounded-11xl bg-white flex flex-col p-6 items-start justify-start gap-[12px]">
                <img
                  className="self-stretch flex-1 relative max-w-full overflow-hidden max-h-full object-cover"
                  alt=""
                  src="/star-coin7@2x.png"
                />
                <div className="self-stretch flex flex-row items-center justify-start gap-[6px]">
                  <img
                    className="relative w-[26px] h-[26px] object-cover"
                    alt=""
                    src="/star-coin8@2x.png"
                  />
                  <div className="relative tracking-[0.01em] leading-[125%] font-semibold">
                    +50
                  </div>
                  <div className="relative text-base tracking-[0.01em] leading-[125%] font-semibold">
                    ALL 랜덤 박스
                  </div>
                </div>
                <div className="self-stretch relative text-base tracking-[0.01em] leading-[125%] text-slategray">
                  설명 블라블라
                </div>
              </div>
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
