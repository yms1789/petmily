import { Paper } from '@mui/material';
import Carousel from 'react-material-ui-carousel';

const placeholderData = Array.from(
  { length: 5 },
  (_, i) => `https://picsum.photos/1920/1000/?image=${i + 1}`,
);
function ProductCarousel() {
  return (
    <div
      role="presentation"
      className="relative flex flex-col mt-10 text-[2.38rem] text-white min-w-[1340px] w-full"
    >
      <Carousel
        className="w-full min-w-[1340px]"
        indicators={false}
        autoPlay
        fullHeightHover
        swipe
      >
        {placeholderData.map(ele => {
          return (
            <Paper key={ele}>
              <div className="flex-1 flex w-full min-w-[1340px]">
                <img
                  className="relative w-full h-[30rem] object-fit"
                  alt=""
                  src={ele}
                />
              </div>
            </Paper>
          );
        })}
      </Carousel>
      <div className="absolute top-[25rem] left-0 brightness-125 flex flex-1 flex-col z-[1]">
        <div
          className="relative flex flex-row justify-center items-center bg-dodgerblue [backdrop-filter:blur(100px)]
            rounded-tr-[100px] rounded-br-[100px] w-[30rem] h-[5rem]"
        >
          <div className="tracking-[0.01em] leading-[125%] font-semibold text-5xl">
            인기 강아지 장난감 브랜드명
          </div>
        </div>
        <div
          className="flex flex-row justify-center gap-2 items-center relative bg-dodgerblue [backdrop-filter:blur(100px)]
            rounded-tr-[100px] rounded-br-[100px] w-[30rem] h-[5rem]"
        >
          <div className="tracking-[0.01em] leading-[125%] font-semibold text-5xl">
            12900원
          </div>
          <div className="text-[1.5rem] tracking-[0.01em] leading-[125%] font-semibold">
            ~ 최저가
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductCarousel;
