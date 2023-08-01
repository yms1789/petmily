import { atom, selector } from 'recoil';

const curationsAtom = atom({
  key: 'curations',
  default: {
    dog: Array.from({ length: 5 }, (_, i) => ({
      ctitle: `ctitle${i}`,
      clink: `clink${i}`,
      ccontent: `ccontent${i}`,
      cimage: `cimage${i}`,
    })),

    cat: Array.from({ length: 5 }, (_, i) => ({
      ctitle: `ctitle${i}`,
      clink: `clink${i}`,
      ccontent: `ccontent${i}`,
      cimage: `cimage${i}`,
    })),
    etc: Array.from({ length: 5 }, (_, i) => ({
      ctitle: `ctitle${i}`,
      clink: `clink${i}`,
      ccontent: `ccontent${i}`,
      cimage: `cimage${i}`,
    })),
  },
});

// Selector를 사용하여 카테고리에 따라 해당 동물 리스트에 항목을 추가
const addToCuration = selector({
  key: 'addToCuration',
  get: ({ get }) => {
    // get 함수를 이용하여 현재 상태의 값을 얻어올 수 있습니다.
    return get(curationsAtom);
  },
  set: ({ get, set }, { category, item }) => {
    console.log(category, item);
    const curations = get(curationsAtom);
    switch (category) {
      case '강아지':
        set(curationsAtom, {
          ...curations,
          dog: [...curations.dog, item],
        });
        break;
      case '고양이':
        set(curationsAtom, {
          ...curations,
          cat: [...curations.cat, item],
        });
        break;
      case '기타동물':
        set(curationsAtom, {
          ...curations,
          etc: [...curations.etc, item],
        });
        break;
      default:
        break;
    }
  },
});

export { curationsAtom, addToCuration };
