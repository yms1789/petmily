import { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';

import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import BookmarkIcon from '@mui/icons-material/Bookmark';
import { arrayOf, number, shape, string } from 'prop-types';
import { useRecoilState } from 'recoil';
import { v4 as uuidv4 } from 'uuid';
import userAtom from 'states/users';
import useFetch from 'utils/fetch';

function RenderBookmarks({ renderData }) {
  const navigation = useNavigate();
  const fetchData = useFetch();
  const [userInfo, setUserInfo] = useRecoilState(userAtom);
  const handleBookmark = useCallback(
    async curationId => {
      if (!userInfo || !Object.keys(userInfo).length) {
        navigation('/login');
        alert('로그인이 필요합니다.');
      }
      try {
        const data = await fetchData.post('curation/bookmarks', {
          userEmail: userInfo.userEmail,
          cid: curationId,
        });
        console.log(data);
        setUserInfo({ ...userInfo, bookmarks: data });
      } catch (error) {
        console.log(error);
      }
    },
    [fetchData, navigation, setUserInfo, userInfo],
  );
  console.log(renderData);
  return (
    <div className="w-full h-full flex flex-col items-center bg-white gap-5 overflow-y-scroll py-1">
      {renderData?.map(ele => {
        return (
          <div
            key={uuidv4()}
            className="relative flex-1 w-[95%] h-full p-5 rounded-11xl bg-slate-100 shadow-md overflow-hidden flex flex-col pt-0 px-0 pb-6 items-center justify-center gap-[16px]"
          >
            {/* 유저 북마크 정보랑 비교해서 큐레이션 카드 ID가 북마크 정보 리스트안에 있으면
     TRUE 아니면 FALSE */}
            {!userInfo?.bookmarks?.includes(ele.cid) ? (
              <BookmarkBorderIcon
                className="absolute bottom-2 right-3 cursor-pointer z-10"
                color="primary"
                onClick={() => {
                  handleBookmark(ele.cid);
                }}
                fontSize="large"
              >
                북마크
              </BookmarkBorderIcon>
            ) : (
              <BookmarkIcon
                className="absolute bottom-2 right-3 cursor-pointer z-10"
                color="primary"
                onClick={() => {
                  handleBookmark(ele.cid);
                }}
              />
            )}
            <a
              href={ele.curl}
              className="w-fit h-fit no-underline text-black"
              target="_blank"
              rel="noreferrer"
            >
              <img
                className="relative w-full object-fill"
                alt=""
                src={ele.cimage}
              />
              <div className="flex flex-col items-start justify-center gap-[16px] p-4 w-fit">
                <div className="flex flex-row items-center justify-center gap-[12px]">
                  <div className="relative tracking-[0.01em] leading-[125%] font-medium flex items-start">
                    {ele.ctitle}
                  </div>
                </div>
                <div className="relative text-[0.88rem] tracking-[0.01em] leading-[125%] text-darkgray flex items-center">
                  {ele.ccontent}
                </div>
              </div>
            </a>
          </div>
        );
      })}
    </div>
  );
}
const rederDataType = shape({
  ctitle: string,
  cid: number,
  ccontent: string,
  cimage: string,
  cdate: string,
  curl: string,
  cpetSpecies: string,
  ccategory: string,
});

RenderBookmarks.propTypes = {
  renderData: arrayOf(rederDataType),
};
export default RenderBookmarks;
