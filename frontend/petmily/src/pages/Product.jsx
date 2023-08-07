import React from 'react';
import { useNavigate } from 'react-router-dom';

import { useSetRecoilState } from 'recoil';
import selectAtom from 'states/select';
import ProductDog from 'static/images/productDog.svg';
import ProductCat from 'static/images/productCat.svg';
import ProductEtc from 'static/images/productEtc.svg';
import useFetch from 'utils/fetch';
import productAtom from 'states/products';

const petCategories = [
  ['강아지', ProductDog],
  ['고양이', ProductCat],
  ['기타 동물', ProductEtc],
];
function Product() {
  const navigation = useNavigate();
  const setSelect = useSetRecoilState(selectAtom);

  const setGlobalProduct = useSetRecoilState(productAtom);
  const fetchData = useFetch();

  const fetchPetData = async selectPet => {
    console.log('fetchData');
    try {
      const curationData = await fetchData.get(
        `/product/getdata?species=${selectPet}`,
      );
      console.log('fetchData', curationData['식품']);

      setGlobalProduct({
        식품: curationData['식품'],
        건강: curationData['건강'],
        미용: curationData['미용'],
        기타: curationData['기타'],
      });
    } catch (error) {
      console.log(error);
    }
  };
  const handleShowItem = async category => {
    setSelect(category);
    await fetchPetData(category);
    navigation(`/product/${category}`);
  };
  return (
    <div
      className="relative bg-whitesmoke flex flex-col justify-start min-w-[1340px] items-center w-full 
    min-h-fit h-fit text-left text-[1.75rem] text-gray font-pretendard"
    >
      <div className="relative h-fit top-40 w-[96%]">
        <div className="flex flex-col items-center gap-[7.5rem]">
          <div className="relative tracking-[0.05em] leading-[125%] font-semibold mt-10">
            반려동물 카테고리
          </div>
          <div className="flex flex-row items-start gap-[80px] w-full">
            {petCategories.map(ele => (
              <div className="relative w-84 h-fit w-full hover:brightness-90">
                <img
                  role="presentation"
                  className="relative h-[75%] left-[0%] rounded-11xl max-w-full w-full max-h-full object-cover cursor-pointer"
                  alt=""
                  src={ele[1]}
                  onClick={() => {
                    handleShowItem(ele[0]);
                  }}
                />
                <div className="relative h-[15%] w-[22%] right-[76%] bottom-[0%] left-[2%]">
                  <b className="relative top-[0%] left-[0%] tracking-[0.01em] leading-[200%] whitespace-nowrap">
                    {ele[0]}
                  </b>
                  <div
                    role="presentation"
                    className="whitespace-nowrap relative left-[0%] text-[1.25rem] tracking-[0.01em] leading-[125%] font-semibold text-dodgerblue cursor-pointer"
                    onClick={() => {
                      handleShowItem(ele[0]);
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
