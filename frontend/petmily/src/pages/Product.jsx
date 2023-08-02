import { useNavigate } from 'react-router-dom';

import { ProductCarousel } from 'components';
import { useSetRecoilState } from 'recoil';
import selectAtom from 'states/select';
import { placeholderImage } from 'utils/utils';

const petCategories = ['강아지', '고양이', '기타 동물'];
function Product() {
  const navigation = useNavigate();
  const setSelect = useSetRecoilState(selectAtom);
  const handleShowItem = category => {
    setSelect(category);
    navigation(`/product/${category}`);
  };
  return (
    <div
      className="relative bg-whitesmoke flex flex-col justify-start min-w-fit items-center w-full 
    min-h-[1600px] text-left text-[1.75rem] text-gray font-pretendard"
    >
      <ProductCarousel />
      <div className="relative h-fit top-40">
        <div className="flex flex-col items-center gap-[7.5rem]">
          <div className="relative tracking-[0.05em] leading-[125%] font-semibold mt-10">
            반려동물 카테고리
          </div>
          <div className="flex flex-row items-start gap-[80px] w-full">
            {petCategories.map(ele => (
              <div className="relative w-[520px] h-fit">
                <img
                  className="relative h-[75%] w-full left-[0%] rounded-11xl max-w-full max-h-full object-cover"
                  alt=""
                  src={placeholderImage(Math.floor(Math.random() * 1001) + 1)}
                />
                <div className="relative h-[15%] w-[22%] right-[76%] bottom-[0%] left-[2%]">
                  <b className="relative top-[0%] left-[0%] tracking-[0.01em] leading-[200%] whitespace-nowrap">
                    {ele}
                  </b>
                  <div
                    role="presentation"
                    className="whitespace-nowrap relative left-[0%] text-[1.25rem] tracking-[0.01em] leading-[125%] font-semibold text-dodgerblue cursor-pointer"
                    onClick={() => {
                      handleShowItem(ele);
                    }}
                  >
                    용품 보러가기
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Product;
