import { format, formatDistanceToNow } from 'date-fns';
import { ko } from 'date-fns/locale';
import adoptionIcon from '../static/images/adoptionIcon.png';
import beautyIcon from '../static/images/beautyIcon.png';
import catIcon from '../static/images/catIcon.png';
import dogIcon from '../static/images/dogIcon.png';
import etcIcon from '../static/images/etcIcon.png';
import feedIcon from '../static/images/feedIcon.png';
import healthIcon from '../static/images/healthIcon.png';
import popularIcon from '../static/images/popularIcon.png';
import profileCat from '../static/images/profilecat.png';
import profileDog from '../static/images/profiledog.png';

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
const profileImage = [profileCat, profileDog];
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
const formatDate = date => {
  const d = new Date(date);
  const now = Date.now();
  const diff = (now - d.getTime()) / 1000; // 현재 시간과의 차이(초)
  if (diff < 60 * 1) {
    // 1분 미만일땐 방금 전 표기
    return '방금 전';
  }
  if (diff < 60 * 60 * 24 * 3) {
    // 3일 미만일땐 시간차이 출력(몇시간 전, 몇일 전)
    return formatDistanceToNow(d, { addSuffix: true, locale: ko });
  }
  return format(d, 'PPP EEE p', { locale: ko }); // 날짜 포맷
};

const gachaButtons = [
  [
    { itemTitle: 'ALL', price: 10 },
    { itemTitle: '프로필 링', price: 20 },
  ],
  [
    { itemTitle: '뱃지', price: 20 },
    { itemTitle: '커버 이미지', price: 30 },
  ],
];

export {
  BACKEND_URL,
  formatDate,
  gachaButtons,
  icons,
  isSameCheck,
  placeholderImage,
  priceToString,
  profileImage,
  validateEmail,
  validatePassword,
};
