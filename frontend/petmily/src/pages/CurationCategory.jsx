import RenderCuration from 'components/RenderCuration';
import { useLocation } from 'react-router-dom';
import { useRecoilValue } from 'recoil';
import { curationsAtom } from 'states/curations';
import { placeholderImage } from 'utils/utils';

function CurationCategory() {
  const path = decodeURIComponent(window.location.pathname).split('/');
  const location = useLocation();
  const [petType, category] = location.state;
  const petCurations = useRecoilValue(curationsAtom);
  const filterPetCurations = petCurations[petType].filter(
    ele => ele.category === category,
  );
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
            category={path.at(-1)}
            showMore={false}
            renderData={filterPetCurations}
          />
        </div>
      </div>
    </div>
  );
}

export default CurationCategory;
