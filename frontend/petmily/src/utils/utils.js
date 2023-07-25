const BACKEND_URL = '';

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
export { isSameCheck, BACKEND_URL, validateEmail, validatePassword };
