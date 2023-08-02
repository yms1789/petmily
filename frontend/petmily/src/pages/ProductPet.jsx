import { ProductCarousel, RenderProducts, SearchBar } from 'components';
import CustomSelect from 'components/CustomSelect';
import { useRecoilValue } from 'recoil';
import selectAtom from 'states/select';

const productCategories = ['식품', '미용', '건강', '기타'];
function ProductPet() {
  const select = useRecoilValue(selectAtom);
  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] max-w-full relative text-[1.75rem] text-gray">
        <div className=" flex flex-col items-start justify-start text-[1.5rem] text-white">
          <ProductCarousel />
          <div className="h-10" />
          <b className="text-center self-stretch relative text-13xl tracking-[0.01em] leading-[125%] text-black">
            HOT ITEM
          </b>
          <div className="h-10" />
          <div className="p-8 w-[96%] min-w-[1340px] max-w-full">
            <div className="flex flex-row justify-around items-center w-full h-auto mt-20 mb-20">
              <div className="relative flex gap-5 justify-start flex-row w-full h-auto">
                <CustomSelect
                  select={select}
                  options={['강아지', '고양이', '기타동물']}
                />
              </div>
              <SearchBar page="최저가" />
            </div>
            {productCategories.map(category => (
              <RenderProducts category={category} showMore />
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ProductPet;
