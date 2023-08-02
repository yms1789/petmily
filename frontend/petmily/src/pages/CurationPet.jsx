import { Suspense, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useRecoilValue, useSetRecoilState } from 'recoil';
import CustomSelect from 'components/CustomSelect';
import RenderCuration from 'components/RenderCuration';
import { curationsAtom } from 'states/curations';
import selectAtom from 'states/select';
import useFetch from 'utils/fetch';
import { placeholderImage } from 'utils/utils';

function CurationPet() {
  const select = useRecoilValue(selectAtom);
  const location = useLocation();
  const fetchData = useFetch();
  const { petType } = location.state;
  console.log(petType);
  const [curations, setCurations] = useState([]);
  const setGlobalCurations = useSetRecoilState(curationsAtom);
  useEffect(() => {
    const fetchPetData = async () => {
      try {
        const curationData = await fetchData.get(`curation/getNewsData`);
        console.log('fetchData', curationData);
        setCurations(curationData.filter(ele => ele.ccategory === '이슈'));
        setGlobalCurations({
          건강: curationData.filter(ele => ele.ccategory === '이슈'),
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
      <div className="min-w-[1340px] max-w-full relative text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <img
            className="relative w-full h-[200px] rounded-[20px]"
            alt=""
            src={placeholderImage(Math.floor(Math.random() * 1001) + 1)}
          />
          <div className="h-20" />
          <b className="text-center self-stretch relative text-13xl tracking-[0.01em] leading-[125%] text-black">
            HOT TOPIC
          </b>
          <Suspense fallback={<p>글목록 로딩중...</p>}>
            <RenderCuration category="인기" renderData={curations} />
          </Suspense>
          <div className="h-10" />
          <div className="flex flex-row justify-around items-center w-full h-auto mt-20 mb-20">
            <div className="relative flex gap-5 justify-start flex-row w-full h-auto">
              <CustomSelect
                select="카테고리"
                options={['건강', '미용', '식품', '여행', '전체']}
              />
              <CustomSelect
                component="더보기"
                select="정렬"
                options={['최신순', '북마크 순']}
              />
            </div>
          </div>
          <Suspense fallback={<p>글목록 로딩중...</p>}>
            {['건강', '미용', '식품', '여행'].includes(select) ? (
              <RenderCuration category={select} renderData={curations} />
            ) : (
              <>
                <RenderCuration
                  pet={location.state.petType}
                  category="건강"
                  renderData={curations}
                />
                <RenderCuration category="미용" renderData={curations} />
                <RenderCuration category="식품" renderData={curations} />
                <RenderCuration category="여행" renderData={curations} />
              </>
            )}
          </Suspense>
        </div>
      </div>
    </div>
  );
}

export default CurationPet;
