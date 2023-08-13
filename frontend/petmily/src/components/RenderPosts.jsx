import { useEffect, useState } from 'react';

import { string } from 'prop-types';
import { useRecoilState, useRecoilValue, useSetRecoilState } from 'recoil';
import userAtom from 'states/users';
import updateimageAtom from 'states/updateimage';
import updatepreviewAtom from 'states/updatepreview';
// import { v4 as uuidv4 } from 'uuid';
import useFetch from 'utils/fetch';
import SocialPost from './SocialPost';

function RenderPosts({ page }) {
  const fetchData = useFetch();
  const userInfo = useRecoilValue(userAtom);
  const [myPosts, setMyPosts] = useState([]);

  const setUpdateFilePreview = useSetRecoilState(updatepreviewAtom);
  const [updateUploadedImage, setUpdateUploadedImage] =
    useRecoilState(updateimageAtom);

  const readMyPosts = async () => {
    try {
      if (page === 'my') {
        const myPostsData = await fetchData.get(
          `profile/${userInfo.userEmail}/writeboard?currentUser=${userInfo.userEmail}`,
        );
        if (myPostsData.length > 0) {
          console.log('사용자가 쓴 게시글 모아보기', myPostsData);
          setMyPosts(myPostsData);
        }
      }
      if (page === 'like') {
        const myPostsData = await fetchData.get(
          `profile/${userInfo.userEmail}/likeboard?currentUser=${userInfo.userEmail}`,
        );
        if (myPostsData.length > 0) {
          console.log('사용자가 쓴 게시글 모아보기', myPostsData);
          setMyPosts(myPostsData);
        }
      }
    } catch (error) {
      console.log(error);
    }
  };

  const updatePost = async (post, currentText, currentHashTags) => {
    const boardRequestDto = {
      userEmail: userInfo.userEmail,
      boardContent: currentText,
    };

    const hashTagRequestDto = {
      hashTagNames: currentHashTags,
    };

    const formData = new FormData();

    formData.append(
      'boardRequestDto',
      new Blob([JSON.stringify(boardRequestDto)], {
        type: 'application/json',
      }),
    );
    formData.append(
      'hashTagRequestDto',
      new Blob([JSON.stringify(hashTagRequestDto)], {
        type: 'application/json',
      }),
    );
    updateUploadedImage.forEach(image => {
      formData.append('file', image);
    });

    try {
      const response = await fetchData.post(
        `board/${post.boardId}`,
        formData,
        'image',
      );
      console.log('게시글 수정', response);
      setUpdateUploadedImage([]);
      setUpdateFilePreview([]);
      readMyPosts();
    } catch (error) {
      console.log(error);
    }
  };

  const deletePost = async currentPostId => {
    const sendBE = {
      userEmail: userInfo.userEmail,
    };
    try {
      const response = await fetchData.delete(`board/${currentPostId}`, sendBE);
      console.log('게시글 삭제', response);
      readMyPosts();
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    readMyPosts();
    console.log('여깅', myPosts);
  }, [page]);

  return (
    <div className="here w-full h-full flex flex-col items-center bg-white gap-5 py-1">
      {myPosts.length > 0 ? (
        myPosts?.map(p => {
          return (
            <div key={p.boardId} className="w-full">
              <SocialPost
                key={p}
                post={p}
                readPosts={readMyPosts}
                updatePost={updatePost}
                deletePost={deletePost}
              />
            </div>
          );
        })
      ) : (
        <div>
          <p className=" font-bold">
            {page === 'my' ? '작성한' : '좋아요한'} 게시글이 없습니다.
          </p>
        </div>
      )}
    </div>
  );
}

RenderPosts.propTypes = {
  page: string,
};

export default RenderPosts;
