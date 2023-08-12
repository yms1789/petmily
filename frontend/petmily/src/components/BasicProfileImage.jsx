import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import userAtom from 'states/users';
import { profileImage, profiles } from 'utils/utils';

function BasicProfileImage() {
  const [userLogin, setUser] = useRecoilState(userAtom);

  useEffect(() => {
    if (userLogin && !userLogin.userProfileImg && !userLogin.BasicProfileImg) {
      if (userLogin.userLikePet === '고양이') {
        setUser({
          ...userLogin,
          BasicProfileImg: profileImage[0],
        });
      }
      if (userLogin.userLikePet === '강아지') {
        setUser({
          ...userLogin,
          BasicProfileImg: profileImage[1],
        });
      }
      setUser({
        ...userLogin,
        BasicProfileImg: profiles,
      });
    }
  }, []);

  return (
    userLogin &&
    userLogin.BasicProfileImg && (
      <img
        className="rounded-full w-[3rem] h-[3rem] overflow-hidden object-cover"
        alt=""
        src={userLogin.BasicProfileImg}
      />
    )
  );
}

export default BasicProfileImage;
