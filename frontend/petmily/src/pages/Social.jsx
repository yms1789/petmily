import { v4 as uuidv4 } from 'uuid';
import RefreshRoundedIcon from '@mui/icons-material/RefreshRounded';
import AddPhotoAlternateRoundedIcon from '@mui/icons-material/AddPhotoAlternateRounded';
import CheckRoundedIcon from '@mui/icons-material/CheckRounded';
import FavoriteRoundedIcon from '@mui/icons-material/FavoriteRounded';
import EditNoteRoundedIcon from '@mui/icons-material/EditNoteRounded';
import AddCircleOutlineRoundedIcon from '@mui/icons-material/AddCircleOutlineRounded';
import { styled } from '@mui/material';
import { useRef, useState } from 'react';
import UploadProfileImage from 'components/UploadProfileImage';
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
    color: '#A6A7AB',
    fontSize: 28,
    '&:hover': { color: '#f4245e' },
  });
  const StyledEditNoteRoundedIcon = styled(EditNoteRoundedIcon, {
    name: 'StyledEditNoteRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#A6A7AB',
    fontSize: 30,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledAddCircleOutlineRoundedIcon = styled(
    AddCircleOutlineRoundedIcon,
    {
      name: 'StyledAddCircleOutlineRoundedIcon',
      slot: 'Wrapper',
    },
  )({
    color: '#A6A7AB',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });
  const [uploadedImage, setUploadedImage] = useState([]);
  const fileInputRef = useRef(null);
  const handleImageUpload = e => {
    const file = e.target.files[0];
    if (!file || !(file instanceof Blob)) {
      console.error('올바른 파일을 선택해주세요.');
      return null;
    }
    const reader = new FileReader();
    reader.readAsDataURL(file);
    console.log('FILE', file);
    console.log('READER RESULT', reader.result);
    return new Promise(resolve => {
      reader.onload = () => {
        setUploadedImage(prevArray => [...prevArray, reader.result || null]);
        console.log(uploadedImage);
        // setUploadedImage(reader.result || null);
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
      <div className="basis-1/2 min-w-[400px] rounded-lg flex flex-col gap-4">
        <SearchBar page="소통하기" />
        <div className="rounded-xl bg-white w-full h-full flex flex-col items-center justify-center text-[1rem] text-black">
          <div className="flex flex-col gap-3 w-full my-4">
            <div className="flex justify-between w-full">
              <div className="font-semibold text-[1.25rem] mx-6">뉴 피드</div>
              <div className="mx-6">
                <StyledRefreshRoundedIcon />
              </div>
            </div>
            <span className="h-[0.06rem] w-full bg-gray2 inline-block" />
            <div className="flex flex-col px-[1rem] items-between justify-between">
              <div className="flex items-start">
                <div className="w-[3rem] h-[3rem] pr-4 overflow-hidden">
                  <img
                    className="rounded-full w-[3rem] h-[3rem] overflow-hidden object-cover"
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
                  className="resize-none font-medium w-full text-black mx-4 rounded-xl p-4 border-solid border-[2px] border-gray2 focus:outline-none focus:border-dodgerblue font-pretendard text-base"
                />
              </div>
              <UploadProfileImage page="소통하기" />
              <div className="ml-[4.5rem] mr-[1rem]">
                <div className="flex justify-start items-center bg-black w-fill h-fill object-cover rounded-lg box-border">
                  {Array.isArray(uploadedImage)
                    ? uploadedImage.map(file => {
                        return (
                          <div
                            key={uuidv4()}
                            className="w-[8rem] h-[8rem] bg-amber-400"
                          >
                            <img
                              src={file}
                              alt="업로드 이미지"
                              className="w-[8rem] object-scale-down"
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
                  className="rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white border-solid border-[2px] border-dodgerblue flex p-[1rem] mt-[0.6rem] items-center justify-center"
                >
                  <StyledAddPhotoAlternateRoundedIcon />
                </div>
                <div className="rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white bg-dodgerblue border-solid border-[2px] border-dodgerblue flex p-[1rem] ml-[0.4rem] mr-[1rem] mt-[0.6rem] items-center justify-center opacity-[1]">
                  <CheckRoundedIcon />
                </div>
              </div>
            </div>
            <span className="h-[0.06rem] w-full bg-gray2 inline-block" />

            <div className="flex flex-col px-[1rem] items-between justify-between">
              <div className="flex items-start">
                <div className="rounded-full overflow-hidden pr-2">
                  <img
                    className="rounded-full w-[3rem] h-[3rem] overflow-hidden object-cover"
                    alt=""
                    src={placeholderImage}
                  />
                </div>
                <div className="flex flex-col w-full gap-[0.5rem] mx-4">
                  <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
                    <b className="relative text-gray">Devon Lane</b>
                    <div className="relative font-medium">@johndue</div>
                    <div className="relative text-[0.94rem]">{`· `}</div>
                    <div className="relative font-medium">{`23s `}</div>
                  </div>
                  <div className="flex-1 relative font-medium">
                    우리집 강아지 커여웡
                  </div>
                  <div className="w-full">
                    <img
                      src={placeholderImage}
                      className="h-full w-full rounded-lg overflow-hidden"
                      alt=""
                    />
                  </div>
                  <div className="flex justify-start h-full gap-[0.2rem]">
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                    >
                      <StyledFavoriteRoundedIcon className="mt-1" />
                      <div>999</div>
                    </div>
                    <div
                      role="presentation"
                      className="gap-[0.5rem] rounded-full text-[1rem] w-fill h-[0.5rem] text-black flex p-[0.5rem] items-center justify-center"
                    >
                      <StyledEditNoteRoundedIcon className="mt-0.5" />
                      <div>999</div>
                    </div>
                  </div>
                  <span className="mt-1 h-[0.06rem] w-full bg-gray2 inline-block" />
                  <div className="flex items-start my-1">
                    <div className="w-[2.5rem] h-[2.5rem] mt-1">
                      <img
                        className="w-[2.5rem] h-[2.5rem] object-cover rounded-full overflow-hidden"
                        alt=""
                        src={placeholderImage}
                      />
                    </div>
                    <div className="flex flex-col gap-[0.4rem] mx-4 w-full">
                      <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
                        <b className="relative text-gray">Devon Lane</b>
                        <div className="relative font-medium">@johndue</div>
                      </div>
                      <div className="flex justify-between w-full font-pretendard text-base">
                        <div>우리집 강아지 커여웡</div>
                        <div className="text-slategray font-medium">{`23s `}</div>
                      </div>
                      <span className="mt-2 h-[0.06rem] w-full bg-gray2 inline-block" />
                      <div className="flex items-center my-1">
                        <div className="w-[2.5rem] h-[2.5rem]">
                          <img
                            className="w-[2.5rem] h-[2.5rem] object-cover rounded-full overflow-hidden"
                            alt=""
                            src={placeholderImage}
                          />
                        </div>
                        <div className="flex flex-col gap-[0.4rem] ml-4 w-full">
                          <div className="flex items-center justify-start gap-[0.3rem] text-slategray">
                            <b className="relative text-gray">Devon Lane</b>
                            <div className="relative font-medium">@johndue</div>
                          </div>
                          <div className="flex justify-between w-full font-pretendard text-base">
                            <div>우리집 강아지 커여웡</div>
                            <div className="text-slategray font-medium">{`23s `}</div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <span className="mb-2 mx-2 h-[0.02rem] w-fill bg-gray2 inline-block" />
                  <div className="gap-[0.5rem] flex justify-start items-center h-full w-full">
                    <div className="w-full border-solid border-[1px] border-gray2 relative flex items-center justify-between rounded-11xl bg-white max-w-full h-[3rem]">
                      <div className="absolute left-0 px-[0.6rem] h-[2rem] w-[2rem] rounded-full overflow-hidden">
                        <img
                          src={placeholderImage}
                          className="h-full w-full rounded-full overflow-hidden"
                          alt=""
                        />
                      </div>
                      <input
                        className=" focus:outline-none w-full h-auto focus:outline-dodgerblue py-[0.8rem] px-[3.5rem] focus:border-1.5 font-pretendard text-base
        lex items-center font-medium rounded-full"
                        placeholder="검색어를 입력하세요"
                      />
                      <StyledAddCircleOutlineRoundedIcon
                        className="absolute right-0  px-[1rem]"
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
