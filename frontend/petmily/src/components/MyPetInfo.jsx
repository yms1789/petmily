import React from 'react';

function MyPetInfo() {
  return (
    <div className="rounded-11xl bg-white h-[768px] overflow-hidden flex flex-col p-[1rem] box-border items-start justify-start gap-[0.75rem] text-[1.25rem]">
      <div className="self-stretch relative h-[46px]">
        <div className="absolute top-[4px] left-[16px] font-semibold">
          내 반려동물
        </div>
        <div className="absolute top-[45px] left-[0px] bg-dark-7 w-[447px] h-px" />
      </div>
      <div className="relative w-[431px] h-[300px] text-[1.31rem]">
        <div className="absolute top-[242px] left-[11px] flex flex-col items-start justify-start gap-[0.31rem]">
          <b className="relative tracking-[-0.01em] z-[0]">망고</b>
          <div className="relative text-[1rem] tracking-[-0.01em] font-medium inline-block w-11 z-[1]">{`2살 `}</div>
          <img
            className="absolute my-0 mx-[!important] top-[31px] left-[26px] w-[18px] h-[18px] overflow-hidden shrink-0 z-[2]"
            alt=""
            src="/male.svg"
          />
        </div>
        <div className="absolute top-[0px] left-[0px] bg-white w-[430px] h-[200px]">
          <img
            className="absolute top-[0px] left-[-16px] w-[463px] h-[200px] object-cover"
            alt=""
            src="/placeholder@2x.png"
          />
        </div>
        <div className="absolute top-[142px] left-[9px] rounded-99980xl box-border w-[92px] h-[90px] overflow-hidden border-[4px] border-solid border-gray">
          <div className="absolute top-[0px] left-[2px] rounded-[283670.63px] w-[90px] h-[90px] overflow-hidden">
            <img
              className="absolute h-[97.96%] w-[97.96%] top-[2.04%] right-[2.04%] bottom-[0%] left-[0%] max-w-full overflow-hidden max-h-full object-cover"
              alt=""
              src="/profile-picture5@2x.png"
            />
          </div>
        </div>
        <div className="absolute top-[203px] left-[318px] rounded-9980xl box-border w-28 h-[39px] overflow-hidden flex flex-row py-[0.94rem] px-[0.19rem] items-center justify-center text-center text-[0.94rem] text-dodgerblue border-[1px] border-solid border-dodgerblue">
          <b className="flex-1 relative leading-[1.19rem]">정보 수정</b>
        </div>
      </div>
      <div className="rounded-11xl bg-white w-[430px] flex flex-col py-[0.31rem] px-[0.94rem] box-border items-start justify-start gap-[0.94rem] text-[0.94rem]">
        <div className="self-stretch flex flex-col items-start justify-start gap-[0.38rem] text-[0.88rem] text-slategray">
          <b className="relative text-[0.94rem] tracking-[0.02em] text-gray">
            특이사항
          </b>
          <div className="flex flex-row items-start justify-start">
            <div className="relative tracking-[-0.02em] font-medium">
              배고프면 신경질 적임
            </div>
          </div>
          <div className="flex flex-row items-start justify-start">
            <div className="relative tracking-[-0.02em] font-medium">
              주인(여민수) 닮아서 혼자 드립치고 혼자 웃음
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start justify-start gap-[0.38rem]">
          <b className="relative tracking-[0.02em] inline-block w-[235px]">
            다른 글 2
          </b>
          <div className="flex flex-row items-start justify-start text-[0.88rem] text-slategray">
            <div className="relative tracking-[-0.02em] font-medium">
              다른 글 2의 내용
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start justify-start gap-[0.38rem]">
          <b className="relative tracking-[0.02em] inline-block w-[235px]">
            다른 글 3
          </b>
          <div className="flex flex-row items-start justify-start text-[0.88rem] text-slategray">
            <div className="relative tracking-[-0.02em] font-medium">
              다른 글 3의 내용
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start justify-start gap-[0.38rem]">
          <b className="relative tracking-[0.02em] inline-block w-[235px]">
            다른 글 4
          </b>
          <div className="flex flex-row items-start justify-start text-[0.88rem] text-slategray">
            <div className="relative tracking-[-0.02em] font-medium">
              다른 글 4의 내용
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start justify-start gap-[0.38rem]">
          <b className="relative tracking-[0.02em] inline-block w-[235px]">
            다른 글 5
          </b>
          <div className="flex flex-row items-start justify-start text-[0.88rem] text-slategray">
            <div className="relative tracking-[-0.02em] font-medium">
              다른 글 5의 내용
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MyPetInfo;
