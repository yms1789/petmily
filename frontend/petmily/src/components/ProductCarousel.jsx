import Carousel from 'react-material-ui-carousel';
import { useEffect, useState } from 'react';
import { Paper } from '@mui/material';
import { useRecoilValue } from 'recoil';
import productAtom from 'states/products';
import { priceToString } from 'utils/utils';

function ProductCarousel() {
  const globalProduct = useRecoilValue(productAtom);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [popularItems, setPopularItems] = useState([]);
  useEffect(() => {
    try {
      const newPopularItems = [];
      console.log('prodCarousel', globalProduct);
      Object.keys(globalProduct).forEach(category => {
        if (globalProduct?.[category].length > 0) {
          newPopularItems.push(globalProduct[category][0]);
        }
      });
      setPopularItems(newPopularItems);
    } catch (error) {
      throw new Error();
    }
  }, []);
  const handleSlideChange = index => {
    setCurrentIndex(index);
  };

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
        onChange={handleSlideChange}
      >
        {popularItems.map(ele => {
          console.log(ele);
          return (
            <Paper key={ele.productName}>
              <a href={ele.productUrl}>
                <div className="flex-1 flex w-full min-w-[1340px]">
                  <img
                    className="relative w-full h-[30rem] object-contain"
                    alt=""
                    src={ele.productImg}
                  />
                </div>
              </a>
            </Paper>
          );
        })}
      </Carousel>
      <div className="absolute top-[25rem] left-0 brightness-125 flex flex-1 flex-col z-[1]">
        <div
          className="relative flex flex-row justify-center items-center bg-dodgerblue [backdrop-filter:blur(100px)]
            rounded-tr-[100px] rounded-br-[100px] w-fit h-[5rem]"
        >
          <div className="tracking-[0.01em] leading-[125%] font-semibold text-5xl px-5 whitespace-nowrap">
            {popularItems[currentIndex].productName.replace(/<\/?b>/g, '')}
          </div>
        </div>
        <div
          className="flex flex-row justify-center gap-2 items-center relative bg-dodgerblue [backdrop-filter:blur(100px)]
            rounded-tr-[100px] rounded-br-[100px] w-[30rem] h-[5rem]"
        >
          <div className="tracking-[0.01em] leading-[125%] font-semibold text-5xl">
            {priceToString(popularItems[currentIndex].productPrice)}원
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductCarousel;
