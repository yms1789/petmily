import { useState, useEffect } from 'react';

import CheckRoundedIcon from '@mui/icons-material/CheckRounded';
import RefreshRoundedIcon from '@mui/icons-material/RefreshRounded';
import { styled } from '@mui/material';
import { useRecoilState, useSetRecoilState } from 'recoil';
import postsAtom from 'states/posts';
import userAtom from 'states/users';
import createimageAtom from 'states/createimage';
import updateimageAtom from 'states/updateimage';
import createpreviewAtom from 'states/createpreview';
import updatepreviewAtom from 'states/updatepreview';

import {
  FollowRecommend,
  SearchBar,
  SocialPost,
  UploadImage,
  Messages,
} from 'components';
import { placeholderImage } from 'utils/utils';
import useFetch from 'utils/fetch';

function Social() {
  const StyledRefreshRoundedIcon = styled(RefreshRoundedIcon, {
    name: 'StyledRefreshRoundedIcon',
    slot: 'Wrapper',
  })({
    color: '#0F1419',
    fontSize: 26,
    '&:hover': { color: '#1f90fe' },
  });

  const userLogin = useRecoilState(userAtom);
  const { userEmail } = userLogin[0];

  const [posts, setPosts] = useRecoilState(postsAtom);
  const [createUploadedImage, setCreateUploadedImage] =
    useRecoilState(createimageAtom);
  const [updateUploadedImage, setUpdateUploadedImage] =
    useRecoilState(updateimageAtom);
  const setCreateFilePreview = useSetRecoilState(createpreviewAtom);
  const setUpdateFilePreview = useSetRecoilState(updatepreviewAtom);
  const [postText, setPostText] = useState('');
  const fetchSocial = useFetch();

  const onPostTextChange = e => {
    setPostText(e.currentTarget.value);
  };

  const readPosts = async () => {
    try {
      const response = await fetchSocial.get(
        `board/all?currentUserEmail=${userEmail}`,
      );
      const dataRecent = response.reverse();
      const dataTen = dataRecent.slice(0, 10);
      setPosts(dataTen);
    } catch (error) {
      console.log(error);
    }
  };

  const createPost = async createPostText => {
    const boardRequestDto = {
      userEmail,
      boardContent: createPostText,
    };

    const hashTagRequestDto = {
      hashTagNames: ['string'],
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

    createUploadedImage.forEach(image => {
      formData.append('file', image);
    });

    try {
      const response = await fetchSocial.post('board/save', formData, 'image');
      console.log('게시글 작성', response);
      setPostText('');
      setCreateUploadedImage([]);
      setCreateFilePreview([]);
      readPosts();
    } catch (error) {
      console.log(error);
    }
  };

  const updatePost = async (post, currentText) => {
    const boardRequestDto = {
      userEmail,
      boardContent: currentText,
    };

    const hashTagRequestDto = {
      hashTagNames: ['string'],
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
      const response = await fetchSocial.post(
        `board/${post.boardId}`,
        formData,
        'image',
      );
      console.log('게시글 수정', response);
      setUpdateUploadedImage([]);
      setUpdateFilePreview([]);
      readPosts();
    } catch (error) {
      console.log(error);
    }
  };

  const deletePost = async currentPostId => {
    const sendBE = {
      userEmail,
    };
    try {
      const response = await fetchSocial.delete(
        `board/${currentPostId}`,
        sendBE,
      );
      console.log('게시글 삭제', response);
      readPosts();
    } catch (error) {
      console.log(error);
    }
  };

  const onSubmitNewPost = e => {
    e.preventDefault();
    createPost(postText);
  };

  useEffect(() => {
    readPosts();
  }, []);

  return (
    <div className="pb-5 min-w-[1340px] max-w-full w-full absolute top-[6.5rem] flex justify-between font-pretendard">
      <Messages />
      <div className="basis-1/2 min-w-[400px] rounded-lg flex flex-col gap-4">
        <SearchBar page="소통하기" />
        <div className="rounded-xl bg-white w-full h-fill flex flex-col items-center justify-center text-[1rem] text-black">
          <div className="flex flex-col gap-5 w-full my-4">
            <div className="flex justify-between w-full">
              <div className="font-semibold text-[1.25rem] mx-6">뉴 피드</div>
              <div className="mx-6">
                <StyledRefreshRoundedIcon />
              </div>
            </div>
            <span className="h-[0.06rem] w-full bg-gray2 inline-block" />
            <form
              encType="multipart/form-data"
              onSubmit={onSubmitNewPost}
              className="relative flex pb-12 flex-col px-[1rem] items-between justify-between"
            >
              <div className="flex items-start">
                <div className="w-[3rem] h-[3rem] pr-4 overflow-hidden">
                  <img
                    className="rounded-full w-[3rem] h-[3rem] overflow-hidden object-cover"
                    alt=""
                    src={placeholderImage(70)}
                  />
                </div>
                <textarea
                  onChange={onPostTextChange}
                  value={postText}
                  name="post"
                  cols="80"
                  rows="5"
                  placeholder="자유롭게 이야기 해보세요!"
                  className="resize-none font-medium w-full text-black mx-4 rounded-xl p-4 border-solid border-[2px] border-gray2 focus:outline-none focus:border-dodgerblue font-pretendard text-base"
                />
              </div>
              <UploadImage page="소통하기" />
              <button
                type="submit"
                className="absolute right-4 bottom-0 cursor-pointer rounded-full text-[1rem] w-[1.2rem] h-[0rem] text-white bg-dodgerblue border-solid border-[2px] border-dodgerblue flex py-[1rem] px-[1.4rem] ml-[0.4rem] mr-[1rem] items-center justify-center opacity-[1]"
              >
                <CheckRoundedIcon />
              </button>
            </form>
            {posts?.map(p => {
              return (
                <div key={p.boardId}>
                  <SocialPost
                    key={p}
                    post={p}
                    readPosts={readPosts}
                    updatePost={updatePost}
                    deletePost={deletePost}
                  />
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <FollowRecommend />
    </div>
  );
}

export default Social;
