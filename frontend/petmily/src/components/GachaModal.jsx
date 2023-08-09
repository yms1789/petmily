// import { Player } from '@lottiefiles/react-lottie-player';

import { string } from 'prop-types';

// import gacha from 'static/animations/gacha.json';

function GachaModal({ gachaItem }) {
  return (
    <div className="relative rounded-[10px] bg-inherit w-[656px] h-[450px] max-w-full flex flex-col justify-center items-center bg-white max-h-full text-left text-xl text-darkgray font-pretendard">
      {gachaItem} 가챠 뽑았다!
    </div>
  );
}

GachaModal.propTypes = {
  gachaItem: string,
};

export default GachaModal;
