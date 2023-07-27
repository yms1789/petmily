import React, { useState } from 'react';
import { styled } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import { string } from 'prop-types';

function SearchBar({ page }) {
  const [inputSearch, setInputSearch] = useState('');
  const StyledSearchIcon = styled(SearchIcon, {
    name: 'StyledSearchIcon',
    slot: 'Wrapper',
  })({
    color: '#5b7083',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  return page !== '소통하기' ? (
    <div className="flex flex-row justify-between items-center border-solid border-2 px-5 border-dodgerblue mt-[5rem] top-[170px] rounded-11xl bg-white w-[760px] h-[60px] overflow-hidden">
      <input
        className=" focus:outline-none w-full h-auto focus:border-dodgerblue focus:border-1.5 font-pretendard text-base
        relative tracking-[0.01em] leading-[125%]"
        placeholder="검색어를 입력하세요"
        value={inputSearch}
        onChange={e => setInputSearch(e.target.value)}
      />
      <SearchIcon
        fontSize="large"
        onClick={() => {
          console.log('click');
        }}
      />
    </div>
  ) : (
    <div className="relative flex items-center justify-between rounded-11xl bg-white max-w-full h-[60px]">
      <input
        className=" focus:outline-none w-full h-auto focus:outline-dodgerblue py-[1rem] pl-[2rem] pr-[5rem] focus:border-1.5 font-pretendard text-base
        lex items-center font-medium rounded-full"
        placeholder="검색어를 입력하세요"
        value={inputSearch}
        onChange={e => setInputSearch(e.target.value)}
      />
      <StyledSearchIcon
        className="absolute right-0  px-[1.5rem]"
        onClick={() => {
          console.log('click');
        }}
      />
    </div>
  );
}
SearchBar.propTypes = {
  page: string,
};
export default SearchBar;
