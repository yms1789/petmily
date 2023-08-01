import RenderCuration from 'components/RenderCuration';
import { placeholderImage } from 'utils/utils';
import useFetch from 'utils/fetch';
import { useEffect, useState } from 'react';

function Curation() {
  const fetchData = useFetch();
  const [curationDatas, setCurationDatas] = useState([]);
  useEffect(() => {
    const fetchCuration = async () => {
      try {
        const curationData = await fetchData.get('curation/getNewsData');
        if (curationData) {
          setCurationDatas(curationData);
        }
      } catch (error) {
        console.log('error', error);
      }
    };
    fetchCuration();
    return () => {};
  }, []);

  return (
    <div className="bg-whitesmoke  min-w-[1340px] max-w-full flex flex-1 flex-col items-center justify-center text-left text-[1.13rem] text-darkgray font-pretendard">
      <div className="min-w-[1340px] max-w-full relative text-[1.75rem] text-gray">
        <div className=" flex p-[40px] flex-col items-start justify-start text-[1.5rem] text-white">
          <img
            className="relative w-full h-[200px] rounded-[20px]"
            alt=""
            src={placeholderImage(Math.floor(Math.random() * 1001))}
          />
          <div className="h-20" />
          <b className="text-center self-stretch relative text-13xl tracking-[0.01em] leading-[125%] text-black">
            HOT TOPIC
          </b>
          <div className="h-10" />
          <RenderCuration category="강아지" renderData={curationDatas} />
          <RenderCuration category="고양이" renderData={curationDatas} />
          <RenderCuration category="기타동물" renderData={curationDatas} />
        </div>
      </div>
    </div>
  );
}

export default Curation;
