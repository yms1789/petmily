import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import axios from 'axios';
import { UploadImage } from 'components';
import logo from 'static/images/logo.svg';

function UserInfo() {
  const navigate = useNavigate();
  const [uploadedImage, setUploadedImage] = useState(null);
  const [username, setUsername] = useState('');
  const [userlike, setUserlike] = useState('');
  const [visibleUsernameError, setVisibleUsernameError] = useState(false);
  const [usernameError, setUsernameError] = useState('');
  const [usernameSuccess, setUsernameSuccess] = useState(false);
  const [isButtonDisabled, setIsButtonDisabled] = useState(true);

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
    setIsButtonDisabled(newUsername.length < 1 || newUsername.length > 8);
    setVisibleUsernameError(true);
    setUsernameSuccess(false);
  };

  const handleDoubleCheck = async (currentUsername, e) => {
    e.preventDefault();

    const sendBE = {
      userNickname: currentUsername,
    };
    try {
      const response = await axios.post('nickname/check', sendBE);
      console.log(response);
      if (response.status === 200) {
        setUsernameSuccess(true);
      } else if (response.status === 401) {
        setUsernameError('중복된 닉네임입니다.');
        setVisibleUsernameError(true);
      } else {
        setUsernameError('다시 시도해주세요.');
        setVisibleUsernameError(true);
      }
    } catch (error) {
      setUsernameError('다시 시도해주세요.');
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
    currentUserlike,
    e,
  ) => {
    e.preventDefault();

    const userInfoEditDto = {
      userEmail: 'poiuy@naver.com',
      userNickname: currentUsername,
      userLikePet: currentUserlike,
    };

    const formData = new FormData();

    formData.append(
      'userInfoEditDto',
      new Blob([JSON.stringify(userInfoEditDto)], {
        type: 'application/json',
      }),
    );

    formData.append('file', currentUserImage);

    try {
      const response = await axios.patch('mypage/edit', formData, 'image');
      console.log(response);
      if (response.status === 200) {
        navigate('/');
      }
    } catch (error) {
      console.log('error', error);
    }
  };

  return (
    <div className="flex justify-center items-center bg-white w-full h-full touch-none text-left text-[1rem] text-gray font-pretendard">
      <div className="flex flex-col p-[3rem] box-border items-center justify-center gap-[3rem]">
        <div className="flex justify-center items-start w-[8rem]">
          <img className="w-[8rem]" alt="" src={logo} />
        </div>
        <b className="self-stretch text-[1.6rem]">개인정보 설정</b>
        <UploadImage
          uploadedImage={uploadedImage}
          setUploadedImage={setUploadedImage}
        />
        <div className="w-full flex flex-col items-start justify-center gap-[1rem]">
          <b className="relative text-[1.4rem]">닉네임</b>
          <b className="relative flex text-slategray items-center w-full h-full shrink-0">
            사용할 닉네임을 입력해주세요
          </b>
          <div className="relative self-stretch flex flex-row items-center justify-center gap-[1rem] text-darkgray">
            <input
              className="flex-1 box-border h-[3rem] flex flex-row px-[1rem] items-center justify-start focus:outline-none w-full
              rounded-3xs border-solid border-[1px] border-darkgray 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
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
          {usernameSuccess && (
            <span className="text-dodgerblue text-base w-full">
              사용할 수 있는 닉네임입니다.
            </span>
          )}
          {visibleUsernameError && (
            <span className="text-red text-base w-full">{usernameError}</span>
          )}
        </div>
        <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
          <b className="relative text-[1.4rem]">선호하는 반려동물</b>
          <b className="relative flex text-slategray items-center shrink-0">
            함께하고 싶은 반려동물이 있나요?
          </b>
          <div className="relative self-stretch flex flex-row items-center justify-center gap-[1rem] text-darkgray">
            <input
              className="flex-1 rounded-3xs box-border h-[3rem] flex flex-row px-[1rem] items-center justify-start border-[1px] border-solid border-darkgray focus:outline-none w-full 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
              type="text"
              placeholder="ex) 강아지, 고양이"
              onChange={e => {
                onChangeUserlike(e);
              }}
            />
          </div>
        </div>
        <div className="relative w-full h-[4.5rem] mt-5">
          <button
            type="submit"
            className={`${
              checkForm() ? ' bg-dodgerblue' : 'bg-darkgray'
            } absolute top-[0rem] left-[0rem] rounded-[50px] w-full h-[4.5rem]`}
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
