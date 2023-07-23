import React, { useState } from 'react';
import { useDaumPostcodePopup } from 'react-daum-postcode';

function SearchAddress() {
  const [address, setAddress] = useState(''); // 주소를 담을 state

  const scriptUrl =
    'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  const open = useDaumPostcodePopup(scriptUrl);

  const handleComplete = data => {
    let fullAddress = data.address;
    let extraAddress = '';

    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress +=
          extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName;
      }
      fullAddress += extraAddress !== '' ? ` (${extraAddress})` : '';
    }

    setAddress(fullAddress); // 주소를 state에 저장
    console.log(fullAddress); // 예: '서울 성동구 왕십리로2길 20 (성수동1가)'
  };

  const handleClick = () => {
    open({ onComplete: handleComplete });
  };

  return (
    <div className="flex items-center w-full">
      <input
        type="text"
        value={address}
        readOnly
        onChange={() => {}} // 경고 방지를 위한 빈 함수
        placeholder="주소를 검색해주세요"
        className="rounded-3xs box-border h-[3rem] flex-1 flex flex-row px-[1rem] items-center justify-start border-[1.5px] border-solid border-darkgray"
      />
      <button
        type="button"
        onClick={handleClick}
        className="rounded-31xl text-white bg-dodgerblue overflow-hidden flex-nowrap flex flex-row py-2.5 px-4 items-center justify-center text-[1rem] ml-5"
      >
        주소 검색
      </button>
    </div>
  );
}

export default SearchAddress;
