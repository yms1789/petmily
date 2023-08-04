import React, { useEffect, useState } from 'react';

import { useRecoilState, useRecoilValue } from 'recoil';
import { ProductCarousel, RenderProducts, SearchBar } from 'components';
import selectAtom from 'states/select';
import CustomSelect from 'components/CustomSelect';
import searchAtom from 'states/search';
import useFetch from 'utils/fetch';
import productAtom from 'states/products';

const productCategories = ['식품', '미용', '건강'];
function ProductPet() {
  const select = useRecoilValue(selectAtom);
  const altSelect = decodeURIComponent(
    window.location.pathname.split('/').at(-1),
  );
  const searchResult = useRecoilValue(searchAtom);
  const [globalProduct, setGlobalProduct] = useRecoilState(productAtom);
  const [isSearch, setIsSearch] = useState(false);
  const fetchData = useFetch();
  useEffect(() => {
    const fetchPetData = async () => {
      try {
        const petSelect = decodeURIComponent(
          window.location.pathname.split('/').at(-1),
        );
        const curationData = await fetchData.get(
          `/product/getdata?species=${petSelect}`,
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
    fetchPetData();
    return () => {};
  }, []);

  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] max-w-full w-full relative text-[1.75rem] text-gray">
        <div className=" flex flex-col items-start justify-start text-[1.5rem] text-white">
          <ProductCarousel />
          <div className="h-10" />

          <div className="h-10" />
          <div className="p-8 w-[96%] min-w-[1340px] max-w-full">
            <div className="flex flex-row justify-around items-center w-full h-auto mt-20 mb-20">
              <div className="relative flex gap-5 justify-start flex-row w-full h-auto">
                <CustomSelect
                  component="product"
                  select={select.length > 0 ? select : altSelect}
                  options={['강아지', '고양이', '기타동물']}
                />
              </div>
              <SearchBar
                page="최저가"
                petCategory={select.length > 0 ? select : altSelect}
                setIsSearch={setIsSearch}
              />
            </div>{' '}
            {isSearch ? (
              <RenderProducts category="검색" renderData={searchResult} />
            ) : (
              productCategories.map(category => (
                <RenderProducts
                  category={category}
                  showMore
                  renderData={globalProduct[category]}
                />
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductPet;
