import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();
const authAtom = atom({
  key: 'auth',
  default: JSON.parse(localStorage.getItem('user')),
  //  ?? {
  //   accessToken: 'access',
  //   refreshToken: 'refresh',
  // },
  effects_UNSTABLE: [persistAtom],
});

export default authAtom;
