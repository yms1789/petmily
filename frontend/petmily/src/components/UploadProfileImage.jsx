import AddToPhotosRoundedIcon from '@mui/icons-material/AddToPhotosRounded';
import { styled } from '@mui/material';
import { useRef } from 'react';
import { string, func } from 'prop-types';

function UploadProfileImage({ uploadedImage, setUploadedImage }) {
  const StyledAddToPhotosRoundedIcon = styled(AddToPhotosRoundedIcon, {
    name: 'StyledAddToPhotosRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#fff',
    fontSize: '2rem',
    width: '2.5rem',
    height: '2rem',
    cursor: 'pointer',
    '&:hover': { color: '#1f90fe' },
  });

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
        setUploadedImage(reader.result || null);
        resolve();
      };
    });
  };

  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  return (
    <div className="relative grid justify-items-center w-full h-[10rem]">
      <div className="overflow-hidden flex justify-center items-center absolute top-[0rem] rounded-[50%] box-border w-[10rem] h-[10rem] border-[0.18rem] border-solid border-dodgerblue">
        {uploadedImage ? (
          <img
            src={uploadedImage}
            alt="프로필 이미지"
            className="w-60 object-scale-down"
          />
        ) : null}
      </div>
      <input
        accept="image/*"
        multiple
        type="file"
        className="hidden"
        ref={fileInputRef}
        onChange={e => handleImageUpload(e)}
      />
      <StyledAddToPhotosRoundedIcon
        className="bg-dodgerblue border-solid border-dodgerblue hover:bg-white hover:ring absolute bottom-0 right-48 rounded-[50px] w-[4rem] h-[4rem] px-[0.7rem] py-[1rem]"
        onClick={handleImageClick}
      />
    </div>
  );
}

UploadProfileImage.propTypes = {
  uploadedImage: string,
  setUploadedImage: func,
};
export default UploadProfileImage;
