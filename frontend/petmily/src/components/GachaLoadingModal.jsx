import { func, string } from 'prop-types';
import { useEffect } from 'react';
import { Player } from '@lottiefiles/react-lottie-player';

import gacha from 'static/animations/gacha.json';

function GachaLoadingModal({ onClose, gachaOpen, gachaSelect, setGachaItem }) {
  useEffect(() => {
    // 서버에 뽑기 요청
    console.log('뽑기 요청');
    setTimeout(() => {
      onClose();
      console.log(gachaSelect);
      setGachaItem('뱃지12');
      gachaOpen();
    }, 4000);
  }, []);

  return (
    <div className="relative rounded-[10px] bg-inherit w-[656px] h-[450px] max-w-full flex flex-col justify-center items-center max-h-full text-left text-xl text-darkgray font-pretendard">
      <Player loop src={gacha} autoplay />
    </div>
  );
}

GachaLoadingModal.propTypes = {
  onClose: func,
  gachaOpen: func,
  gachaSelect: string,
  setGachaItem: func,
};

export default GachaLoadingModal;
