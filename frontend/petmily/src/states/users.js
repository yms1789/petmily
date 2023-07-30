import { atom } from 'recoil';

const userAtom = atom({
  key: 'loginState',
  default: null,
});

export default userAtom;
