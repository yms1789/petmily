import CustomSelect from 'components/CustomSelect';
import RenderCuration from 'components/RenderCuration';
import { useRecoilValue } from 'recoil';
import selectAtom from 'states/select';
import { placeholderImage } from 'utils/utils';

function CurationDetail() {
  const select = useRecoilValue(selectAtom);
  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] max-w-full relative text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <img
            className="relative w-full h-[200px]"
            alt=""
            src={placeholderImage}
          />
          <div className="h-20" />
          <b className="text-center self-stretch relative text-13xl tracking-[0.01em] leading-[125%] text-black">
            HOT TOPIC
          </b>
          <RenderCuration category="인기" />
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
          {['건강', '미용', '식품', '여행'].includes(select) ? (
            <RenderCuration category={select} />
          ) : (
            <>
              <RenderCuration category="건강" />
              <RenderCuration category="미용" />
              <RenderCuration category="식품" />
              <RenderCuration category="여행" />
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default CurationDetail;
