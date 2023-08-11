import React from 'react';
import { useNavigate } from 'react-router-dom';
import { useSetRecoilState } from 'recoil';
import { v4 as uuidv4 } from 'uuid';
import { objectOf, func, string } from 'prop-types';

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
function ErrorFallback({ error, resetErrorBoundary }) {
  console.log(error, resetErrorBoundary);
  return (
    <div role="alert">
      <p>Something went wrong:</p>
      <pre style={{ color: 'red' }}>{error.message}</pre>
      <button type="button" onClick={resetErrorBoundary}>
        Try again
      </button>
    </div>
  );
}
function Product() {
  const navigation = useNavigate();
  const setSelect = useSetRecoilState(selectAtom);

  const setGlobalProduct = useSetRecoilState(productAtom);
  const fetchData = useFetch();

  const fetchPetData = async selectPet => {
    try {
      const productData = await fetchData.get(
        `/product/getdata?species=${selectPet}`,
      );
      console.log('fetchData', productData);

      if (productData && productData.length > 0) {
        setGlobalProduct({
          식품: productData['식품'],
          건강: productData['건강'],
          미용: productData['미용'],
          기타: productData['기타'],
        });
        return true;
      }
      return false;
    } catch (error) {
      console.log(error);
      return false;
    }
  };
  const handleShowItem = async category => {
    setSelect(category);
    const result = await fetchPetData(category);
    if (result) navigation(`/product/${category}`);
    else throw new Error();
  };
  return (
    <div
      className="relative bg-whitesmoke flex flex-col justify-start min-w-[1340px] items-center w-full 
      text-left text-[1.75rem] text-gray font-pretendard"
    >
      <div className="relative h-fit top-44 w-[96%]">
        <div className="flex flex-col items-center gap-[7.5rem]">
          <div className="relative tracking-[0.05em] leading-[125%] font-semibold mt-10">
            반려동물 카테고리
          </div>
          <div className="flex flex-row items-start gap-[80px] w-full">
            {petCategories.map(ele => (
              <div
                key={uuidv4()}
                className="relative w-84 h-fit w-full hover:brightness-90"
              >
                <img
                  role="presentation"
                  className="relative h-auto left-[0%] rounded-11xl max-w-full w-full max-h-full object-cover cursor-pointer"
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
ErrorFallback.propTypes = {
  error: objectOf(string),
  resetErrorBoundary: func,
};

export default Product;
