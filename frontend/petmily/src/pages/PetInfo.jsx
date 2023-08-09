import { useState } from 'react';

import ArrowDropDownOutlinedIcon from '@mui/icons-material/ArrowDropDownOutlined';
import { styled } from '@mui/material';
import { string } from 'prop-types';
import { useRecoilState, useRecoilValue } from 'recoil';
import userAtom from 'states/users';
import createimageAtom from 'states/createimage';
import { UploadImage } from 'components';
import logo from 'static/images/logo.svg';
import useFetch from 'utils/fetch';

function PetInfo({ page }) {
  const StyledArrowDropDownOutlinedIcon = styled(ArrowDropDownOutlinedIcon, {
    name: 'StyledArrowDropDownOutlinedIcon',
    slot: 'Wrapper',
  })({
    color: '#a6a7ab',
    fontSize: 40,
    '&:hover': { color: '#1f90fe' },
  });

  const [createUploadedImage] = useRecoilState(createimageAtom);
  const [petName, setPetName] = useState('');
  const [petSpeices, setPetSpeices] = useState('');
  const [petGender, setPetGender] = useState('');
  const [petBirth, setPetBirth] = useState(0);
  const [petIntro, setPetIntro] = useState('');
  const [petBirthError, setPetBirthError] = useState('');
  const fetchPet = useFetch();

  const userLogin = useRecoilValue(userAtom);

  const checkForm = () => {
    if (
      petName &&
      petSpeices &&
      petGender &&
      petBirth.length === 8 &&
      petIntro
    ) {
      return true;
    }
    return false;
  };

  const onChangePetname = e => {
    setPetName(e.target.value);
  };
  const onChangePetspecies = e => {
    setPetSpeices(e.target.value);
  };
  const onChangePetgender = e => {
    setPetGender(e.target.value);
  };
  const onChangePetbirth = e => {
    const input = e.target.value;

    if (input.length > 8) {
      e.target.value = input.slice(0, 8);
    }
    if (input.length !== 8) {
      setPetBirthError('유효하지 않은 생년월일 입니다.');
      return;
    }

    const year = input.slice(0, 4);
    const month = input.slice(4, 6);
    const day = input.slice(6);

    if (
      parseInt(year, 10) < 1900 ||
      parseInt(month, 10) < 1 ||
      parseInt(month, 10) > 12 ||
      parseInt(day, 10) < 1 ||
      parseInt(day, 10) > 31
    ) {
      setPetBirthError('유효하지 않은 생년월일입니다.');
      return;
    }
    setPetBirthError('유효한 생년월일입니다.');

    setPetBirth(e.target.value);
  };
  const onChangePetintro = e => {
    setPetIntro(e.target.value);
  };

  const handlePetinfo = async (
    currentPetImage,
    currentPetName,
    currentPetSpeices,
    currentPetGender,
    currentPetBirth,
    currentPetIntro,
    e,
  ) => {
    e.preventDefault();

    const petInfoEditDto = {
      userEmail: userLogin,
      petName: currentPetName,
      petGender: currentPetGender,
      petInfo: currentPetIntro,
      petBirth: Number(currentPetBirth),
      speciesName: currentPetSpeices,
    };

    const formData = new FormData();

    formData.append(
      'petInfoEditDto',
      new Blob([JSON.stringify(petInfoEditDto)], {
        type: 'application/json',
      }),
    );

    formData.append('file', currentPetImage);

    try {
      const response = await fetchPet.post('pet/save', formData, 'image');
      console.log(response);
    } catch (error) {
      console.log('error', error);
    }
  };

  return (
    <div
      className={`${
        page ? 'h-fill rounded-lg' : 'h-[100vh]'
      } flex justify-center items-start bg-white w-full touch-none text-left text-[1rem] text-gray font-pretendard`}
    >
      <div
        className={`${
          page ? 'rounded-lg py-[5rem]' : 'top-0 py-[3rem]'
        } absolute flex flex-col box-border items-center justify-center bg-white w-full h-fill gap-[3rem]`}
      >
        {page ? null : (
          <div className="flex justify-center items-start w-[8rem] pb-3">
            <img className="w-[8rem]" alt="" src={logo} />
          </div>
        )}
        <div className="w-[36rem] flex justify-start">
          <b className="text-[1.6rem]">
            {page ? '반려동물 정보 수정' : '반려동물 정보 설정'}
          </b>
        </div>
        <UploadImage />
        <div className="flex flex-col items-start justify-center">
          <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
            <b className="text-[1.4rem]">반려동물 이름</b>
            <b className="flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
              반려동물의 이름을 입력해주세요
            </b>
            <div className="relative self-stretch flex flex-row items-center justify-center gap-[1rem] text-darkgray">
              <input
                className="flex-1 rounded-3xs box-border h-[3rem] flex flex-row px-[1rem] items-center justify-start border-[1px] border-solid border-darkgray focus:outline-none w-full 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
                type="text"
                placeholder="반려동물 이름"
                onChange={e => {
                  onChangePetname(e);
                }}
              />
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start justify-center">
          <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
            <b className="text-[1.4rem]">반려동물 종</b>
            <b className="flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
              반려동물의 종을 입력해주세요
            </b>
            <input
              className="rounded-3xs box-border h-[3rem] flex flex-row px-[1rem] items-center justify-start border-[1px] border-solid border-darkgray focus:outline-none w-full 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
              type="text"
              placeholder="ex) 햄스터"
              onChange={e => {
                onChangePetspecies(e);
              }}
            />
          </div>
        </div>
        <div className="flex flex-col items-start justify-center">
          <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
            <b className="text-[1.4rem]">반려동물 생일</b>
            <b className="flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
              반려동물의 생일을 입력해주세요
            </b>
            <input
              type="number"
              placeholder="생년월일 8자리"
              onChange={e => {
                onChangePetbirth(e);
              }}
              maxLength="8"
              className="w-[17.5rem] rounded-3xs box-border h-[3rem] px-[1rem] items-center justify-between border-[1px] border-solid border-darkgray focus:outline-none 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
            />
          </div>
          <div className="m-3">
            {petBirthError && (
              <span
                className={`${
                  petBirthError === '유효한 생년월일입니다.'
                    ? 'text-dodgerblue'
                    : 'text-red'
                }  text-base w-full`}
              >
                {petBirthError}
              </span>
            )}
          </div>
        </div>
        <div className="relative flex flex-col items-start justify-center">
          <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
            <b className="text-[1.4rem]">반려동물 성별</b>
            <b className="flex text-slategray items-center w-fill h-[1.56rem] shrink-0">
              반려동물의 성별을 선택해주세요
            </b>
            <select
              name=""
              id=""
              onChange={e => {
                onChangePetgender(e);
              }}
              className="w-[17.5rem] appearance-none rounded-3xs box-border h-[3rem] px-[1rem] flex flex-row items-center justify-between border-[1px] border-solid border-darkgray focus:outline-none 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
            >
              <option value="0">성별</option>
              <option value="1">남</option>
              <option value="2">여</option>
            </select>
            <div className="absolute right-[19rem] bottom-1 flex items-center pointer-events-none">
              <StyledArrowDropDownOutlinedIcon className="w-5 h-5 text-gray-400" />
            </div>
          </div>
        </div>
        <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
          <b className="relative text-[1.4rem]">소개글</b>
          <b className="relative flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
            함께하고 있는 반려동물을 소개해주세요
          </b>
          <textarea
            name=""
            id=""
            cols="30"
            rows="10"
            placeholder="내용을 작성해주세요"
            onChange={e => {
              onChangePetintro(e);
            }}
            className="overflow-scroll resize-none font-medium w-full text-black rounded-3xs bg-white box-border h-[21.69rem] flex flex-row py-[1.31rem] px-[1.56rem] items-start justify-start border-[1px] border-solid border-darkgray focus:outline-none 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
          />
        </div>
        <div className="w-[36rem] h-[4.5rem]">
          <button
            type="submit"
            className={`${
              checkForm() ? ' bg-dodgerblue' : 'bg-darkgray'
            } rounded-full w-full h-[4.5rem]`}
            onClick={e => {
              handlePetinfo(
                createUploadedImage,
                petName,
                petSpeices,
                petGender,
                petBirth,
                petIntro,
                e,
              );
            }}
            disabled={!checkForm()}
          >
            <b className="flex justify-center items-center text-[1.5rem] text-white">
              {page ? '수정 완료' : '작성 완료'}
            </b>
          </button>
        </div>
      </div>
    </div>
  );
}

PetInfo.propTypes = {
  page: string,
};

export default PetInfo;
