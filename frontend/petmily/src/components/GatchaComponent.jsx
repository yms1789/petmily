import { string, number } from 'prop-types';
import { ReactComponent as StarCoin } from 'static/images/starCoin.svg';

function GatchaComponent({ itemTitle, price }) {
  return (
    <div className="self-stretch flex-1 rounded-11xl bg-white flex flex-col p-6 items-center justify-start gap-[12px]">
      <StarCoin width={255} height={255} />
      <div className="self-stretch flex flex-row items-center justify-start">
        <StarCoin width={50} height={50} />
        <div className="relative tracking-[0.01em] leading-[125%] font-semibold text-dodgerblue">
          +{price}
        </div>
      </div>
      <span className="relative w-full text-base ml-5 tracking-[0.01em] leading-[125%] font-bold">
        {itemTitle} 랜덤 뽑기
      </span>
    </div>
  );
}

GatchaComponent.propTypes = {
  itemTitle: string,
  price: number,
};

export default GatchaComponent;
