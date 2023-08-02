import { atom } from 'recoil';

const authAtom = atom({
  key: 'auth',
  default: JSON.parse(localStorage.getItem('user')),
  //  ?? {
  //   accessToken: 'access',
  //   refreshToken: 'refresh',
  // },
});

export default authAtom;
