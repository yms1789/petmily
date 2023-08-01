import ArrowForwardIosRoundedIcon from '@mui/icons-material/ArrowForwardIosRounded';
import PetsIcon from '@mui/icons-material/Pets';
import { styled } from '@mui/material';
import { arrayOf, bool, shape, string } from 'prop-types';
import { memo, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import { v4 as uuidv4 } from 'uuid';

function RenderCuration({ category, showMore = true, renderData }) {
  console.log('reCuration', renderData);
  const memoizedRenderData = useMemo(() => renderData, [renderData]);
  const navigation = useNavigate();
  const StyledPetsIcon = styled(PetsIcon, {
    name: 'StyledPetsIcon',
    slot: 'Wrapper',
  })({
    width: '30px',
    height: 'auto',
  });
  const StyledArrowForwardIosRoundedIcon = styled(ArrowForwardIosRoundedIcon, {
    name: 'StyledArrowForwardIosRoundedIcon',
    slot: 'Wrapper',
  })({});
  const path = decodeURIComponent(window.location.pathname);
  const handleShowMoreClick = clickedCategory => {
    if (path.includes('pet')) {
      navigation(`/category/${path.split('/').at(-1)}/${clickedCategory}`, {
        state: { petType: path.split('/').at(-1), category: clickedCategory },
      });
    } else {
      navigation(`/pet/${clickedCategory}`, {
        state: { petType: clickedCategory },
      });
    }
  };
  if (renderData === null) {
    return null;
  }

  return (
    <div className="min-w-[1340px] max-w-full flex flex-col items-start justify-start gap-[2.25rem] mb-5 mt-5">
      <div className="self-stretch flex flex-row items-center justify-between">
        <div
          className={`rounded-31xl ${
            category === '인기'
              ? ' bg-white text-dodgerblue border-solid border-2 border-dodgerblue'
              : 'bg-dodgerblue'
          } overflow-hidden flex flex-row py-2.5 px-5 items-center justify-start gap-[0.5rem]`}
        >
          <StyledPetsIcon className="relative w-[14px] h-auto" />
          <div className="relative tracking-[0.01em] leading-[125%] font-extrabold px-2 rounded-xl text-xl">
            {category}
          </div>
        </div>
        {showMore && category !== '인기' ? (
          <div
            className="rounded-31xl overflow-hidden flex flex-row py-2.5 px-5 items-center bg-white gap-[12px] text-[1.25rem] text-dodgerblue border-[2px] border-solid cursor-pointer"
            role="presentation"
            onClick={() => {
              handleShowMoreClick(category);
            }}
          >
            <b className="relative tracking-[0.01em] leading-[125%] text-dodgerblue">
              더보기
            </b>
            <StyledArrowForwardIosRoundedIcon className="text-dodgerblue" />
          </div>
        ) : null}
      </div>
      <div className="min-w-[1340px] max-w-full flex flex-row items-start justify-start gap-[24.96px] text-[1rem] text-gray">
        {memoizedRenderData.slice(0, 5).map(ele => {
          return (
            <div
              key={uuidv4()}
              className="flex-1 min-w-[300px] rounded-11xl bg-white overflow-hidden flex flex-col pt-0 px-0 pb-6 items-center justify-center gap-[16px]"
            >
              <a
                href={ele.curl}
                className="w-fit h-fit no-underline text-black"
              >
                <img
                  className="relative w-[300px] object-fill"
                  alt=""
                  src={ele.cimage}
                />
                <div className="flex flex-col items-start justify-center gap-[16px] p-2 w-fit">
                  <div className="flex flex-row items-center justify-center gap-[12px]">
                    <div className="relative tracking-[0.01em] leading-[125%] font-medium flex items-start">
                      {ele.ctitle}
                    </div>
                  </div>
                  <div className="relative text-[0.88rem] tracking-[0.01em] leading-[125%] text-darkgray flex items-center">
                    {ele.ccontent}
                  </div>
                </div>
              </a>
            </div>
          );
        })}
      </div>
    </div>
  );
}

const rederDataType = shape({
  ctitle: string,
  ccontent: string,
  cimage: string,
  cdate: string,
  curl: string,
  cpetSpecies: string,
  ccategory: string,
});

RenderCuration.propTypes = {
  category: string,
  showMore: bool,
  renderData: arrayOf(rederDataType),
};
export default memo(RenderCuration);
