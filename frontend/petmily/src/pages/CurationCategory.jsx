import RenderCuration from 'components/RenderCuration';
import { useEffect } from 'react';
import useFetch from 'utils/fetch';
import { placeholderImage } from 'utils/utils';

function CurationCategory() {
  const path = decodeURIComponent(window.location.pathname).split('/');
  const fetchCuration = useFetch();
  useEffect(() => {
    async function handleCuration() {
      try {
        const response = fetchCuration.get(
          `curation/${path.slice(2).join('/')}`,
        );
        console.log(response);
      } catch (error) {
        console.log(error);
      }
    }
    handleCuration();
    return () => {};
  }, [fetchCuration, path]);

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
          <div className="h-10" />

          <RenderCuration category={path.at(-1)} showMore={false} />
        </div>
      </div>
    </div>
  );
}

export default CurationCategory;
