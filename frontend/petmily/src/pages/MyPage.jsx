import ArrowBackRoundedIcon from '@mui/icons-material/ArrowBackRounded';
import { styled } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import Messages from '../components/Messages';
import MyPetInfo from '../components/MyPetInfo';
import { placeholderImage } from '../utils/utils';

const posts = Array.from({ length: 5 }, (_, i) => i);

function MyPage() {
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate(-1);
  };

  const StyleBackRoundedIcon = styled(ArrowBackRoundedIcon, {
    name: 'StyleBackRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#1f90fe',
  });
  return (
    <div className="flex flex-row justify-center items-start relative bg-whitesmoke min-w-[1832px] max-w-full h-[1300px] overflow-y-auto text-left text-[1.13rem] text-dodgerblue font-pretendard">
      <div className="relative px-10 min-w-[1340px] max-w-full w-full top-[100px] flex flex-row items-start gap-4 text-gray">
        <Messages />
        <div className="flex basis-1/2 rounded-11xl min-w-[40%] bg-white h-[952px] flex-col py-[0.75rem] px-[0rem] box-border items-start justify-start text-[0.94rem]">
          <div
            role="presentation"
            className="overflow-hidden flex flex-col py-[0.75rem] px-[1.5rem] items-start justify-start cursor-pointer"
            onClick={handleGoBack}
          >
            <StyleBackRoundedIcon />
          </div>
          <div className="self-stretch flex flex-col pt-[8.44rem] px-[1.94rem] pb-[0.88rem] items-start justify-center relative gap-[1.25rem]">
            <div className="absolute my-0 mx-[!important] top-[0px] left-[1px] bg-white w-full h-[200px]">
              <img
                className="absolute top-[calc(50%_-_100px)] w-full h-[200px] object-cover"
                alt=""
                src={placeholderImage}
              />
            </div>
            <div className="flex flex-row w-full items-end justify-between z-[1] text-center text-dodgerblue">
              <div className="relative rounded-[100px] box-border w-[142px] h-[139px] overflow-hidden shrink-0 border-[4px] border-solid border-gray">
                <div className="absolute top-[calc(50%_-_69.5px)] left-[calc(50%_-_69px)] rounded-[100px] w-[139px] h-[139px] overflow-hidden">
                  <img
                    className="absolute top-[calc(50%_-_68.5px)] left-[calc(50%_-_68.5px)] w-[136.16px] h-[136.16px] object-cover"
                    alt=""
                    src={placeholderImage}
                  />
                </div>
              </div>
              <div
                className="rounded-[100px] box-border w-28 h-[39px] overflow-hidden shrink-0 flex 
              flex-row py-[0.94rem] px-[0.19rem] items-center justify-center border-[1px] border-solid border-dodgerblue"
              >
                <b className="flex-1 relative leading-[1.19rem]">
                  내 정보 수정
                </b>
              </div>
            </div>
            <div className="flex flex-col items-start justify-start z-[2] text-[1.31rem]">
              <div className="w-[125px] flex flex-col items-start justify-center">
                <b className="relative tracking-[-0.01em]">싸이어족</b>
                <div className="relative text-[1rem] tracking-[-0.02em] font-medium text-slategray">
                  saker@naver.com
                </div>
              </div>
            </div>
            <div className="flex flex-row items-center justify-start gap-[2.63rem] z-[3] text-[1rem]">
              <div className="flex flex-row items-start justify-start gap-[0.25rem]">
                <b className="relative">569</b>
                <div className="relative tracking-[-0.02em] font-medium text-slategray">
                  팔로잉
                </div>
              </div>
              <div className="flex flex-row items-start justify-start gap-[0.31rem]">
                <b className="relative">72</b>
                <div className="relative tracking-[-0.02em] font-medium text-slategray">
                  팔로워
                </div>
              </div>
            </div>
          </div>
          <div className="self-stretch flex flex-row py-[0.75rem] px-[0rem] w-full items-start justify-start text-[1rem] text-darkgray-100">
            <div className="flex-1 flex flex-col pt-[0.94rem] px-[0rem] pb-[0rem] items-center justify-start gap-[0.94rem] text-dodgerblue">
              <b className="relative">게시글</b>
              <div className="self-stretch relative bg-dodgerblue h-0.5" />
            </div>
            <div className="flex-1 flex flex-col pt-[0.94rem] px-[0rem] pb-[0rem] items-center justify-start gap-[0.94rem]">
              <b className="relative">좋아요</b>
              <div className="self-stretch relative bg-white h-0.5" />
            </div>
            <div className="flex-1 flex flex-col pt-[0.94rem] px-[0rem] pb-[0rem] items-center justify-start gap-[0.94rem]">
              <b className="relative">북마크</b>
              <div className="self-stretch relative bg-white h-0.5" />
            </div>
          </div>
          <div className="self-stretch w-full flex flex-col py-[0rem] px-[0.06rem] items-start justify-start gap-[0.56rem] text-slategray">
            <div className="relative w-full h-px">
              <div className="absolute top-[0px] left-[-1px] bg-dark-7 w-full h-px hidden" />
            </div>
            <div className="w-full flex flex-row py-[0rem] px-[0.94rem] box-border items-start justify-start gap-[0.63rem]">
              <div className="self-stretch flex flex-row items-start justify-start">
                <div className="relative rounded-99980xl w-[49px] h-[49px] overflow-hidden shrink-0">
                  <img
                    className="absolute h-[97.96%] w-[97.96%] top-[2.04%] right-[2.04%] bottom-[0%] left-[0%] max-w-full overflow-hidden max-h-full object-cover"
                    alt=""
                    src={placeholderImage}
                  />
                </div>
              </div>
              <div className="flex flex-col w-full">
                {posts.map(ele => (
                  <div
                    key={ele}
                    className="w-full flex flex-col flex-1 items-start justify-start"
                  >
                    <div className="self-stretch flex flex-row pt-[0rem] px-[0rem] pb-[0.25rem] items-center justify-start gap-[0.25rem]">
                      <b className="relative text-gray">싸이어족</b>
                      <div className="relative font-medium">@catcat</div>
                      <div className="relative">{`· `}</div>
                      <div className="relative font-medium">23s</div>
                    </div>
                    <div className="self-stretch flex flex-row items-start justify-start text-gray">
                      <div className="flex-1 relative font-medium">
                        집 가고 싶다....
                      </div>
                    </div>
                    <div className="self-stretch rounded-2xl overflow-hidden flex flex-row py-[0.63rem] px-[0rem] items-start justify-start">
                      <div className="relative rounded-2xl box-border w-[95%] h-[34.72vw] overflow-hidden shrink-0 border-[0.14vw] border-solid border-lightslategray">
                        <img
                          className="absolute top-[0vw] left-[0vw] w-full h-[34.72vw] object-cover"
                          alt=""
                          src={placeholderImage}
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
                          src={placeholderImage}
                        />
                      </div>
                      <div className="relative w-[9.72vw] h-[2.5vw] text-crimson">
                        <img
                          className="absolute top-[calc(50%_-_1.67vw)] left-[0vw] w-6 h-6 overflow-hidden"
                          alt=""
                          src={placeholderImage}
                        />
                        <div className="absolute top-[-0.14vw] left-[5vw] font-medium">
                          6.2K
                        </div>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
        <MyPetInfo />
      </div>
    </div>
  );
}

export default MyPage;
