import catIcon from '../static/images/catIcon.png';
import dogIcon from '../static/images/dogIcon.png';
import etcIcon from '../static/images/etcIcon.png';
import healthIcon from '../static/images/healthIcon.png';
import beautyIcon from '../static/images/beautyIcon.png';
import feedIcon from '../static/images/feedIcon.png';
import popularIcon from '../static/images/popularIcon.png';
import adoptionIcon from '../static/images/adoptionIcon.png';

const BACKEND_URL = '';

const icons = {
  인기: popularIcon,
  강아지: dogIcon,
  고양이: catIcon,
  기타동물: etcIcon,
  기타: etcIcon,
  건강: healthIcon,
  미용: beautyIcon,
  식품: feedIcon,
  입양: adoptionIcon,
};
const placeholderImage = number => {
  return `https://picsum.photos/1920/1000/?image=${number}`;
};
const validateEmail = email => {
  const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailPattern.test(email)) {
    return '유효한 이메일 주소를 입력해주세요.';
  }
  return '';
};
const validatePassword = inputPassword => {
  const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d).{8,}$/;
  if (!passwordPattern.test(inputPassword)) {
    return '비밀번호는 영문자와 숫자를 포함한 8자 이상이어야 합니다.';
  }
  return '';
};
const isSameCheck = (inputPassword, checkPassword) => {
  if (inputPassword !== checkPassword) {
    return '비밀번호를 다시 확인하세요.';
  }
  return '';
};
const priceToString = price => {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
};
export {
  icons,
  placeholderImage,
  isSameCheck,
  BACKEND_URL,
  validateEmail,
  validatePassword,
  priceToString,
};
