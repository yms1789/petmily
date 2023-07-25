import React from 'react';

function SearchBar() {
  return (
    <div className="border-solid absolute top-[170px] left-[calc(50%_-_380px)] rounded-11xl bg-white w-[47.5rem] h-[3.75rem] overflow-hidden">
      <img
        className="absolute top-[calc(50%_-_15px)] left-[689px] w-[1.88rem] h-[1.88rem] overflow-hidden"
        alt=""
        src="/search1.svg"
      />
      <div className="absolute top-[calc(50%_-_11px)] left-[39px] tracking-[0.01em] leading-[125%] font-medium">
        검색어를 입력하세요
      </div>
    </div>
  );
}

export default SearchBar;
