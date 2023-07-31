import ArrowDropDownOutlinedIcon from '@mui/icons-material/ArrowDropDownOutlined';
import { styled } from '@mui/material';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios';
import { BACKEND_URL } from '../utils/utils';
import logo from '../static/images/logo.svg';
import UploadProfileImage from '../components/UploadProfileImage';

function PetInfo() {
  const StyledArrowDropDownOutlinedIcon = styled(ArrowDropDownOutlinedIcon, {
    name: 'StyledArrowDropDownOutlinedIcon',
    slot: 'Wrapper',
  })({
    color: '#a6a7ab',
    fontSize: 40,
    '&:hover': { color: '#1f90fe' },
  });
  const navigate = useNavigate();
  const [uploadedImage, setUploadedImage] = useState(null);
  const [petname, setPetname] = useState('');
  const [petgender, setPetgender] = useState(0);
  const [petbirth, setPetbirth] = useState(new Date());
  const [petintro, setPetintro] = useState('');

  const checkForm = () => {
    if (petname && petgender && petbirth && petintro) {
      return true;
    }
    return false;
  };

  const onChangePetname = e => {
    setPetname(e.target.value);
  };
  const onChangePetgender = e => {
    setPetgender(e.target.value);
  };
  const onChangePetbirth = e => {
    setPetbirth(e); // Tue Jul 04 2023 16:57:01 GMT+0900 (한국 표준시)
  };
  const onChangePetintro = e => {
    setPetintro(e.target.value);
  };

  const handlePetinfo = async (
    currentPetImage,
    currentPetname,
    currentPetgender,
    currentPetbirth,
    currentPetintro,
    e,
  ) => {
    // 백엔드에 반려동물 정보 전달
    e.preventDefault();
    console.log(
      'PetInfo',
      currentPetImage[0],
      currentPetname,
      currentPetgender,
      currentPetbirth,
      currentPetintro,
      currentPetImage[1],
    );
    const petData = {
      uploadedImage: currentPetImage[0],
      petname: currentPetname,
      petgender: currentPetgender,
      petbirth: currentPetbirth,
      petintro: currentPetintro,
    };
    const config = currentPetImage[1];
    try {
      const response = await axios.post(BACKEND_URL, petData, config);
      console.log(response);
      navigate('/');
    } catch (error) {
      console.log('error', error);
    }
  };

  return (
    <div className="flex justify-center items-center bg-white w-full h-full overflow-y-auto text-left text-[1rem] text-gray font-pretendard">
      <div className="flex flex-col p-[4rem] bg-white items-center justify-center gap-[3rem]">
        <div className="flex justify-center items-start w-[8rem] pb-3">
          <img className="w-[8rem]" alt="" src={logo} />
        </div>
        <b className="self-stretch text-[1.6rem]">반려동물 설정</b>
        <UploadProfileImage
          uploadedImage={uploadedImage}
          setUploadedImage={setUploadedImage}
        />
        <div className="w-full flex flex-col items-start justify-center gap-[1rem]">
          <b className="relative text-[1.4rem]">반려동물 이름</b>
          <b className="relative flex text-slategray items-center w-full h-full shrink-0">
            반려동물의 이름과 성별을 입력해주세요
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
            <select
              name=""
              id=""
              onChange={e => {
                onChangePetgender(e);
              }}
              className="appearance-none flex-1 rounded-3xs box-border h-[3rem] px-[1rem] flex flex-row items-center justify-between border-[1px] border-solid border-darkgray focus:outline-none w-full 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
            >
              <option value="0">성별</option>
              <option value="1">남</option>
              <option value="2">여</option>
            </select>
            <div className="absolute right-2 flex items-center pointer-events-none">
              <StyledArrowDropDownOutlinedIcon className="w-5 h-5 text-gray-400" />
            </div>
          </div>
        </div>
        <div className="flex flex-col items-start justify-center">
          <div className="w-[36rem] flex flex-col items-start justify-start gap-[1rem]">
            <b className="text-[1.4rem]">생일</b>
            <b className="flex text-slategray items-center w-[28.5rem] h-[1.56rem] shrink-0">
              반려동물의 생일을 입력해주세요
            </b>
            <DatePicker
              dateFormat="yyyy.MM.dd"
              shouldCloseOnSelect
              selected={petbirth}
              onChange={e => {
                onChangePetbirth(e);
              }}
              className="w-[17.5rem] rounded-3xs box-border h-[3rem] px-[1rem] items-center justify-between border-[1px] border-solid border-darkgray focus:outline-none 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
            />
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
            className="resize-none font-medium w-full text-black rounded-3xs bg-white box-border h-[21.69rem] flex flex-row py-[1.31rem] px-[1.56rem] items-start justify-start border-[1px] border-solid border-darkgray focus:outline-none 
              focus:border-dodgerblue focus:border-1.5 font-pretendard text-base"
          />
        </div>
        <div className="relative w-[35.44rem] h-[4.5rem]">
          <button
            type="submit"
            className={`${
              checkForm() ? ' bg-dodgerblue' : 'bg-darkgray'
            } absolute top-[0rem] left-[0rem] rounded-[50px] w-[35.44rem] h-[4.5rem]`}
            onClick={e => {
              handlePetinfo(
                uploadedImage,
                petname,
                petgender,
                petbirth,
                petintro,
                e,
              );
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

export default PetInfo;
