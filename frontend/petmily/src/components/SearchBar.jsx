import React from 'react';

function SearchBar() {
  return (
    <div className="border-solid border-2 border-dodgerblue mt-[5rem] top-[170px] rounded-11xl bg-white w-[760px] h-[60px] overflow-hidden">
      <div className="relative top-[calc(50%_-_11px)] left-[39px] tracking-[0.01em] leading-[125%] font-medium">
        검색어를 입력하세요
      </div>
    </div>
  );
}

export default SearchBar;
