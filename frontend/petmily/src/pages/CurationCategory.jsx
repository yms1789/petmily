import RenderCuration from 'components/RenderCuration';
import { useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { curationsAtom } from 'states/curations';
import { placeholderImage } from 'utils/utils';

function CurationCategory() {
  const location = useLocation();
  const { category } = location.state;
  const petCurations = useRecoilValue(curationsAtom);
  console.log('petCurations', petCurations[category], category);
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
          <div className="h-10" />

          <RenderCuration
            category={category}
            showMore={false}
            renderData={petCurations[category]}
          />
        </div>
      </div>
    </div>
  );
}

export default CurationCategory;
