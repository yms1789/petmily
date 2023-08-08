import { arrayOf, string } from 'prop-types';

import { placeholderImage } from 'utils/utils';

const showItems = (contents, category) => {
  switch (category) {
    case '게시글':
      return (
        <>
          {contents.map(ele => (
            <div
              key={ele}
              className="w-full flex flex-col flex-1 items-start justify-start"
            >
              <div className="self-stretch flex flex-row pt-[0rem] px-[0rem] pb-[0.25rem] items-center justify-start gap-[0.25rem]">
                <div className="self-stretch flex flex-row items-center justify-start">
                  <div className="relative rounded-99980xl w-[49px] h-[49px] overflow-hidden shrink-0">
                    <img
                      className="absolute h-[97.96%] w-[97.96%] top-[2.04%] right-[2.04%] bottom-[0%] left-[0%] max-w-full overflow-hidden max-h-full object-cover rounded-[100px]"
                      alt=""
                      src={placeholderImage(
                        Math.floor(Math.random() * 1001) + 1,
                      )}
                    />
                  </div>
                </div>
                <div className="self-stretch flex flex-col gap-2 items-start justify-start text-gray">
                  <div className="flex flex-row gap-1">
                    <b className="relative text-gray text-xl">싸이어족</b>
                    <div className="w-fit flex flex-row gap-1 h-fit whitespace-nowrap pt-[0.05rem] text-[1.2rem] text-slategray">
                      <div className="relative font-medium">@catcat</div>
                      <div className="relative">{`· `}</div>
                      <div className="relative font-medium">23s</div>
                    </div>
                  </div>
                  <div className="flex-1 relative text-[1.2rem] font-medium">
                    집 가고 싶다....
                  </div>
                </div>
              </div>
              <div className="self-stretch rounded-2xl overflow-hidden flex flex-row py-[0.63rem] px-[0rem] items-start justify-center">
                <div className="relative rounded-2xl box-border w-[90%] px-5 h-[34.72vw] overflow-hidden shrink-0 border-[0.14vw] border-solid border-lightslategray">
                  <img
                    className="absolute top-[0vw] left-[0vw] w-full h-[34.72vw] object-cover"
                    alt=""
                    src={placeholderImage(Math.floor(Math.random() * 1001) + 1)}
                  />
                </div>
              </div>
              <div className="self-stretch overflow-hidden flex flex-row py-[0.25rem] px-[0rem] items-start justify-start text-[1rem] text-darkgray-100">
                <div className="relative w-[9.72vw] h-[2.5vw]">
                  <div className="absolute top-[0vw] left-[4.9vw] font-medium">
                    61
                  </div>
                  <img
                    className="absolute top-[-0.42vw] left-[0vw] w-6 h-6 overflow-hidden"
                    alt=""
                    src={placeholderImage(Math.floor(Math.random() * 1001) + 1)}
                  />
                </div>
                <div className="relative w-[9.72vw] h-[2.5vw] text-crimson">
                  <img
                    className="absolute top-[calc(50%_-_1.67vw)] left-[0vw] w-6 h-6 overflow-hidden"
                    alt=""
                    src={placeholderImage(Math.floor(Math.random() * 1001) + 1)}
                  />
                  <div className="absolute top-[-0.14vw] left-[5vw] font-medium">
                    6.2K
                  </div>
                </div>
              </div>
            </div>
          ))}
        </>
      );
    case '북마크':
      return 0;

    case '좋아요':
      return 1;
    default:
      return null;
  }
};

function MypageController({ contents, category }) {
  return <>{showItems(contents, category)}</>;
}
MypageController.propTypes = {
  contents: arrayOf(),
  category: string,
};
export default MypageController;
