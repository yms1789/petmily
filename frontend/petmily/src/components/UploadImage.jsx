import { useEffect, useRef } from 'react';

import { v4 as uuidv4 } from 'uuid';
import AddPhotoAlternateRoundedIcon from '@mui/icons-material/AddPhotoAlternateRounded';
import AddToPhotosRoundedIcon from '@mui/icons-material/AddToPhotosRounded';
import { styled } from '@mui/material';
import { PropTypes, bool, string } from 'prop-types';
import { useRecoilState, useRecoilValue } from 'recoil';
import userAtom from 'states/users';
import createimageAtom from 'states/createimage';
import createpreviewAtom from 'states/createpreview';
import updateimageAtom from 'states/updateimage';
import updatepreviewAtom from 'states/updatepreview';
import { profiles } from 'utils/utils';

function UploadImage({ page }) {
  const StyledAddPhotoAlternateRoundedIconWrapper = styled(
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
  const StyledAddPhotoAlternateRoundedIconSmallWrapper = styled(
    AddPhotoAlternateRoundedIcon,
    {
      name: 'StyledCheckRoundedIcon',
      slot: 'Wrapper',
    },
  )({
    color: '#1f90fe',
    fontSize: 18,
    '&:hover': { color: '#1f90fe' },
  });
  const StyledAddToPhotosRoundedIconWrapper = styled(AddToPhotosRoundedIcon, {
    name: 'StyledAddToPhotosRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#fff',
    fontSize: '1.8rem',
    width: '2.3rem',
    height: '1.8rem',
    cursor: 'pointer',
    '&:hover': { color: '#1f90fe' },
  });

  const profile = profiles;
  const userLogin = useRecoilValue(userAtom);

  const fileInputRef = useRef(null);

  const [createUploadedImage, setCreateUploadedImage] =
    useRecoilState(createimageAtom);
  const [updateUploadedImage, setUpdateUploadedImage] =
    useRecoilState(updateimageAtom);
  const [createFilePreview, setCreateFilePreview] =
    useRecoilState(createpreviewAtom);
  const [updateFilePreview, setUpdateFilePreview] =
    useRecoilState(updatepreviewAtom);

  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  const handleFilePreview = file => {
    const reader = new FileReader();
    reader.onload = () => {
      if (page === '소통하기') {
        const isDuplicate = createFilePreview?.some(image => {
          return image.name === file.name || image.size === file.size;
        });
        if (isDuplicate) {
          console.error('중복된 사진입니다');
          return;
        }
        setCreateFilePreview(prevArray => [
          ...prevArray,
          reader.result || null,
        ]);
      } else if (page === '소통하기수정') {
        const isDuplicate = updateFilePreview.some(image => {
          return image.name === file.name || image.size === file.size;
        });
        if (isDuplicate) {
          console.error('중복된 사진입니다');
          return;
        }
        setUpdateFilePreview(prevArray => [
          ...prevArray,
          reader.result || null,
        ]);
      } else {
        setCreateFilePreview(reader.result || null);
      }
    };
    reader.readAsDataURL(file);
  };

  const handleImageUpload = file => {
    try {
      if (page === '소통하기') {
        const isDuplicate = createUploadedImage.some(image => {
          return image.name === file.name || image.size === file.size;
        });
        if (isDuplicate) {
          console.error('중복된 사진입니다');
          return;
        }
        setCreateUploadedImage(prevArray => [...prevArray, file || null]);
      } else if (page === '소통하기수정') {
        const isDuplicate = updateUploadedImage.some(image => {
          return image.name === file.name || image.size === file.size;
        });
        if (isDuplicate) {
          console.error('중복된 사진입니다');
          return;
        }
        setUpdateUploadedImage(prevArray => [...prevArray, file || null]);
      } else {
        setCreateUploadedImage(file || null);
      }
    } catch (error) {
      console.log(error);
    }
  };

  const handleFileChange = event => {
    event.preventDefault();
    const file = event.target.files[0];
    if (!file || !(file instanceof Blob)) {
      console.error('올바른 파일을 선택해주세요');
      return;
    }
    handleFilePreview(file);
    handleImageUpload(file);
  };

  useEffect(() => {
    if (!page) {
      setCreateFilePreview(null);
    }
  }, []);

  const uploadIamgeComponent = pageName => {
    switch (pageName) {
      case '소통하기':
        return (
          <>
            <div className="ml-[4.5rem] mr-[1rem] mt-[1rem]">
              <div className="overflow-hidden mt-2 h-full w-full flex flex-wrap justify-start items-center object-cover rounded-lg box-border gap-1">
                {Array.isArray(createFilePreview) &&
                  createFilePreview.map(file => {
                    return (
                      <div
                        key={uuidv4()}
                        className="basis-[32.8%] h-[14rem] overflow-hidden flex-shrink-0"
                      >
                        <img
                          src={file}
                          alt="업로드 이미지"
                          className="w-full h-full object-cover overflow-hidden rounded-lg"
                        />
                      </div>
                    );
                  })}
              </div>
            </div>

            <div className="relative flex justify-end">
              <input
                accept="image/*"
                multiple
                type="file"
                className="hidden"
                ref={fileInputRef}
                onChange={e => handleFileChange(e)}
              />
              <div
                role="presentation"
                onClick={handleImageClick}
                className="absolute -bottom-12 right-[5rem] cursor-pointer rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white border-solid border-[2px] border-dodgerblue flex p-[1rem] mt-[0.6rem] items-center justify-center"
              >
                <StyledAddPhotoAlternateRoundedIconWrapper />
              </div>
            </div>
          </>
        );
      case '소통하기수정':
        return (
          <>
            <div className="relative flex justify-start">
              <input
                accept="image/*"
                multiple
                type="file"
                className="hidden"
                ref={fileInputRef}
                onChange={e => handleFileChange(e)}
              />
              <div
                role="presentation"
                onClick={handleImageClick}
                className="absolute -top-[11rem] right-[6rem] cursor-pointer rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white border-solid border-[2px] border-dodgerblue flex px-[0.3rem] py-[0.6rem] items-center justify-center"
              >
                <StyledAddPhotoAlternateRoundedIconSmallWrapper />
              </div>
            </div>
            <div className="mb-1 w-full ">
              <div className="overflow-hidden mt-2 h-full w-full flex flex-wrap justify-start items-center object-cover rounded-lg box-border gap-1">
                {Array.isArray(updateFilePreview) &&
                  updateFilePreview.map(file => {
                    return (
                      <div
                        key={uuidv4()}
                        className="basis-[32.75%] h-[14rem] overflow-hidden flex-shrink-0"
                      >
                        <img
                          src={file}
                          alt="업로드 이미지"
                          className="w-full h-full object-cover overflow-hidden rounded-lg"
                        />
                      </div>
                    );
                  })}
              </div>
            </div>
          </>
        );
      case '메세지':
        return (
          <div>
            <div>하하</div>
            메세지
          </div>
        );
      default:
        return (
          <div className="relative grid justify-items-center w-full h-[10rem]">
            <div className="overflow-hidden flex justify-center items-center absolute top-[0rem] rounded-[50%] box-border w-[10rem] h-[10rem] bg-gray2">
              {createFilePreview ? (
                <img
                  src={createFilePreview}
                  alt=""
                  className="w-full h-full object-cover"
                />
              ) : (
                <img
                  src={userLogin ? userLogin.userProfileImg : profile}
                  alt=""
                  className="w-full h-full object-cover"
                />
              )}
            </div>
            <input
              accept="image/*"
              multiple
              type="file"
              className="hidden"
              ref={fileInputRef}
              onChange={handleFileChange}
            />
            <StyledAddToPhotosRoundedIconWrapper
              className="bg-dodgerblue border-solid border-dodgerblue hover:bg-white hover:ring absolute bottom-0 left-10 rounded-[50px] w-[4rem] h-[4rem] px-[0.5rem] py-[0.8rem]"
              onClick={handleImageClick}
            />
          </div>
        );
    }
  };

  return <div>{uploadIamgeComponent(page)}</div>;
}

UploadImage.propTypes = {
  page: PropTypes.oneOfType([string, bool]),
};

export default UploadImage;
