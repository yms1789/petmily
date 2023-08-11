import { Player } from '@lottiefiles/react-lottie-player';

import { string } from 'prop-types';

// import gacha from 'static/animations/gacha.json';
import gachaBoom from 'static/animations/gachaBoom.json';

function GachaModal({ gachaItem }) {
  console.log(gachaItem);
  return (
    <div
      className={`relative rounded-[10px] bg-inherit w-[656px] h-[450px] max-w-full flex flex-col justify-center items-center ${
        gachaItem ? 'bg-white' : 'bg-inherit'
      } max-h-full text-left text-xl text-darkgray font-pretendard`}
    >
      {gachaItem ? (
        <div>{gachaItem.itemName}</div>
      ) : (
        <>
          <Player loop src={gachaBoom} autoplay />
          <h1 className="absolute top-[-20px] left-24 text-[100px] text-white">
            나가!
          </h1>
        </>
      )}
    </div>
  );
}

GachaModal.propTypes = {
  gachaItem: string,
};
export default GachaModal;
