import AddToPhotosRoundedIcon from '@mui/icons-material/AddToPhotosRounded';
import { styled } from '@mui/material';
import { useRef } from 'react';
import { string, func } from 'prop-types';
import AWS from 'aws-sdk';

function UploadProfileImage({ uploadedImage, setUploadedImage }) {
  const StyledAddToPhotosRoundedIcon = styled(AddToPhotosRoundedIcon, {
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

  const fileInputRef = useRef(null);
  const handleImageClick = () => {
    fileInputRef.current.click();
  };

  // AWS S3에 업로드하는 함수를 정의
  const uploadFile = async file => {
    // AWS S3 설정
    AWS.config.update({
      region: process.env.REACT_APP_BUCKET_REGION,
      credentials: {
        accessKeyId: process.env.REACT_APP_BUCKET_ACCESS,
        secretAccessKey: process.env.REACT_APP_BUCKET_SECRET,
      },
    });

    const s3 = new AWS.S3();

    const uploadParams = {
      Bucket: process.env.REACT_APP_BUCKET_NAME,
      Key: file.name,
      Body: file,
      ACL: 'public-read', // 이미지를 public으로 업로드하여 URL 접근 가능하도록 설정
    };

    try {
      const data = await s3.upload(uploadParams).promise();
      console.log(data.Location); // 이미지 URL 확인
      return data.Location; // 업로드된 이미지의 URL 반환
    } catch (error) {
      console.error(error);
      return null;
    }
  };

  const handleImageUpload = e => {
    const file = e.target.files[0];
    if (!file || !(file instanceof Blob)) {
      console.error('올바른 파일을 선택해주세요');
      return;
    }

    const reader = new FileReader();
    reader.onload = async () => {
      // 이미지 데이터가 업데이트된 이후에 업로드를 진행합니다.
      const imageUrl = await uploadFile(file);
      // 이미지 URL을 setUploadedImage에 전달합니다.
      setUploadedImage(imageUrl);
    };
    reader.readAsDataURL(file);
  };

  return (
    <div className="relative grid justify-items-center w-full h-[10rem]">
      <div className="overflow-hidden flex justify-center items-center absolute top-[0rem] rounded-[50%] box-border w-[10rem] h-[10rem] bg-gray2">
        {uploadedImage ? (
          <img
            src={uploadedImage}
            alt="프로필 이미지"
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
        onChange={e => handleImageUpload(e)}
      />
      <StyledAddToPhotosRoundedIcon
        className="bg-dodgerblue border-solid border-dodgerblue hover:bg-white hover:ring absolute bottom-0 right-48 rounded-[50px] w-[4rem] h-[4rem] px-[0.5rem] py-[0.8rem]"
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
