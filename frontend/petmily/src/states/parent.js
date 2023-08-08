import { atom } from 'recoil';

const parentAtom = atom({
  key: 'parentAtom',
  default: 0,
});

export default parentAtom;
