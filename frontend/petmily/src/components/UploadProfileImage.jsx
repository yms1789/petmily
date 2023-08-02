// /* eslint-disable no-restricted-syntax */
import { v4 as uuidv4 } from 'uuid';
import AddPhotoAlternateRoundedIcon from '@mui/icons-material/AddPhotoAlternateRounded';
import AddToPhotosRoundedIcon from '@mui/icons-material/AddToPhotosRounded';
import { styled } from '@mui/material';
import { useEffect, useRef, useState } from 'react';
import { PropTypes, string, func } from 'prop-types';
// import AWS from 'aws-sdk';

function UploadProfileImage({ page, uploadedImage, setUploadedImage }) {
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
  const [filePreview, setFilePreview] = useState([]);
  const fileInputRef = useRef(null);
  const handleImageClick = () => {
    fileInputRef.current.click();
  };
  const handleFilePreview = file => {
    const reader = new FileReader();
    reader.onload = () => {
      if (page === '소통하기') {
        setFilePreview(prevArray => [...prevArray, reader.result || null]);
      } else {
        setFilePreview(reader.result);
      }
    };
    reader.readAsDataURL(file);
  };
  const handleImageUpload = file => {
    try {
      const formData = new FormData();
      formData.append('name', file.name);
      formData.append('image', file);
      // for (const x of formData.entries()) {
      //   console.log(x); //폼데이터에 잘 들어갔는지 출력
      // }
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      };
      if (page === '소통하기') {
        setUploadedImage(prevArray => [
          ...prevArray,
          [formData, config] || null,
        ]);
      } else {
        setUploadedImage([formData, config]);
      }
      console.log(uploadedImage);
    } catch (error) {
      console.log(error);
    }
  };
  const handleFileChange = event => {
    event.preventDefault();
    const file = event.target.files[0];
    if (!file || !(file instanceof Blob)) {
      console.error('올바른 파일을 선택해주세요');
    } else {
      handleFilePreview(file);
      handleImageUpload(file);
    }
  };
  useEffect(() => {
    console.log(uploadedImage);
  }, [uploadedImage]);

  const uploadIamgeComponent = pageName => {
    switch (pageName) {
      case '소통하기':
        return (
          <>
            <div className="ml-[4.5rem] mr-[1rem]">
              <div className="overflow-hidden mt-2 h-full w-full flex flex-wrap justify-start items-center object-cover rounded-lg box-border gap-1">
                {Array.isArray(filePreview) &&
                  filePreview.map(file => {
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
              {filePreview ? (
                <img
                  src={filePreview}
                  alt="프로필 이미지 미리보기"
                  className="w-full h-full object-cover"
                />
              ) : null}
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

UploadProfileImage.propTypes = {
  page: string,
  uploadedImage: PropTypes.arrayOf(
    PropTypes.oneOfType([
      PropTypes.instanceOf(FormData),
      PropTypes.shape({
        headers: PropTypes.shape({
          'Content-Type': string,
        }),
      }),
    ]),
  ),
  setUploadedImage: func,
};

export default UploadProfileImage;
