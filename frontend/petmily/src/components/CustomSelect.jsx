import { arrayOf, string } from 'prop-types';
import { useState } from 'react';
import PetsIcon from '@mui/icons-material/Pets';
import { styled } from '@mui/material';

function CustomSelect({ select, options }) {
  const [currentValue, setCurrentValue] = useState(select);
  const [isShowOptions, setShowOptions] = useState(false);

  const StyledPetsIcon = styled(PetsIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({
    width: '30px',
    height: 'auto',
  });

  return (
    <div
      className={`relative rounded-31xl bg-dodgerblue flex flex-row w-fit
    py-[0.7rem] px-[1.3rem] items-center justify-end gap-[0.75rem] cursor-pointer 
    before:content-['⌵']  before:font-extrabold before:absolute before:top-0 before:right-4 before:text-white before:text-[30px]`}
      role="presentation"
      onClick={() => {
        setShowOptions(prev => !prev);
      }}
    >
      <StyledPetsIcon className="absolute top-2 left-4" />
      <label
        htmlFor="id"
        className="left-12bg-transparent w-[120px] ml-8 text-white appearance-none relative tracking-[0.05em] leading-[125%] font-extrabold text-xl whitespace-nowrap"
      >
        {currentValue}
      </label>
      <ul
        className={`absolute top-[1.4rem] left-0 w-full h-fit ${
          isShowOptions
            ? 'max-h-none border-solid border-2 border-dodgerblue'
            : 'max-h-0'
        } p-0 rounded-[15px] bg-white list-none overflow-hidden
        }`}
      >
        {options.map(ele => (
          <li
            key={ele}
            role="presentation"
            className="text-xl text-dodgerblue px-2 py-2 hover:bg-darkgray hover:brightness-125 transition duration-200 ease-in"
            onClick={() => {
              setCurrentValue(ele);
            }}
          >
            {ele}
          </li>
        ))}
      </ul>
    </div>
  );
}

CustomSelect.propTypes = {
  select: string,
  options: arrayOf(string),
};

export default CustomSelect;
