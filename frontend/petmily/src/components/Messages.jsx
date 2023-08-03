import React from 'react';

const tempMessages = Array.from({ length: 5 }, (_, i) => i);

function Messages() {
  return (
    <div className="mx-4 basis-1/4 flex h-screen rounded-lg bg-white min-w-[20%] flex-col p-[1rem] items-start justify-start gap-[0.38rem]">
      <div className="flex w-full flex-col items-start justify-center gap-[1.06rem] text-[1.25rem]">
        <div className="relative font-semibold">메세지 목록</div>
        <div className="relative bg-slate-200 w-full h-[2px]" />
      </div>
      {tempMessages.map(ele => {
        return (
          <div
            key={ele}
            className="self-stretch flex flex-col items-start justify-start gap-[0.63rem]"
          >
            <div className="w-full flex flex-row py-[0.75rem] px-[1rem] box-border items-center justify-between">
              <div className="w-[184px] flex flex-row items-center justify-between">
                <div className="relative rounded-99980xl w-[49px] h-[49px] overflow-hidden shrink-0">
                  <img
                    className="absolute h-[97.96%] w-[97.96%] top-[2.04%] right-[2.04%] bottom-[0%] left-[0%] max-w-full overflow-hidden max-h-full object-cover"
                    alt=""
                    src="/messagepartnerimage@2x.png"
                  />
                </div>
                <div className="flex flex-col items-start justify-start gap-[0.19rem]">
                  <b className="relative">Bessie Cooper</b>
                  <div className="relative text-[1rem] tracking-[-0.02em] font-medium text-darkgray-100">
                    blahblah
                  </div>
                </div>
              </div>
              <div className="rounded-9980xl bg-dodgerblue h-[30px] overflow-hidden flex flex-row py-[0.94rem] px-[0.75rem] box-border items-center justify-center text-center text-[0.94rem] text-white">
                <b className="relative leading-[1.19rem]">{ele}</b>
              </div>
            </div>
            {ele < tempMessages.length - 1 ? (
              <div className="relative bg-slate-200 w-full h-[2px]" />
            ) : null}
          </div>
        );
      })}
    </div>
  );
}

export default Messages;
