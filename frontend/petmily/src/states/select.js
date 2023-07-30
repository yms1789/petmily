import { atom } from 'recoil';

const selectAtom = atom({
  key: 'select',
  default: '',
});

export default selectAtom;
