import RefreshRoundedIcon from '@mui/icons-material/RefreshRounded';
import AddPhotoAlternateRoundedIcon from '@mui/icons-material/AddPhotoAlternateRounded';
import CheckRoundedIcon from '@mui/icons-material/CheckRounded';
import FavoriteRoundedIcon from '@mui/icons-material/FavoriteRounded';
import ChatRoundedIcon from '@mui/icons-material/ChatRounded';
import { styled } from '@mui/material';
import { useRef, useState } from 'react';
import FollowRecommend from '../components/FollowRecommend';
import SearchBar from '../components/SearchBar';
import { placeholderImage } from '../utils/utils';

function Social() {
  const StyledRefreshRoundedIcon = styled(RefreshRoundedIcon, {
    name: 'StyledRefreshRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#0F1419',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledAddPhotoAlternateRoundedIcon = styled(
    AddPhotoAlternateRoundedIcon,
    {
      name: 'StyledCheckRoundedIcon',
      slot: 'Wrapper',
    },
  )({
    color: '#1f90fe',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledFavoriteRoundedIcon = styled(FavoriteRoundedIcon, {
    name: 'StyledFavoriteRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#1f90fe',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledChatRoundedIcon = styled(ChatRoundedIcon, {
    name: 'StyleChatRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#1f90fe',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const [placeholderData, setUploadedImage] = useState([]);
  const fileInputRef = useRef(null);
  const handleImageUpload = e => {
    const file = e.target.files[0];
    if (!file || !(file instanceof Blob)) {
      console.error('올바른 파일을 선택해주세요.');
      return null;
    }
    const reader = new FileReader();
    reader.readAsDataURL(file);
    console.log(file);
    return new Promise(resolve => {
      reader.onload = () => {
        setUploadedImage(placeholderData.push(placeholderImage));
        if (placeholderData.length === 5) {
          setUploadedImage(null);
        }
        resolve();
      };
    });
  };
  const handleImageClick = () => {
    fileInputRef.current.click();
  };
  return (
    <div className="pb-[10rem] min-w-[1340px] max-w-full w-full absolute top-[6.5rem] flex justify-between">
      <div className="mx-4 basis-1/4 flex h-[100px] rounded-lg bg-white">d</div>
      <div className="basis-1/2 h-full rounded-lg flex flex-col gap-4">
        <SearchBar page="소통하기" />
        <div className="rounded-xl bg-white w-full h-full flex flex-col items-center justify-center text-[1rem] text-black">
          <div className="flex flex-col gap-4 w-full my-4">
            <div className="flex justify-between w-full">
              <div className="font-semibold text-[1.25rem] mx-6">뉴 피드</div>
              <div className="mx-6">
                <StyledRefreshRoundedIcon />
              </div>
            </div>
            <span className="h-[0.06rem] w-full bg-gray2 inline-block" />
            <div className="flex flex-col px-[1rem] items-between justify-between">
              <div className="flex items-start">
                <div className="flex justify-center items-center rounded-full w-[3rem] h-[3rem] overflow-hidden">
                  <img
                    className="h-full w-full object-cover"
                    alt=""
                    src={placeholderImage}
                  />
                </div>
                <textarea
                  name=""
                  id=""
                  cols="80"
                  rows="5"
                  placeholder="자유롭게 이야기 해보세요!"
                  className="resize-none font-medium text-black mx-4 rounded-xl p-4 border-solid border-[1px] border-gray2 focus:outline-none focus:outline-lightblue focus:border-1.5 font-pretendard text-base"
                />
              </div>
              <div className="ml-[4rem] mr-[1rem]">
                <div className="overflow-hidden flex justify-start items-center bg-black w-full h-full object-cover rounded-lg box-border">
                  {placeholderData
                    ? placeholderData.map(item => {
                        return (
                          <div>
                            <img
                              src={item}
                              alt="업로드 이미지"
                              className="w-60 object-scale-down"
                            />
                          </div>
                        );
                      })
                    : null}
                </div>
              </div>
              <div className="flex justify-end h-full">
                <input
                  accept="image/*"
                  multiple
                  type="file"
                  className="hidden"
                  ref={fileInputRef}
                  onChange={e => handleImageUpload(e)}
                />
                <div
                  role="presentation"
                  onClick={handleImageClick}
                  className="rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white border-solid border-[2px] border-dodgerblue flex p-[1rem] my-[1rem] items-center justify-center"
                >
                  <StyledAddPhotoAlternateRoundedIcon />
                </div>
                <div className="rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white bg-dodgerblue border-solid border-[2px] border-dodgerblue flex p-[1rem] m-[1rem] items-center justify-center opacity-[1]">
                  <CheckRoundedIcon />
                </div>
              </div>
            </div>
            <span className="h-[0.06rem] w-full bg-gray2 inline-block" />

            <div className="flex flex-col px-[1rem] items-between justify-between">
              <div className="flex items-start">
                <div className="flex justify-center items-center rounded-full w-[3rem] h-[3rem] overflow-hidden">
                  <img
                    className="h-full w-full object-cover"
                    alt=""
                    src={placeholderImage}
                  />
                </div>
                <div className="flex flex-col gap-[0.5rem] mx-4">
                  <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
                    <b className="relative text-gray">Devon Lane</b>
                    <div className="relative font-medium">@johndue</div>
                    <div className="relative text-[0.94rem]">{`· `}</div>
                    <div className="relative font-medium">{`23s `}</div>
                  </div>
                  <div className="flex-1 relative font-medium">
                    우리집 강아지 커여웡
                  </div>
                  <div>
                    <img
                      src={placeholderImage}
                      className="h-full w-full rounded-lg overflow-hidden"
                      alt=""
                    />
                  </div>
                  <div className="flex justify-start h-full gap-[1rem]">
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-gray border-solid border-[2px] border-dodgerblue flex p-[1rem] items-center justify-center"
                    >
                      <StyledFavoriteRoundedIcon />
                      <div>999</div>
                    </div>
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-gray border-solid border-[2px] border-dodgerblue flex p-[1rem] items-center justify-center"
                    >
                      <StyledChatRoundedIcon />
                      <div>999</div>
                    </div>
                  </div>
                  <span className="mt-3 h-[0.06rem] w-full bg-gray2 inline-block" />
                  <div className="flex items-start my-2">
                    <div className="w-[3rem] h-[3rem]">
                      <img
                        className="w-[3rem] h-[3rem] object-cover rounded-full overflow-hidden"
                        alt=""
                        src={placeholderImage}
                      />
                    </div>
                    <div className="flex flex-col gap-[0.5rem] mx-4 w-full">
                      <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
                        <b className="relative text-gray">Devon Lane</b>
                        <div className="relative font-medium">@johndue</div>
                        <div className="relative text-[0.94rem]">{`· `}</div>
                        <div className="relative font-medium">{`23s `}</div>
                      </div>
                      <div className="flex justify-between w-full font-medium">
                        <div>우리집 강아지 커여웡</div>
                        <div className="text-slategray font-medium">{`23s `}</div>
                      </div>
                      <span className="mt-3 h-[0.06rem] w-full bg-gray2 inline-block" />
                      <div className="flex items-start my-2">
                        <div className="w-[3rem] h-[3rem]">
                          <img
                            className="w-[3rem] h-[3rem] object-cover rounded-full overflow-hidden"
                            alt=""
                            src={placeholderImage}
                          />
                        </div>
                        <div className="flex flex-col gap-[0.5rem] ml-4 w-full">
                          <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
                            <b className="relative text-gray">Devon Lane</b>
                            <div className="relative font-medium">@johndue</div>
                            <div className="relative text-[0.94rem]">{`· `}</div>
                            <div className="relative font-medium">{`23s `}</div>
                          </div>
                          <div className="flex justify-between w-full font-medium">
                            <div>우리집 강아지 커여웡</div>
                            <div className="text-slategray font-medium">{`23s `}</div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="gap-[1rem] flex justify-start items-center h-full w-full">
                    <div className="w-full border-solid border-[1px] border-gray2 relative flex items-center justify-between rounded-11xl bg-white max-w-full h-[60px]">
                      <div className="absolute left-0 px-[1rem] h-[2.5rem] w-[2.5rem] rounded-full overflow-hidden">
                        <img
                          src={placeholderImage}
                          className="h-full w-full rounded-full overflow-hidden"
                          alt=""
                        />
                      </div>
                      <input
                        className=" focus:outline-none w-full h-auto focus:outline-dodgerblue py-[1rem] px-[5rem] focus:border-1.5 font-pretendard text-base
        lex items-center font-medium rounded-full"
                        placeholder="검색어를 입력하세요"
                      />
                      <StyledChatRoundedIcon
                        className="absolute right-0  px-[1.5rem]"
                        onClick={() => {
                          console.log('click');
                        }}
                      />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <FollowRecommend />
    </div>
  );
}

export default Social;
