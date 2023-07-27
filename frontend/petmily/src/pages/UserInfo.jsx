import AddToPhotosRoundedIcon from '@mui/icons-material/AddToPhotosRounded';
import PetsRoundedIcon from '@mui/icons-material/PetsRounded';
import { styled } from '@mui/material';
import { useRef, useState } from 'react';
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios';
import { BACKEND_URL } from '../utils/utils';
import logo from '../static/images/logo.svg';

function UserInfo() {
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
  const StyledPetsRoundedIcon = styled(PetsRoundedIcon, {
    name: 'StyledPetsRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#1f90fe',
    fontSize: '2rem',
    width: '2.5rem',
    height: '2rem',
  });
  const [uploadedImage, setUploadedImage] = useState(null);
  const [username, setUsername] = useState('');
  const [userlike, setUserlike] = useState('');
  const [visibleUsernameError, setVisibleUsernameError] = useState(false);
  const [usernameError, setUsernameError] = useState('');
  const [isButtonDisabled, setIsButtonDisabled] = useState(true); // Initialize the button as disabled
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

  const checkForm = () => {
    return username && !isButtonDisabled;
  };

  const onChangeUsername = e => {
    const newUsername = e.target.value;
    if (newUsername.length > 8) {
      setUsernameError('닉네임은 8글자 이하로 입력해주세요.');
    } else {
      setUsername(newUsername);
      setUsernameError('');
    }
    setIsButtonDisabled(newUsername.length < 1 || newUsername.length > 8); // Update the button disabled state directly
    setVisibleUsernameError(false);
  };

  const handleDoubleCheck = async (currentUsername, e) => {
    e.preventDefault();
    console.log(username);
    try {
      const response = await axios.post(BACKEND_URL, currentUsername);
      console.log(response);
      if (response.data.exists) {
        setUsernameError('중복된 닉네임으로 사용할 수 없습니다.');
      } else {
        setUsernameError('');
      }
    } catch (error) {
      setVisibleUsernameError(true);
      console.log('error', error);
    }
  };

  const onChangeUserlike = e => {
    setUserlike(e.target.value);
  };

  const handleUserinfo = async (
    currentUserImage,
    currentUsername,
    currentUserAddress,
    currentUserlike,
    e,
  ) => {
    // 백엔드에 반려동물 정보 전달
    e.preventDefault();
    console.log(
      'UserInfo',
      currentUserImage,
      currentUsername,
      currentUserAddress,
      currentUserlike,
    );
    const userData = {
      uploadedImage,
      username,
      userlike,
    };
    try {
      const response = await axios.post(BACKEND_URL, userData);
      console.log(response);
    } catch (error) {
      console.log('error', error);
    }
  };

  return (
    <div className="relative bg-white w-full h-[111rem] overflow-y-auto text-left text-[1.25rem] text-gray font-pretendard">
      <div className="absolute top-[calc(50%_-_771px)] left-[calc(50%_-_332px)] rounded-[40px] bg-white w-[41.5rem] flex flex-col p-[2.5rem] box-border items-center justify-start gap-[3.63rem]">
        <div className="relative w-[12.31rem] h-[13.38rem] text-[3.13rem] text-dodgerblue">
          <img
            className="absolute top-[0rem] left-[0rem] w-[12rem]"
            alt=""
            src={logo}
          />
        </div>
        <b className="self-stretch relative text-[2rem] tracking-[0.01em] leading-[125%]">
          개인정보 설정
        </b>
        <div className="relative grid justify-items-center w-full h-[10rem]">
          <div className="overflow-hidden flex justify-center items-center absolute top-[0rem] rounded-[50%] box-border w-[10rem] h-[10rem] border-[0.2rem] border-solid border-dodgerblue">
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
        <div className="w-[36rem] flex flex-col items-start justify-center gap-[1rem]">
          <b className="relative text-[1.5rem] tracking-[0.01em] leading-[125%]">
            닉네임
          </b>
          <b className="relative tracking-[0.01em] leading-[125%] flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
            사용할 닉네임을 입력해주세요
          </b>
          <div className="relative self-stretch flex flex-row items-center justify-center gap-[1rem] text-darkgray">
            <input
              className="flex-1 rounded-3xs box-border h-[3rem] flex flex-row px-[1rem] items-center justify-start border-[1.5px] border-solid border-darkgray"
              type="text"
              placeholder="8글자 이하 닉네임을 사용할 수 있어요"
              onChange={e => {
                setVisibleUsernameError(false);
                onChangeUsername(e);
              }}
            />
            <button
              type="button"
              onClick={e => {
                handleDoubleCheck(username, e);
              }}
              className={`rounded-31xl text-white bg-dodgerblue overflow-hidden flex flex-row py-2.5 px-4 items-center justify-center text-[1.2rem] ${
                isButtonDisabled
                  ? 'opacity-50 cursor-not-allowed'
                  : 'cursor-pointer'
              }`}
              disabled={isButtonDisabled}
            >
              중복확인
            </button>
          </div>
          {usernameError && (
            <span className="text-red-500 text-base w-full">
              {usernameError}
            </span>
          )}
          {visibleUsernameError && (
            <span className="text-red-500 text-base w-full">
              중복된 닉네임으로 사용할 수 없습니다.
            </span>
          )}
        </div>
        <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
          <b className="relative text-[1.5rem] tracking-[0.01em] leading-[125%]">
            선호하는 반려동물
          </b>
          <b className="relative tracking-[0.01em] leading-[125%] flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
            함께하고 싶은 반려동물이 있나요?
          </b>
          <div className="relative self-stretch flex flex-row items-center justify-center gap-[1rem] text-darkgray">
            <input
              className="flex-1 rounded-3xs box-border h-[3rem] flex flex-row px-[1rem] items-center justify-start border-[1.5px] border-solid border-darkgray"
              type="text"
              placeholder="ex) 강아지, 고양이"
              onChange={e => {
                onChangeUserlike(e);
              }}
            />
          </div>
        </div>
        <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
          <b className="relative text-[1.5rem] tracking-[0.01em] leading-[125%]">
            키우고 있는 반려동물
          </b>
          <b className="relative tracking-[0.01em] leading-[125%] flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
            함께하고 있는 반려동물이 있나요?
          </b>
          <button
            type="submit"
            className="self-stretch rounded-3xs flex flex-row py-5 px-4 items-center justify-between text-dodgerblue border-[1px] border-solid border-dodgerblue"
            onClick={e => {
              handleUserinfo(uploadedImage, username, userlike, e);
            }}
          >
            <div className="flex items-center h-[30px]">
              <b className="text-[1.2rem]">반려동물 정보 입력하기</b>
            </div>
            <StyledPetsRoundedIcon />
          </button>
        </div>
        <div className="relative w-[35.44rem] h-[4.5rem]">
          <button
            type="submit"
            className={`${
              checkForm() ? ' bg-dodgerblue' : 'bg-darkgray'
            } absolute top-[0rem] left-[0rem] rounded-[50px] w-[35.44rem] h-[4.5rem]`}
            onClick={e => {
              handleUserinfo(uploadedImage, username, userlike, e);
            }}
            disabled={!checkForm()}
          >
            <b className="absolute top-[1.31rem] left-[calc(50%_-_45.5px)] tracking-[0.01em] leading-[125%] text-[1.5rem] text-white">
              작성 완료
            </b>
          </button>
        </div>
      </div>
    </div>
  );
}

export default UserInfo;
