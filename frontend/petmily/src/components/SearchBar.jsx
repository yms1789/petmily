import React, { useState } from 'react';
import SearchIcon from '@mui/icons-material/Search';
import { string } from 'prop-types';

function SearchBar({ page }) {
  const [inputSearch, setInputSearch] = useState('');
  return page !== '소통하기' ? (
    <div
      className={`relative flex flex-row justify-between items-center ${
        page === '최저가' ?? 'top-[100px]'
      } rounded-11xl bg-white w-[760px] h-[60px]`}
    >
      <input
        className="outline-none w-full rounded-[100px] h-auto py-5 px-4 border-[1.5px] border-solid border-darkgray 
        focus:border-dodgerblue focus:border-[1.5px] font-pretendard text-base
        relative tracking-[0.01em] leading-[125%]"
        placeholder="검색어를 입력하세요"
        value={inputSearch}
        onChange={e => setInputSearch(e.target.value)}
      />
      <SearchIcon
        className="absolute right-4 z-[1] cursor-pointer text-darkgray"
        fontSize="large"
        onClick={() => {
          console.log('click');
        }}
      />
    </div>
  ) : null;
}
SearchBar.propTypes = {
  page: string,
};
export default SearchBar;
