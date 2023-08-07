import { atom } from 'recoil';

const previewAtom = atom({
  key: 'preview',
  default: [],
});

export default previewAtom;
