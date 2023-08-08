import { useEffect, useState } from 'react';
import { CircularProgress } from '@mui/material';
import useFetch from 'utils/fetch';
import { RenderCuration } from 'components';

function Curation() {
  const fetchData = useFetch();
  const [curationDatas, setCurationDatas] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  useEffect(() => {
    setIsLoading(true);
    const fetchCuration = async () => {
      try {
        const curationData = await fetchData.get(
          'curation/getNewsData?species=all',
        );
        if (curationData) {
          setCurationDatas(curationData);
          setIsLoading(false);
        }
      } catch (error) {
        console.log('error', error);
      }
    };
    fetchCuration();
  }, []);

  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] w-[95%] py-2 px-10 relative text-[1.75rem] text-gray">
        <div className="flex flex-col items-start justify-start text-[1.5rem] text-white">
          <div className="h-48" />
          <b className="text-center self-stretch relative text-13xl tracking-[0.01em] leading-[125%] text-black">
            HOT TOPIC
          </b>
          <div className="h-10" />
          {!isLoading ? (
            <>
              <RenderCuration
                category="강아지"
                renderData={curationDatas['강아지']}
              />
              <RenderCuration
                category="고양이"
                renderData={curationDatas['고양이']}
              />
              <RenderCuration
                category="기타동물"
                renderData={curationDatas['기타동물']}
              />
            </>
          ) : (
            <div className="flex w-full mt-20 flex-row justify-center items-center text-darkgray">
              <CircularProgress color="inherit" size={70} />
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Curation;
