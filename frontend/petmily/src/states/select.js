import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();
const selectAtom = atom({
  key: 'select',
  default: '',
  effects_UNSTABLE: [persistAtom], // 새로고침 시에도 atom 데이터가 유지될 수 있도록하기 위함
});

export default selectAtom;
